package com.personalize.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.personalize.knowledge.entity.UserKnowledgeInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserKnowledgeMapper extends BaseMapper<UserKnowledgeInfo> {
}
