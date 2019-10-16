package com.magic.security.filter;

import lombok.Data;

import java.util.Date;

@Data
public class TokenInfo {
    /**
     * 是否存活.
     */
    private boolean active;

    /**
     * 应用clientId.
     */
    private String client_id;

    /**
     * scope.
     */
    private String[] scope;

    /**
     * 用户名.
     */
    private String user_name;

    /**
     * 某一个应用（clientId）可以访问的资源服务.
     */
    private String[] aud;

    /**
     * token过期时间.
     */
    private Date exp;

    /**
     * 具体的权限.
     */
    private String[] authorities;
}
