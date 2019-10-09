package com.cloud.security.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserInfo {

    private Long userId;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;
}
