                                  //package com.hzsf.chronicanalysis.config.security;
//
//import com.hzsf.chronicanalysis.service.ISysUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// *用来自定义密码验证管理
// */
//@Primary
//@Component("sampleAuthenticationManager")
//public class SampleAuthenticationManager implements AuthenticationManager {
//
//    @Autowired
//    private ISysUserService sysUserService;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//
//    @Override
//    public Authentication authenticate(Authentication auth) throws AuthenticationException {
//        UserVo sysUser = userApi.getUser(auth.getName()).getData();
//        if (sysUser == null){
//            throw new BadCredentialsException("Bad Credentials");
//        }
//        LoginUser loginUser = new LoginUser();
//        loginUser.setUsername(sysUser.getUsername());
//        loginUser.setPassword(sysUser.getPassword());
//        // 这里我们自定义了验证通过条件：username与password相同就可以通过认证
//        if (auth.getName().equals(loginUser.getUsername()) && passwordEncoder.matches((String)auth.getCredentials(),loginUser.getPassword())) {
//             return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), loginUser.getAuthorities());
//        }
//        // 没有通过认证则抛出密码错误异常
//        throw new BadCredentialsException("Bad Credentials");
//    }
//}
