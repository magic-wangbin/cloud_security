package com.cloud.security.config;

import com.cloud.security.filter.AclInterceptor;
import com.cloud.security.filter.AuditLogInterceptor;
import com.cloud.security.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class SecurityConfig implements WebMvcConfigurer {

    /**
     * 审计日志.
     */
    @Autowired
    private AuditLogInterceptor auditLogInterceptor;

    @Autowired
    private AclInterceptor aclInterceptor;


    /**
     * 添加应用拦截器.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInterceptor);
        registry.addInterceptor(aclInterceptor);
    }

    @Bean
    public AuditorAware auditorAware() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                UserInfo userInfo = (UserInfo) servletRequestAttributes.getRequest().getSession().getAttribute("user");
                String userName = null;
                // 直接获取session中的用户
                if (userInfo != null) {
                    userName = userInfo.getUserName();
                }
                return Optional.ofNullable(userName);
            }
        };
    }
}
