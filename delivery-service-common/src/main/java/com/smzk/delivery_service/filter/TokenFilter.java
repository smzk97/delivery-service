package com.smzk.delivery_service.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smzk.delivery_service.entity.EmployeeThreadLocal;
import com.smzk.delivery_service.entity.Result;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.utils.JwtUtil;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter("/*")
public class TokenFilter implements Filter {

    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String URI = httpServletRequest.getRequestURI();
        String token = httpServletRequest.getHeader("token");

        if(URI.contains("/login") && (token == null || token.isEmpty())){
            log.info("用户未登录");
            chain.doFilter(request,response);
            return;
        }else if(token == null || token.isEmpty()){
            log.error("缺失token");
            httpServletResponse.setStatus(ErrorCode.UNAUTHORIZED.getCode());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(Result.Failed(ErrorCode.UNAUTHORIZED.getMsg())));
            return;
        }
        try{
            Claims claims = JwtUtil.parseToken(token);
            EmployeeThreadLocal employeeThreadLocal = objectMapper.convertValue(claims, EmployeeThreadLocal.class);
            ThreadLocalUtils.setEmployee(employeeThreadLocal);
            chain.doFilter(request,response);
        }catch(Exception e){
            log.error("token解析异常，{}",e.getMessage());
            httpServletResponse.setStatus(ErrorCode.UNAUTHORIZED.getCode());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(Result.Failed(e.getMessage())));
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("过滤器资源销毁");
    }
}
