package com.smzk.delivery_service.config;

import com.smzk.delivery_service.filter.AdminAuthFilter;
import com.smzk.delivery_service.filter.UserAuthFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AdminAuthFilter> adminAuthFilterRegistration() {
        FilterRegistrationBean<AdminAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AdminAuthFilter());
        registration.addUrlPatterns("/admin/*"); // 仅拦截管理端
        registration.setName("adminAuthFilter");
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<UserAuthFilter> userAuthFilterRegistration() {
        FilterRegistrationBean<UserAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new UserAuthFilter());
        registration.addUrlPatterns("/user/*"); // 仅拦截用户端
        registration.setName("userAuthFilter");
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setOrder(2);
        return registration;
    }

}
