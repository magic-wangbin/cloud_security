/**
 *
 */
package com.imooc.security.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jojo
 *
 */
@SpringBootApplication
@RestController
@Slf4j
@EnableZuulProxy
public class AdminApplication {

    private RestTemplate restTemplate = new RestTemplate();

//    @PostMapping("/login")
//    public void login(@RequestBody Credentials credentials, HttpServletRequest request) {
//
//        String oauthServiceUrl = "http://gateway.magic.com:9070/token/oauth/token";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setBasicAuth("admin", "123456");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("username", credentials.getUsername());
//        params.add("password", credentials.getPassword());
//        params.add("grant_type", "password");
//        params.add("scope", "read write");
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
//
//        ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
//
//        request.getSession().setAttribute("token", response.getBody());
//    }

    @GetMapping("/oauth/callback")
    public void callback(@RequestParam String code, String state, HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("state 标识值为：" + state);

        // 获取token的请求
        String oauthServiceUrl = "http://gateway.magic.com:9070/token/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("admin", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://admin.magic.com:8080/oauth/callback");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfo> token = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);

        request.getSession().setAttribute("token", token.getBody());

        response.sendRedirect("/");
    }

    @GetMapping("me")
    public TokenInfo me(HttpServletRequest request) {
        TokenInfo tokenInfo = (TokenInfo) request.getSession().getAttribute("token");
        return tokenInfo;
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    /**
     *
     * @author jojo
     * 2019年8月25日
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
