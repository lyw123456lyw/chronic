package com.hzsf.chronicanalysis.config.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.hzsf.chronicanalysis.config.security.jwt.JwtConfig;
import com.hzsf.chronicanalysis.service.ITokenService;
import com.hzsf.chronicanalysis.user.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtConfig jwtConfig ;

    @Autowired
    ITokenService tokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取header中的验证信息
        String authHeader = request.getHeader("token");
        if (authHeader != null ) {
            CustomUser customUser = tokenService.getTokenInfo(authHeader);
            Collection<? extends GrantedAuthority> authorities = customUser.getAuthorities();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUser,
                    null, customUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
