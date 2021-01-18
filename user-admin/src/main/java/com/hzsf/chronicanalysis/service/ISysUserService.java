package com.hzsf.chronicanalysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzsf.chronicanalysis.user.dto.UserDto;
import com.hzsf.chronicanalysis.user.entity.CustomUser;
import com.hzsf.chronicanalysis.user.entity.SysUserVo;
import com.hzsf.chronicanalysis.user.vo.LoginVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 慢性病防控用户表 服务类
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
public interface ISysUserService extends IService<SysUserVo> {
    /**
     * 登录接口需要返回的东西
     * @return
     */
    LoginVo login(CustomUser customUser);

    /**
     * 用户的增删改
     * @param userDto
     * @return
     */
    Boolean operUser(@RequestBody UserDto userDto);

    /**
     * 查询用户信息
     * @param id
     * @return
     */
    SysUserVo getUserInfo(Integer id);

    List<String> getResouceUrlList(String userName);
}
