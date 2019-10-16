package com.magic.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * oauth认证filter.
 */
@Component
@Slf4j
public class OAuthFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {

        log.info("oauth start");

        // 获取request请求
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        // 直接可以访问的请求
        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            return null;
        }

        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(authHeader)) {
            return null;
        }

        if (!StringUtils.startsWithIgnoreCase(authHeader, "bearer ")) {
            return null;
        }

        try {

            TokenInfo info = getTokenInfo(authHeader);
            request.setAttribute("tokenInfo", info);

        } catch (Exception e) {
            log.error("get token info fail", e);
        }

        return null;
    }

    private TokenInfo getTokenInfo(String authHeader) {
        String token = StringUtils.substringAfter(authHeader, "bearer ");
        String oauthServiceUrl = "http://localhost:9090/oauth/check_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("gateway", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);

        log.info("token info :" + response.getBody().toString());

        return response.getBody();
    }


}
