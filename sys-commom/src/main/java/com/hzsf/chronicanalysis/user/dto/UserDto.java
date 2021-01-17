package com.hzsf.chronicanalysis.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Data
public class UserDto {

    @ApiModelProperty(value = "用户名",example = "张杰")
    private String username;

    @ApiModelProperty(value = "密码",example = "")
    private String password;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "备注信息")
    private String note;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty(value = "操作类型 1 新增;编辑 2 删除")
    private Integer operType;

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
}
