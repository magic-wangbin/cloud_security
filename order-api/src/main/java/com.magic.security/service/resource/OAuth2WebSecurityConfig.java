//package com.magic.security.service.resource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
//import org.springframework.security.oauth2.provider.token.*;
//
//@Configuration
//@EnableWebSecurity
//public class OAuth2WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public ResourceServerTokenServices tokenServices() {
//        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setClientId("orderService");
//        remoteTokenServices.setClientSecret("123456");
//        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:9090/oauth/check_token");
//        remoteTokenServices.setAccessTokenConverter(getAccessTokenConverter());
//        return remoteTokenServices;
//    }
//
//    private AccessTokenConverter getAccessTokenConverter() {
//        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
//        DefaultUserAuthenticationConverter authenticationConverter = new DefaultUserAuthenticationConverter();
//        authenticationConverter.setUserDetailsService(userDetailsService);
//        accessTokenConverter.setUserTokenConverter(authenticationConverter);
//        return accessTokenConverter;
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
//        authenticationManager.setTokenServices(tokenServices());
//        return authenticationManager;
//    }
//}
