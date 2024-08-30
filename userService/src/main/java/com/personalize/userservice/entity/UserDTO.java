package com.personalize.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端信息展示实体,是用户可以查看并修改的字段
 */
@Data
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String phone;
    private String nickName;
    private Integer age;
    @Builder
    public UserDTO(String id,String email,String phone,String nickName,Integer age){
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.nickName = nickName;
        this.age = age;
    }
}
