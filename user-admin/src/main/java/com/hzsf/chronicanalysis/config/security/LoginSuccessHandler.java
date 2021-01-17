package com.hzsf.chronicanalysis.config.security;

import com.hzsf.chronicanalysis.ResponseStatusCode;
import com.hzsf.chronicanalysis.service.ISysUserService;
import com.hzsf.chronicanalysis.user.entity.CustomUser;
import com.hzsf.chronicanalysis.user.vo.LoginVo;
import com.hzsf.chronicanalysis.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ISysUserService sysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        LoginVo login = sysUserService.login(customUser);
        RequestUtils.customResponse(response, ResponseStatusCode.SUCCESS,login);
    }
}
