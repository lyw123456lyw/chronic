package com.hzsf.chronicanalysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzsf.chronicanalysis.mapper.SysRoleMapper;
import com.hzsf.chronicanalysis.response.exception.BusinessException;
import com.hzsf.chronicanalysis.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzsf.chronicanalysis.user.entity.*;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 慢性病防控角色表 服务实现类
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleVo> implements ISysRoleService {
    @Autowired
    private ISysResourceService resourceService;

    @Autowired
    private ISysResourceRoleService resourceRoleService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Override
    public List<Integer> getResourceOwendRole(String url) {
        SysResourceVo resource = resourceService.getOne(new LambdaQueryWrapper<SysResourceVo>().eq(SysResourceVo::getUrl, url));
        if (resource == null){
            return null;
        }else{
            List<SysResourceRoleVo> list = resourceRoleService.list(new LambdaQueryWrapper<SysResourceRoleVo>().eq(SysResourceRoleVo::getResourceId, resource.getId()));
            if (list.isEmpty()){
                return null;
            }else{
                List<Integer> collect = list.parallelStream().map(a -> a.getRoleId()).collect(Collectors.toList());
                return collect;
            }
        } }

    @Override
    public List<SysRoleVo> getRoleList() {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (null == customUser){
            throw new RuntimeException("用户信息异常");
        }else{
            SysUserVo user = sysUserService.getOne(new LambdaQueryWrapper<SysUserVo>().eq(SysUserVo::getUsername, customUser.getUsername()));
            return  this.getBaseMapper().getRoleList(user.getId());
        }

    }
}
