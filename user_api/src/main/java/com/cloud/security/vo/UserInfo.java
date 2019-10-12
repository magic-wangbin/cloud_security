package com.cloud.security.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

@Data
public class UserInfo {

    private Long userId;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String permissions;

    /**
     * 用户权限.
     *
     * @param method
     * @return
     */
    public boolean hasPermission(String method) {
        if (
            (StringUtils.equalsIgnoreCase("get", method) && StringUtils.contains(permissions, "r"))
                || (StringUtils.equalsIgnoreCase("post", method) && StringUtils.contains(permissions, "w"))
        ) {
            return true;
        }
        return false;
    }
}
