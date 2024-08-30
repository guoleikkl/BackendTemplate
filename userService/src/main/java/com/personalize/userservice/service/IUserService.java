package com.personalize.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.personalize.common.entity.R;
import com.personalize.userservice.entity.LoginFormDTO;
import com.personalize.userservice.entity.UserDTO;
import com.personalize.userservice.entity.UserInfo;
import com.personalize.userservice.entity.UserRegistrationDTO;

import javax.servlet.http.HttpServletRequest;

public interface IUserService extends IService<UserInfo> {

    R<String> registerUser(UserRegistrationDTO userRegistrationDto);
    R<String> login(LoginFormDTO loginFormDTO, HttpServletRequest request);

    R<UserDTO> getUserInfo(String authorization);

    R<Boolean> updateUserInfo(UserDTO userDTO);
}
