package com.cloud.security.entity;

import com.cloud.security.vo.UserInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    public UserInfo buildInfo() {
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(this, info);
        return info;
    }
}
