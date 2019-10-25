package com.imooc.security.admin;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenInfo {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private Long expires_in;
    private String scope;

    private LocalDateTime expireTime;

    public TokenInfo init() {
        expireTime = LocalDateTime.now().plusSeconds(expires_in - 3); //提前3s开始刷新
        return this;
    }

    public boolean isExpired() {
        // 过期时间在当前时间之前
        return expireTime.isBefore(LocalDateTime.now());
    }
}
