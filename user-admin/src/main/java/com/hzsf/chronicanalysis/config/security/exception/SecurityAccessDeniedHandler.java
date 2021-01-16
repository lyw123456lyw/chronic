package com.hzsf.chronicanalysis.config.security.exception;

import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
 * 自定义访问受限资源时没有得到许可抛出的异常
 */
@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseUtil.responseJson(response,new Response(true,"403","您访问的页面没有权限",""));
    }
}
