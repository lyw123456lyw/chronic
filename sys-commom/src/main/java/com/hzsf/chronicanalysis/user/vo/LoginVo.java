package com.hzsf.chronicanalysis.user.vo;

import com.hzsf.chronicanalysis.user.entity.SysUserRoleVo;
import com.hzsf.chronicanalysis.user.entity.SysUserVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginVo {

    private SysUserVo sysUser;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("过期时间")
    private Long expire;
}
