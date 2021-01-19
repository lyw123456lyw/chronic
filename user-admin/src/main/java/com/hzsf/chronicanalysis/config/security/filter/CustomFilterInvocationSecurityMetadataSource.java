package com.hzsf.chronicanalysis.config.security.filter;

import com.hzsf.chronicanalysis.service.ISysResourceService;
import com.hzsf.chronicanalysis.service.ISysRoleService;
import com.hzsf.chronicanalysis.user.entity.SysResourceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private ISysResourceService resourceService;

    @Autowired
    private ISysRoleService sysRoleService;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        Set<ConfigAttribute> set = new HashSet<>();
        // 获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequest().getRequestURI();
        log.info("requestUrl >> {}", requestUrl);
        //获取全部受限资源的URL
        List<SysResourceVo> resourceList = resourceService.list();
        if (!resourceList.isEmpty()){
            List<String> resourceUrl = resourceList.parallelStream().map(a -> a.getUrl()).collect(Collectors.toList());
            //访问当前URL所需要的角色集合
            for (String url : resourceUrl) {
                if (antPathMatcher.match(url,requestUrl)){
                    List<Integer> resourceOwendRole = sysRoleService.getResourceOwendRole(requestUrl);
                    resourceOwendRole.forEach(roleName -> {
                        SecurityConfig securityConfig = new SecurityConfig(String.valueOf(roleName));
                        set.add(securityConfig);
                    });
                }
            }
        }
        //防止数据库中没有数据，不能进行权限拦截
        if(set.size()<1){
            ConfigAttribute configAttribute = new SecurityConfig("ROLE_NO_USER");
            set.add(configAttribute);
        }
        return set;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
