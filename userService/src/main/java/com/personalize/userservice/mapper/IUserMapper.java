package com.personalize.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.personalize.userservice.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper extends BaseMapper<UserInfo> {

}
