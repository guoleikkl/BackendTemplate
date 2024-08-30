package com.personalize.knowledge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.personalize.knowledge.entity.UserKnowledgeInfo;
import com.personalize.knowledge.feignClient.IdClient;
import com.personalize.knowledge.mapper.IUserKnowledgeMapper;
import com.personalize.knowledge.service.IKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KnowledgeServiceImpl implements IKnowledgeService {
    @Autowired
    private IdClient idClient;
    @Autowired
    private IUserKnowledgeMapper userKnowledgeMapper;
    @Override
    public void click(String knowledgeId, String authorization) {
        String userId = idClient.userId(authorization);
        QueryWrapper<UserKnowledgeInfo> queryWrapper = new QueryWrapper<>();
        Map<String,String> idQuery = new HashMap<>();
        idQuery.put("user_id",userId);
        idQuery.put("knowledge_id",knowledgeId);
        QueryWrapper<UserKnowledgeInfo> userKnowledgeInfoQueryWrapper = queryWrapper.allEq(idQuery);
        UserKnowledgeInfo userKnowledgeInfo = userKnowledgeMapper.selectOne(userKnowledgeInfoQueryWrapper);
        if (userKnowledgeInfo==null){
            createUserKnowledgeRecord(knowledgeId,userId);
        }else {
            Integer clickNum = userKnowledgeInfo.getClickNum();
            Integer after_click = clickNum+1;
            userKnowledgeInfo.setClickNum(after_click);
            userKnowledgeMapper.updateById(userKnowledgeInfo);
        }
    }

    @Override
    public int createUserKnowledgeRecord(String knowledgeId, String userId) {
        String id = idClient.id();
        UserKnowledgeInfo record = UserKnowledgeInfo.builder().id(id).userId(userId).knowledgeId(knowledgeId)
                .clickNum(1).build();
        int insert = userKnowledgeMapper.insert(record);
        return insert;
    }
}
