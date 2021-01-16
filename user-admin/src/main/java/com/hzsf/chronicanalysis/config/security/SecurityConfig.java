package com.hzsf.chronicanalysis.config.security;

import com.fasterxml.jackson.core.filter.TokenFilter;
import com.hzsf.chronicanalysis.config.security.exception.SecurityAccessDeniedHandler;
import com.hzsf.chronicanalysis.config.security.exception.SecurityAuthenticationEntryPoint;
import com.hzsf.chronicanalysis.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     *客户端service
     */
    @Autowired
    private ISysUserService sysUserService;
    /**
     *设置密码解码器
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     *未登录处理器
     */
    @Autowired
    private SecurityAuthenticationEntryPoint authenticationEntryPoint;
    /**
     *授权失败处理器
     */
    @Autowired
    private SecurityAccessDeniedHandler accessDeniedHandler;
    /**
     *每次登陆的时候校验token
     */
    @Autowired
    private TokenFilter tokenFilter;




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 默认是启用的，需要禁用CSRF保护
        http.csrf().disable();
        // 基于token，所以不需要session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //允许oauth2端点和登陆,登出端口的访问
        http.authorizeRequests().antMatchers("/v1.0/pb/auth/**").permitAll();
        http.authorizeRequests().antMatchers("/v1.0/pb/alipay/**").permitAll();
        http.authorizeRequests().antMatchers("/oauth/**").permitAll();
        http.authorizeRequests()
                // 阿里巴巴 druid
                .antMatchers("/druid/**").permitAll()
                // swagger 文档
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                .anyRequest().authenticated();
        //访问受限资源异常处理
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        //未登访问异常处理
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        // 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        http.addFilterBefore(tokenFilter,UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }



    /**
     * 配置的是认证信息, AuthenticationManagerBuilder 这个类,就是AuthenticationManager的建造者,
     * 我们只需要向这个类中, 配置用户信息, 就能生成对应的AuthenticationManager,
     * 这个类也提过,是用户身份的管理者, 是认证的入口,
     * 因此,我们需要通过这个配置,向security提供真实的用户身份。
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Autowired
    private SampleAuthenticationManager sampleAuthenticationManager;

    /**
     * 把AuthenticationManager暴露为bean
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return sampleAuthenticationManager;
    }
}
