//package com.hzsf.chronicanalysis.config.security;
//
//
//import com.zykj.platform.authorization.config.oauth2.AuthenticationServerConfig;
//import com.zykj.platform.authorization.service.impl.UserDetailsServiceImpl;
//import com.zykj.platform.authorization.utils.ResponseUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.binary.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
//import org.springframework.security.oauth2.provider.*;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//
//@Configuration
//@Slf4j
//public class SecurityHandlerConfig {
//    @Autowired
//    private ClientDetailsService clientDetailsService;
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    private AuthenticationServerConfig config;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthorizationServerTokenServices authorizationServerTokenServices;
//
//    /**
//     *表单登录成功处理器
//     */
//    @Bean
//    public AuthenticationSuccessHandler loginSuccessHandler(){
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                log.info("登陆成功");
//                //登陆成功之后需要调用oauth2中返回的令牌给前端
//                UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
//                String header = request.getHeader("Authorization");
//                if (header == null && !header.startsWith("Basic")) {
//                    throw new UnapprovedClientAuthenticationException("请求投中无client信息");
//                }
//                String[] tokens = ResponseUtil.extractAndDecodeHeader(header,request);
//                String clientId = tokens[0];
//                String clientSecret = tokens[1];
//                ClientDetails client = clientDetailsService.loadClientByClientId(clientId);
//                if (client == null){
//                    throw new UnapprovedClientAuthenticationException("clientId 不存在"+clientId);
//                    //判断  方言  是否一致
//                }else if (!StringUtils.equals(client.getClientSecret(),clientSecret)){
//                    throw new UnapprovedClientAuthenticationException("clientSecret 不匹配"+clientId);
//                }
//                //密码授权 模式, 组建 authentication
//                HashMap<String, String> map = new HashMap<>();
//                map.put("username",userDetails.getUsername());
//                map.put("password",userDetails.getPassword());
//                TokenRequest tokenRequest = new TokenRequest(map,clientId,client.getScope(),"password");
//                OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(client);
//                OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
//                OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
//                //返回前端
//                ResponseUtil.responseJson(response, HttpStatus.OK.value(), token);
//            }
//        };
//    }
//
//    public static void main(String[] args) {
//        ResourceServerTokenServices resourceServerTokenServices = new DefaultTokenServices();
//        resourceServerTokenServices.loadAuthentication("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib3JkZXItc2VydmVyIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTU5NjQzMzA5NiwiYXV0aG9yaXRpZXMiOlsic3lzOnVzZXI6cGFzc3dvcmQiLCJqb2I6cXVlcnkiLCJzeXM6bWVudTphZGQiLCJzeXM6dXNlcjphZGQiLCJnZW5lcmF0ZTplZGl0Iiwibm90aWNlOnF1ZXJ5IiwiZGljdDphZGQiLCJleGNlbDpzaG93OmRhdGFzIiwiZGljdDpkZWwiLCJkaWN0OnF1ZXJ5Iiwic3lzOmxvZzpxdWVyeSIsInN5czpmaWxlOmRlbCIsInN5czpyb2xlOnF1ZXJ5Iiwiam9iOmFkZCIsInN5czpyb2xlOmFkZCIsInN5czpyb2xlOmRlbCIsIm5vdGljZTpkZWwiLCJleGNlbDpkb3duIiwic3lzOm1lbnU6ZGVsIiwic3lzOnVzZXI6cXVlcnkiLCJzeXM6ZmlsZTpxdWVyeSIsIm1haWw6YWxsOnF1ZXJ5Iiwic3lzOm1lbnU6cXVlcnkiLCJqb2I6ZGVsIiwibm90aWNlOmFkZCIsIm1haWw6c2VuZCJdLCJqdGkiOiJkNmI1ODljYS02ZjQxLTQyYmEtODMwNi1lYTdkNDViODc0ZTYiLCJjbGllbnRfaWQiOiJjbGllbnRfbmFtZSJ9.vc9XA8mxQhXUQGIucvC3acN4NobQaso5qqPPwSFEAME");
//
//    }
//
//    /**
//     *表单登陆失败处理器
//     */
//    @Bean
//    public AuthenticationFailureHandler loginFailureHandler(){
//        return new AuthenticationFailureHandler() {
//            @Override
//            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//                ResponseUtil.responseJson(response, HttpStatus.OK.value(), "密码错误");
//            }
//        };
//    }
//
//    /**
//     *未登录情况下,访问受限资源处理器
//     */
//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint(){
//        return new AuthenticationEntryPoint() {
//            @Override
//            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//                ResponseUtil.responseJson(response, HttpStatus.OK.value(), "请登录");
//            }
//        };
//    }
//
//    /**
//     *退出登录成功处理器
//     */
//    @Bean
//    public LogoutSuccessHandler logoutSussessHandler(){
//        return new LogoutSuccessHandler() {
//            @Override
//            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//
//            }
//        };
//    }
//}
