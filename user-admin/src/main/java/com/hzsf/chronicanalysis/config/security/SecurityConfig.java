package com.hzsf.chronicanalysis.config.security;

//import com.hzsf.chronicanalysis.config.security.exception.SecurityAccessDeniedHandler;
//import com.hzsf.chronicanalysis.config.security.exception.SecurityAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzsf.chronicanalysis.ResponseStatusCode;
import com.hzsf.chronicanalysis.config.security.filter.CustomAccessDecisionManager;
import com.hzsf.chronicanalysis.config.security.filter.CustomFilterInvocationSecurityMetadataSource;
import com.hzsf.chronicanalysis.config.security.filter.TokenFilter;
import com.hzsf.chronicanalysis.config.security.handler.LoginFailureHandler;
import com.hzsf.chronicanalysis.config.security.handler.LoginSuccessHandler;
import com.hzsf.chronicanalysis.config.security.way.JsonFormateLoginFilter;
import com.hzsf.chronicanalysis.response.FR;
import com.hzsf.chronicanalysis.service.CustomUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     *客户端service
     */
    @Autowired
    private CustomUserDetailServiceImpl customUserDetailService;
    /**
     *设置密码解码器
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private CustomFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Resource
    private CustomAccessDecisionManager accessDecisionManager;

    @Autowired
    private TokenFilter tokenFilter;



    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/", "/*.html", "/favicon.ico", "/css/**", "/js/**", "/fonts/**", "/layui/**", "/img/**",
                "/v2/api-docs/**", "/swagger-resources/**", "/webjars/**", "/pages/**", "/druid/**",
                "/statics/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 默认是启用的，需要禁用CSRF保护
        http.csrf().disable();
        // 基于token，所以不需要session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // url权限认证处理
        http.authorizeRequests().anyRequest().authenticated().withObjectPostProcessor(filterSecurityInterceptorObjectPostProcessor());
        //访问受限资源异常处理
        http.exceptionHandling().accessDeniedHandler((request, response, ex) -> {
            customResponse(response, ResponseStatusCode.SUCCESS, FR.failUnAuth(null));
        });
        //未登访问异常处理
        http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
            customResponse(response, ResponseStatusCode.SUCCESS, FR.failUnLogin(null));
        });
        // 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        //采用JSON格式的表单登陆
        http.addFilterAt(jsonFormateLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        //用于在验证密码前通过token刷新用户信息
        http.addFilterBefore(tokenFilter,UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    /**
     * 自定义 FilterSecurityInterceptor  ObjectPostProcessor 以替换默认配置达到动态权限的目的
     *
     * @return ObjectPostProcessor
     */
    private ObjectPostProcessor<FilterSecurityInterceptor> filterSecurityInterceptorObjectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setAccessDecisionManager(accessDecisionManager);
                object.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                return object;
            }
        };
    }

    @Bean
    public JsonFormateLoginFilter jsonFormateLoginFilter() throws Exception {
        JsonFormateLoginFilter jsonFormateLoginFilter = new JsonFormateLoginFilter();
        jsonFormateLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        jsonFormateLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);
        jsonFormateLoginFilter.setFilterProcessesUrl("/sys-user-vo/login");
        jsonFormateLoginFilter.setAuthenticationManager(authenticationManagerBean());
        return jsonFormateLoginFilter;
    }

    @Autowired
    private ObjectMapper objectMapper;

    private void customResponse(HttpServletResponse response, ResponseStatusCode rsc, Object e) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(rsc.getCode());
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(e));
        out.flush();
        out.close();
    }

    /**
     * 配置的是认证信息, AuthenticationManagerBuilder 这个类,就是AuthenticationManager的建造者,
     * 我们只需要向这个类中, 配置用户信息, 就能生成对应的AuthenticationManager,
     * 这个类也提过,是用户身份的管理者, 是认证的入口,
     * 因此,我们需要通过这个配置,向security提供真实的用户身份。
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
}
