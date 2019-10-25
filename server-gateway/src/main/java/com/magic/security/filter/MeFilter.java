package com.magic.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MeFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String uri = request.getRequestURI();
        return StringUtils.equals("/user/me", uri);
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String user = requestContext.getZuulRequestHeaders().get("username");
        if (StringUtils.isNotBlank(user)) {
            requestContext.setResponseBody("{\"username\":\"" + user + "\"}");
//            requestContext.setResponseBody("{\"username:\"" + user + "\"}");
        }
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(200);
        requestContext.getResponse().setContentType("applicaiton/json");
        return null;
    }
}
