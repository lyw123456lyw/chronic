package com.hzsf.chronicanalysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzsf.chronicanalysis.user.entity.SysMenuVo;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
public interface ISysMenuService extends IService<SysMenuVo> {

    /**
     * 获取用户菜单树
     * @param userId
     * @return
     */
    List<SysMenuVo> getTree(Integer userId);


}
