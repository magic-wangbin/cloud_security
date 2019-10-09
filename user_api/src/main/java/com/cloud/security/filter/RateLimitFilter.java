package com.cloud.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 流量控制Filter.
 */
@Component
@Order(1)
public class RateLimitFilter extends OncePerRequestFilter {

    private RateLimiter rateLimiter = RateLimiter.create(1);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("filter order info is 1");
        if (rateLimiter.tryAcquire()) {
            filterChain.doFilter(request, response);
        } else {
            System.out.println("每秒内请求次数最大为1");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            Map<String, Object> result = Maps.newHashMap();
            result.put("message", "too many request!!");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            response.getWriter().flush();
        }
    }
}
