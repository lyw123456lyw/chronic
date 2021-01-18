package com.hzsf.chronicanalysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzsf.chronicanalysis.user.entity.SysRoleVo;

import java.util.List;

/**
 * <p>
 * 慢性病防控角色表 服务类
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
public interface ISysRoleService extends IService<SysRoleVo> {
    /**
     * 获取当前资源访问所需要的角色信息
     * @return
     */
    List<Integer> getResourceOwendRole(String url);
}
