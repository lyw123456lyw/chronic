package com.hzsf.chronicanalysis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hzsf.chronicanalysis.config.security.jwt.JwtConfig;
import com.hzsf.chronicanalysis.service.ITokenService;
import com.hzsf.chronicanalysis.user.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JWTokenServiceImpl implements ITokenService {
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public Boolean saveToken(CustomUser customUser) {
        String token = jwtConfig.createToken(JSONObject.toJSONString(customUser));
        customUser.setToken(token);
        customUser.setExpire(jwtConfig.getExpire());
        return true;
    }

    @Override
    public CustomUser getTokenInfo(String token) {
        String usernameFromToken = jwtConfig.getUsernameFromToken(token);
        return JSONObject.parseObject(usernameFromToken, CustomUser.class);
    }
}
