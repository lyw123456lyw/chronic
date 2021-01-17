package com.hzsf.chronicanalysis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzsf.chronicanalysis.mapper.SysUserMapper;
import com.hzsf.chronicanalysis.service.ISysResourceRoleService;
import com.hzsf.chronicanalysis.service.ISysRoleService;
import com.hzsf.chronicanalysis.service.ISysUserRoleService;
import com.hzsf.chronicanalysis.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzsf.chronicanalysis.user.dto.UserDto;
import com.hzsf.chronicanalysis.user.entity.*;
import com.hzsf.chronicanalysis.user.vo.LoginVo;
import com.hzsf.chronicanalysis.utils.ReqDedupHelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 慢性病防控用户表 服务实现类
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserVo> implements ISysUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登陆
     * @param customUser
     * @return
     */
    @Override
    public LoginVo login(CustomUser customUser) {
        SysUserVo user = this.getOne(new LambdaQueryWrapper<SysUserVo>().eq(SysUserVo::getUsername, customUser.getUsername()));
        if (null == user){
            throw new RuntimeException("不存在该用户");
        }else{
            //验证密码
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUser.getUsername(), customUser.getPassword());
//            SecurityContextHolder.getContext().setAuthentication(sampleAuthenticationManager.authenticate(authenticationToken));
            //查出用户拥有那些资源角色
            List<SysResourceVo> sysResourceVos = this.getBaseMapper().queryResourceListByUserId(user.getId());
            if (!sysResourceVos.isEmpty()){
                user.setResourceList(sysResourceVos);
            }
            List<SysMenuVo> sysMenuVoList = this.getBaseMapper().queryMenuListByUserId(user.getId());
            if (sysMenuVoList.isEmpty()){
//                throw new RuntimeException("请联系管理员分配菜单");
            }else{
                //在这里需要将菜单树转换为json树
                user.setMenuList(sysMenuVoList);
            }
            //将User信息转换为MD5存放在redis中,返回前端token
//            String userInfo = JSONObject.toJSONString(user);
//            String paramMd5 = ReqDedupHelperUtil.dedupParamMD5(userInfo);
//            String redisUserKey = ReqDedupHelperUtil.dedupParamMD5(String.valueOf(user.getId()));
//            redisTemplate.opsForValue().set(redisUserKey,paramMd5);
            LoginVo loginVo = new LoginVo();
            loginVo.setSysUser(user);
//            loginVo.setToken(redisUserKey);
            return loginVo;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean operUser(UserDto userDto) {
        if (userDto.getOperType() == 1){

        }else{

        }
        return null;
    }

    @Override
    public SysUserVo getUserInfo(Integer id) {
        return null;
    }
}
