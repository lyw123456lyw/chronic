package com.hzsf.chronicanalysis.controller;


import com.hzsf.chronicanalysis.service.ISysMenuService;
import com.hzsf.chronicanalysis.user.entity.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/sys-menu-vo")
public class SysMenuController {
    @Autowired
    private ISysMenuService sysMenuService;

    @PostMapping("/menuTree")
    public List<SysMenuVo> getMenuList(Integer userId){
        return sysMenuService.getTree(1);
    }

}
