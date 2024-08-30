package com.personalize.userservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.personalize.common.entity.En;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("sk_user")
public class UserInfo extends En<String> {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;
    /**
     * 密码
     */
    @TableField("password")
    private String password;

    @TableField("email")
    private String email;
    /**
     * 权限信息
     */
    @TableField("authority")
    private String authority;
    /**
     * 最后登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;
    /**
     * 最后登录IP
     */
    @TableField("login_ip")
    private String loginIP;
    @TableField("status")
    private boolean status;
    @Builder
    public UserInfo(String id, LocalDateTime createTime, String createUser, LocalDateTime updateTime,String updateUser,
                String username,String phone,Integer age,String password,String nickName,String email,String authority,
                LocalDateTime loginTime,String loginIP,Boolean status){
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.username = username;
        this.phone = phone;
        this.age = age;
        this.password = password;
        this.nickName = nickName;
        this.email =email;
        this.authority = authority;
        this.loginTime = loginTime;
        this.loginIP = loginIP;
        this.status = status;
    }
}
