package com.imooc.security.admin;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * cookie拦截的处理逻辑.
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();

        // 首先获取access_token_cooke
        String accessToken = getCookie("magic_access_token");
        if (StringUtils.isNotBlank(accessToken)) {
            // 往请求头中添加token
            requestContext.addZuulRequestHeader("Authorization", "bearer " + accessToken);
        } else {
            // token 不存在或者失效，获取refreshToken
            String refreshToken = getCookie("magic_refresh_token");
            if (StringUtils.isBlank(refreshToken)) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(500);
                requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
                requestContext.getResponse().setContentType("application/json");
            } else {
                // 刷新token
                String oauthServiceUrl = "http://gateway.imooc.com:9070/token/oauth/token";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth("admin", "123456");

                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("grant_type", "refresh_token");
                params.add("refresh_token", refreshToken);
                HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

                try {
                    ResponseEntity<TokenInfo> newToken = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
                    // 刷新成功
                    requestContext.addZuulRequestHeader("Authorization", "bearer " + newToken.getBody().getAccess_token());

                    // 同时写入Cookie
                    Cookie accessTokenCookie = new Cookie("magic_access_token", newToken.getBody().getAccess_token());
                    accessTokenCookie.setMaxAge(newToken.getBody().getExpires_in().intValue());
                    accessTokenCookie.setDomain("magic.com");
                    accessTokenCookie.setPath("/");
                    response.addCookie(accessTokenCookie);

                    Cookie refreshTokenCookie = new Cookie("magic_refresh_token", newToken.getBody().getRefresh_token());
                    refreshTokenCookie.setMaxAge(2592000);
                    refreshTokenCookie.setDomain("magic.com");
                    refreshTokenCookie.setPath("/");
                    response.addCookie(refreshTokenCookie);

                } catch (Exception e) {
                    requestContext.setSendZuulResponse(false);
                    requestContext.setResponseStatusCode(500);
                    requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
                    requestContext.getResponse().setContentType("application/json");
                }

            }
        }
        return null;
    }

    private String getCookie(String name) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Cookie[] cookies = request.getCookies();

        String value = null;
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(name, cookie.getName())) {
                value = cookie.getValue();
                break;
            }
        }
        return value;
    }
}
