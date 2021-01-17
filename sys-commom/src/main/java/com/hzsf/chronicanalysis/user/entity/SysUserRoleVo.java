package com.hzsf.chronicanalysis.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户角色关联表
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_role")
@ApiModel(value="SysUserRoleVo对象", description="用户角色关联表")
public class SysUserRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "用户Id")
    private Long adminId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @TableId( type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    private Integer id;


}
