package com.personalize.userservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personalize.userservice.FeignClient.IdClient;
import com.personalize.userservice.entity.LoginFormDTO;
import com.personalize.userservice.entity.UserDTO;
import com.personalize.userservice.entity.UserInfo;
import com.personalize.userservice.entity.UserRegistrationDTO;
import com.personalize.userservice.service.IUserService;
import com.personalize.userservice.mapper.IUserMapper;
import com.personalize.common.entity.R;
import com.personalize.common.entity.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.personalize.common.constant.UserConstant.LOGIN_USER_KEY;
import static com.personalize.common.constant.UserConstant.LOGIN_USER_TTL;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<IUserMapper, UserInfo> implements IUserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public IUserMapper userMapper;
    @Autowired
    private IdClient idClient;
    @Override
    public R<String> registerUser(UserRegistrationDTO userRegistrationDto) {
        //如果用户名已存在，则返回错误消息
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername,userRegistrationDto.getUsername());
        long count = count(queryWrapper);
        if (count!=0){
            return R.fail("用户名重复");
        }
        if (!StrUtil.equals(userRegistrationDto.getPassword(),userRegistrationDto.getCheckPass())){
            return R.fail("两次密码不同，请检查密码");
        }
        //存用户
        String userId = idClient.id();
        LocalDateTime now = LocalDateTime.now();
        UserSession createUser = new UserSession("1","root","root","super");
        UserInfo userInfo = UserInfo.builder().id(userId).updateUser(createUser.getUsername()).createUser(createUser.getUsername())
                .updateTime(now).createTime(now).username(userRegistrationDto.getUsername()).password(userRegistrationDto.getPassword())
                .email(userRegistrationDto.getEmail()).phone(userRegistrationDto.getPhone()).nickName(userRegistrationDto.getNickName()).status(true).authority("guest")
                .age(userRegistrationDto.getAge()).build();
        int insert = userMapper.insert(userInfo);
        if (insert>0){
            return R.success("用户注册成功");
        }else {
            return R.fail("用户插入失败");
        }
    }
    @Override
    public R<String> login(LoginFormDTO loginFormDTO, HttpServletRequest request) {
        String curUsername = loginFormDTO.getUsername();
        //判断用户是否存在
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername,curUsername);
        long isExist = count(queryWrapper);
        if (isExist==0){
            return R.fail("用户不存在");
        }
        UserInfo user = getOne(queryWrapper);
        String curPassword = loginFormDTO.getPassword();
        if (!StrUtil.equals(curPassword,user.getPassword())){
            return R.fail("密码错误");
        }
        if (!user.isStatus()){
            return R.fail("用户未激活");
        }
        //登录成功，更新其ip信息和登录时间
        String loginIp =request.getRemoteAddr();
        LocalDateTime loginUpdate = LocalDateTime.now();
        user.setLoginIP(loginIp);
        user.setLoginTime(loginUpdate);
        updateById(user);
        //7. 保存用户信息到redis中
        // 7.1 随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true);
        UserSession userDTO = BeanUtil.copyProperties(user, UserSession.class);
        Map<String,Object> userMap = BeanUtil.beanToMap(userDTO,new HashMap<>(), CopyOptions.create().setIgnoreNullValue(true)
                .setFieldValueEditor((fieldName,fieldValue)->fieldValue.toString()));
        //7.3 存储
        String tokenKey = LOGIN_USER_KEY+token;
        stringRedisTemplate.opsForHash().putAll(tokenKey,userMap);
        //7.4设置token有效期
        stringRedisTemplate.expire(tokenKey,LOGIN_USER_TTL, TimeUnit.MINUTES);
        return R.success(token,"登录成功");
    }
    /**
     * 用户信息展示
     */
    @Override
    public R<UserDTO> getUserInfo(String authorization) {
        String id = idClient.userId(authorization);
        if (id.isBlank()){
            return R.fail("用户信息获取失败，用户已过期");
        }
        UserInfo userInfo = userMapper.selectById(id);
        UserDTO userDTO = UserDTO.builder().id(userInfo.getId())
                .email(userInfo.getEmail())
                .phone(userInfo.getPhone())
                .nickName(userInfo.getNickName())
                .age(userInfo.getAge()).build();
        return R.success(userDTO);
    }

    @Override
    public R<Boolean> updateUserInfo(UserDTO userDTO) {
        String id = userDTO.getId();
        UserInfo updateUser = userMapper.selectById(id);
        updateUser.setEmail(userDTO.getEmail());
        updateUser.setPhone(userDTO.getPhone());
        updateUser.setNickName(userDTO.getNickName());
        updateUser.setAge(userDTO.getAge());
        int i = userMapper.updateById(updateUser);
        if (i>0){
            return R.success(true,"更新成功");
        }else {
            return R.fail("更新失败");
        }
    }

}
