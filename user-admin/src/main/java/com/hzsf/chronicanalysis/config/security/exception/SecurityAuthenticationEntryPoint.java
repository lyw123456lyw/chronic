package com.hzsf.chronicanalysis.config.security.exception;


import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义处理未登录的情况抛出去的异常
 *当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
 */
@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ResponseUtil.responseJson(response,new Response(true,"401","用户名或密码错误",""));
    }
}
