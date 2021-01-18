package com.hzsf.chronicanalysis.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CustomUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private String token;

    private Long expire;

    private List<SysUserRoleVo> roleList;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roleList == null){
            return null;
        }else{
            return roleList.parallelStream()
                    .map(p -> new SimpleGrantedAuthority(String.valueOf(p.getRoleId()))).collect(Collectors.toSet());
        }
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        // do nothing
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
