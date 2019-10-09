package com.cloud.security.filter;

import com.cloud.security.service.UserService;
import com.cloud.security.vo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
@Order(2)
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("filter order info is 2");

        String authHeader = request.getHeader("Authorization");
        Enumeration<String> headerNames = request.getHeaderNames();

        if (StringUtils.isNotBlank(authHeader)) {

            String token64 = StringUtils.substringAfter(authHeader, "Basic ");
            String token = new String(Base64Utils.decodeFromString(token64));
            String[] items = StringUtils.split(token, ":");

            String userName = items[0];
            String password = items[1];

            UserInfo userInfo = userService.findByUserName(userName);

            if (userInfo != null && password.equals(userInfo.getPassword())) {
                request.setAttribute("user", userInfo);
            }
        }

        filterChain.doFilter(request, response);


    }
}
