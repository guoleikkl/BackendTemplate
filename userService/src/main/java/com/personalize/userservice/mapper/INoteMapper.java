package com.personalize.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.personalize.userservice.entity.Note;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface INoteMapper extends BaseMapper<Note> {
    @Select("select id from sk_notes where user_id = #{userId} and note_id=#{noteId}")
    String isExistNotesByUserIdAndKnowledgeId(@Param("userId")String userId, @Param("noteId")String noteId);
}
