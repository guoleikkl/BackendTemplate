package com.personalize.userservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.personalize.common.entity.R;
import com.personalize.userservice.FeignClient.IdClient;
import com.personalize.userservice.entity.Note;
import com.personalize.userservice.entity.NoteDTO;
import com.personalize.userservice.entity.UserDTO;
import com.personalize.userservice.mapper.INoteMapper;
import com.personalize.userservice.service.INoteService;
import com.personalize.userservice.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class UserServiceApplicationTests {
    @Autowired
    private IdClient idClient;
    @Autowired
    private INoteMapper noteMapper;
    @Autowired
    public IUserService userService;
    @Autowired
    public INoteService noteService;
    @Test
    void contextLoads() {
        String token = "5aa26ef1ab734aefaa4611f7cfd38f57";
        R<List<NoteDTO>> allNotes = noteService.getAllNotes(token);
        for (NoteDTO noteDTO:allNotes.getData()){
            System.out.println("笔记id:"+noteDTO.getId()+"知识点id："+noteDTO.getKnowledgeId()+"知识点内容:"+noteDTO.getNoteMsg());
        }
    }
    @Test
    void testCreateNote(){
        String id = idClient.id();
        String userId = "1241733585707204641";
        String knowledgeId = "2";
        String noteMsg = "test";
        LocalDateTime now = LocalDateTime.now();
        Note note = Note.builder().id(id).createTime(now).createUser(userId).updateTime(now).updateUser(userId)
                .userId(userId).knowledgeId(knowledgeId).noteMsg(noteMsg).build();
        int insert = noteMapper.insert(note);
        System.out.println(insert);
    }
}
