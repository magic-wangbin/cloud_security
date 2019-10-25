package com.imooc.security.admin;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
public class SessionFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        // 获取token进行转发
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //
        TokenInfo token = (TokenInfo) request.getSession().getAttribute("token");
        if (token != null) {

            String tokenValue = token.getAccess_token();

            if (token.isExpired()) {
                //token过期开始刷新令牌
                String oauthServiceUrl = "http://gateway.magic.com:9070/token/oauth/token";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth("admin", "123456");

                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("grant_type", "refresh_token");
                params.add("refresh_token", token.getRefresh_token());
                HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

                try {
                    ResponseEntity<TokenInfo> newToken = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
                    request.getSession().setAttribute("token", newToken.getBody().init());// 获取token，并设置过期时间
                    tokenValue = newToken.getBody().getAccess_token();
                } catch (Exception e) {
                    // 刷新令牌失败的处理
                    requestContext.setSendZuulResponse(false);// 服务不会继续往下执行
                    requestContext.setResponseStatusCode(500);
                    requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
                    requestContext.getResponse().setContentType("application/json");
                }
            }
            requestContext.addZuulRequestHeader("Authorization", "bearer " + tokenValue);
        }

        return null;
    }
}
