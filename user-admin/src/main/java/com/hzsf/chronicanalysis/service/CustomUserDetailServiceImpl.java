package com.hzsf.chronicanalysis.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzsf.chronicanalysis.user.entity.CustomUser;
import com.hzsf.chronicanalysis.user.entity.SysUserRoleVo;
import com.hzsf.chronicanalysis.user.entity.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CustomUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysUserRoleService userRoleService;

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
            Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
            List<SysUserRoleVo> roleList = userRoleService.list(new LambdaQueryWrapper<SysUserRoleVo>().eq(SysUserRoleVo::getAdminId, sysUser.getId()));
            if (!roleList.isEmpty()){
                CustomUser customUser = new CustomUser();
                customUser.setPassword(sysUser.getPassword());
                customUser.setUsername(sysUser.getUsername());
                customUser.setRoleList(roleList);
                return customUser;
            }else{
                CustomUser customUser = new CustomUser();
                customUser.setPassword(sysUser.getPassword());
                customUser.setUsername(sysUser.getUsername());
                return customUser;            }
        }
    }
}
