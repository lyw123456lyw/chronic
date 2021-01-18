package com.hzsf.chronicanalysis.config.security;

import com.alibaba.fastjson.JSONObject;
import com.hzsf.chronicanalysis.ResponseStatusCode;
import com.hzsf.chronicanalysis.config.security.jwt.JwtConfig;
import com.hzsf.chronicanalysis.service.ISysUserService;
import com.hzsf.chronicanalysis.service.ITokenService;
import com.hzsf.chronicanalysis.service.impl.JWTokenServiceImpl;
import com.hzsf.chronicanalysis.user.entity.CustomUser;
import com.hzsf.chronicanalysis.user.vo.LoginVo;
import com.hzsf.chronicanalysis.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ISysUserService sysUserService;


    @Autowired
    private JWTokenServiceImpl tokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        tokenService.saveToken(customUser);
        LoginVo login = sysUserService.login(customUser);
        login.setToken(customUser.getToken());
        login.setExpire(customUser.getExpire());
        RequestUtils.customResponse(response, ResponseStatusCode.SUCCESS,login);
    }
}
