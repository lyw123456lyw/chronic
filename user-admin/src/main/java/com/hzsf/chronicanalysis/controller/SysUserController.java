package com.hzsf.chronicanalysis.controller;


import com.hzsf.chronicanalysis.service.ISysRoleService;
import com.hzsf.chronicanalysis.service.ISysUserRoleService;
import com.hzsf.chronicanalysis.service.ISysUserService;
import com.hzsf.chronicanalysis.user.dto.UserDto;
import com.hzsf.chronicanalysis.user.vo.LoginVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 慢性病防控用户表 前端控制器
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@RestController
@RequestMapping("/sys-user-vo")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;


    public Boolean operUser(@RequestBody UserDto userDto){
        return null;
    }

    @GetMapping("login")
    @ApiOperation("登陆接口")
    public String login(@RequestBody UserDto userDto){
        System.out.println("123");
        return "null";
    }

    @GetMapping("test")
    @ApiOperation("测试接口")
    public String login(){
        System.out.println("夫士大夫士大夫");
        return "null";
    }

}
