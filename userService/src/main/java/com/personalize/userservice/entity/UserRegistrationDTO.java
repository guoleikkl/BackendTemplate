package com.personalize.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    private String username;
    private String nickName;
    private String password;
    private String checkPass;
    private Integer age;
    private String phone;
    private String email;
}
