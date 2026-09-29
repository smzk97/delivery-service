package com.smzk.delivery_service.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smzk.delivery_service.entity.admin.EmployeeThreadLocal;
import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.entity.user.UserThreadLocal;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.utils.JwtUtils;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserAuthFilter implements Filter {
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final List<String> whiteLists = new ArrayList<>(List.of(
            "/user/login"
    ));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jakarta.servlet.Filter.super.init(filterConfig);
        log.info("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String URI = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getHeader("authorization");

        for(String white:whiteLists){
            if(white.matches(URI)){
                chain.doFilter(request,response);
                return;
            }
        }

        if(token == null || token.isEmpty()){
            log.error("缺失authorization");
            httpServletResponse.setStatus(ErrorCode.UNAUTHORIZED.getCode());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(Result.Failed(ErrorCode.UNAUTHORIZED.getMsg())));
            return;
        }
        try{
            Claims claims = JwtUtils.parseToken(token);
            UserThreadLocal userThreadLocal = objectMapper.convertValue(claims, UserThreadLocal.class);
            ThreadLocalUtils.setUser(userThreadLocal);
            chain.doFilter(request,response);
        }catch(Exception e){
            log.error("authorization解析异常，{}",e.getMessage());
            httpServletResponse.setStatus(ErrorCode.UNAUTHORIZED.getCode());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(Result.Failed(e.getMessage())));
        }
    }

    @Override
    public void destroy() {
        jakarta.servlet.Filter.super.destroy();
        ThreadLocalUtils.removeUser();
        log.info("过滤器资源销毁");
    }
}
