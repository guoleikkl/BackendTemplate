package com.personalize.userservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.personalize.common.entity.En;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sk_notes")
public class Note extends En<String> {
    private static final long serialVersionUID = 1L;
    @TableField("user_id")
    private String userId;
    @TableField("knowledge_id")
    private String knowledgeId;
    @TableField("note_msg")
    private String noteMsg;
    @Builder
    public Note(String id, LocalDateTime createTime, String createUser, LocalDateTime updateTime, String updateUser,String userId, String knowledgeId, String noteMsg){
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.userId = userId;
        this.knowledgeId = knowledgeId;
        this.noteMsg = noteMsg;
    }
}
