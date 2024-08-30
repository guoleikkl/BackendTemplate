package com.personalize.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@TableName("sk_user_knowledge_click")
public class UserKnowledgeInfo {
    @TableField("id")
    private String id;
    @TableField("user_id")
    private String userId;
    @TableField("knowledge_id")
    private String knowledgeId;
    @TableField("click_num")
    private Integer clickNum;
    @Builder
    public UserKnowledgeInfo(String id,String userId,String knowledgeId,Integer clickNum){
        this.id = id;
        this.userId = userId;
        this.knowledgeId = knowledgeId;
        this.clickNum = clickNum;
    }
}
