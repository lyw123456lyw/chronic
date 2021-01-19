package com.hzsf.chronicanalysis.controller;


import com.hzsf.chronicanalysis.service.ISysRoleService;
import com.hzsf.chronicanalysis.user.entity.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 慢性病防控角色表 前端控制器
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/sys-role-vo")
public class SysRoleController {
    @Autowired
    private ISysRoleService roleService;


    @PostMapping("/getRoleList")
    public List<SysRoleVo> getRoleList(){
        return roleService.getRoleList();
    }

}
