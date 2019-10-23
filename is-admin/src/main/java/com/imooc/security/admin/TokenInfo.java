package com.imooc.security.admin;

import lombok.Data;

@Data
public class TokenInfo {
    private String access_token;
    private String token_type;
    private Long expires_in;
    private String scope;
}
