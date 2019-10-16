package com.magic.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("authorization start");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        if (!isNeedAuth(request)) {
            return null;
        }

        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            return null;
        }

        // 获取token
        TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");

        if ((tokenInfo == null || !tokenInfo.isActive())) {
            // 401
            log.info("audit log update fail 401");
            handleError(401, requestContext);
        }

        if (!hasPermission(tokenInfo, request)) {
            log.info("audit log update fail 403");
            handleError(403, requestContext);
        }

        requestContext.addZuulRequestHeader("username", tokenInfo.getUser_name());

        return null;
    }

    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
        return true;////RandomUtils.nextInt() % 2 == 0;
    }

    private void handleError(int statusCode, RequestContext requestContext) {
        requestContext.getResponse().setContentType("application/json");
        requestContext.setResponseStatusCode(statusCode);
        requestContext.setResponseBody("{\"message\":\"auth fail\"}");
        requestContext.setSendZuulResponse(false);
    }

    private boolean isNeedAuth(HttpServletRequest request) {
        return true;
    }
}
