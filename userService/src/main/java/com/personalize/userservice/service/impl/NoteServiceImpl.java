package com.personalize.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personalize.userservice.FeignClient.IdClient;
import com.personalize.userservice.entity.Note;
import com.personalize.userservice.entity.NoteDTO;
import com.personalize.userservice.mapper.INoteMapper;
import com.personalize.userservice.service.INoteService;
import com.personalize.common.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NoteServiceImpl extends ServiceImpl<INoteMapper, Note> implements INoteService {
    @Autowired
    private INoteMapper noteMapper;
    @Autowired
    private IdClient idClient;
    /**
     *
     * @param noteDTO
     * @return
     */
    @Override
    public R<Boolean> updateUserNote(NoteDTO noteDTO,String authorization) {
        String userId = idClient.userId(authorization);
        String knowledgeId = noteDTO.getKnowledgeId();
        QueryWrapper<Note> queryWrapper =  new QueryWrapper<>();
        Map<String,String> idQuery = new HashMap<>();
        idQuery.put("user_id",userId);
        idQuery.put("knowledge_id",knowledgeId);
        QueryWrapper<Note> noteQueryWrapper = queryWrapper.allEq(idQuery);
        Note isNote = noteMapper.selectOne(noteQueryWrapper);
        if (isNote==null){
            //创建笔记
            R<Boolean> res = createUserNote(noteDTO,userId);
            return res;
        }else {
            //更新笔记
            Note note = noteMapper.selectById(isNote.getId());
            LocalDateTime now = LocalDateTime.now();
            note.setNoteMsg(noteDTO.getNoteMsg());
            note.setUpdateTime(now);
            note.setUpdateUser(userId);
            int res = noteMapper.updateById(note);
            if (res>0){
                return R.success(true,"笔记更新成功");
            }else {
                return R.fail("笔记更新失败");
            }
        }
    }
    @Override
    public R<Boolean> createUserNote(NoteDTO noteDTO,String userId) {
        String id = idClient.id();
        LocalDateTime now = LocalDateTime.now();
        Note note = Note.builder().id(id).createTime(now).createUser(userId).updateTime(now).updateUser(userId)
                .userId(userId).knowledgeId(noteDTO.getKnowledgeId()).noteMsg(noteDTO.getNoteMsg()).build();
        int insert = noteMapper.insert(note);
        if (insert>0){
            return R.success(true,"笔记首次创建完成");
        }else {
            return R.fail("笔记首次创建失败");
        }
    }

    @Override
    public R<NoteDTO> getUserNote(String knowledgeId, String authorization) {
        String userId = idClient.userId(authorization);
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        Map<String ,String> idQuery = new HashMap<>();
        idQuery.put("user_id",userId);
        idQuery.put("knowledge_id",knowledgeId);
        QueryWrapper<Note> noteQueryWrapper = queryWrapper.allEq(idQuery);
        Note note = noteMapper.selectOne(noteQueryWrapper);
        if (note==null){
            return R.fail("笔记获取失败");
        }else {
            //这里新建的是用户id
            NoteDTO noteDTO = new NoteDTO(note.getId(),note.getKnowledgeId(),note.getNoteMsg());
            return R.success(noteDTO,"笔记获取完成");
        }
    }

    @Override
    public R<List<NoteDTO>> getAllNotes(String authorization) {
        String userId = idClient.userId(authorization);
        QueryWrapper<Note> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Note> noteQuery = queryWrapper.eq("user_id", userId);
        List<Note> notes = noteMapper.selectList(noteQuery);
        List<NoteDTO> res = new ArrayList<>();
        for (Note note:notes){
            NoteDTO noteDTO = new NoteDTO(note.getId(), note.getKnowledgeId(), note.getNoteMsg());
            res.add(noteDTO);
        }
        if (res==null){
            return R.fail("笔记获取失败");
        }
        return R.success(res,"笔记获取成功");
    }
}
