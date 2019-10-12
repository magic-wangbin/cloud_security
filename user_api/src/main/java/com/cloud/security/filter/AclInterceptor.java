package com.cloud.security.filter;

import com.cloud.security.vo.UserInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AclInterceptor extends HandlerInterceptorAdapter {

    private String[] permitUrls = new String[]{"/users/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println(4);
        //
        if (ArrayUtils.contains(permitUrls, request.getRequestURI())) {
            return true;
        }

        // 查询用户信息
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        // 401
        if (user == null) {
            response.setContentType("text/plain");
            response.getWriter().write("need authentication");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        //403
        String method = request.getMethod();
        if (!user.hasPermission(method)) {
            response.setContentType("text/plain");
            response.getWriter().write("forbidden");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        return true;
    }
}
