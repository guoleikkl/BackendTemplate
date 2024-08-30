package com.personalize.common.controller;

import cn.hutool.core.bean.BeanUtil;
import com.personalize.common.constant.UserConstant;
import com.personalize.common.entity.UserSession;
import com.personalize.common.utils.id.IdGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/utils")
public class idGenerateController {
    @Autowired
    private IdGenerate<Long> idGenerate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/id")
    public String id(){
        return idGenerate.generate().toString();
    }
    @GetMapping("/{token}")
    public String userId(@PathVariable("token")String token){
        System.out.println("token:"+token);
        String userToken = UserConstant.LOGIN_USER_KEY+token;
        Map<Object, Object> userMap = redisTemplate.opsForHash().entries(userToken);
        if (userMap.isEmpty()){
            return "";
        }
        UserSession userSession = BeanUtil.fillBeanWithMap(userMap,new UserSession(),false);
        return userSession.getId();
    }
}
