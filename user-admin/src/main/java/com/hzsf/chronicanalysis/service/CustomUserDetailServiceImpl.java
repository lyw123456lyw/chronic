package com.hzsf.chronicanalysis.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzsf.chronicanalysis.user.entity.CustomUser;
import com.hzsf.chronicanalysis.user.entity.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class CustomUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService userService;

    /**
     * UserDetailsService接口用户返回用户相关数据。它有loadUserByUsername方法，根据用户名查询用户实体，可以实现该接口覆盖该方法，实现
     * 自定义获取用户过程。该接口实现类被DaoAuthenticationProvider类使用，用于认证过程中载入用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserVo sysUser = userService.getOne(new LambdaQueryWrapper<SysUserVo>().eq(SysUserVo::getUsername, username));
        if (null == sysUser){
            throw new RuntimeException("不存在该用户");
        }else if (sysUser.getStatus() == 0){
            throw new RuntimeException("请联系管理员解禁");
        }else{
            return new CustomUser(sysUser.getUsername(),sysUser.getPassword());
        }
    }
}
