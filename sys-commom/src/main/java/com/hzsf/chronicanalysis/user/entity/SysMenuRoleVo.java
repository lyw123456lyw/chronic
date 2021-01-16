package com.hzsf.chronicanalysis.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 系统菜单角色关联表
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu_role")
@ApiModel(value="SysMenuRoleVo对象", description="系统菜单角色关联表")
public class SysMenuRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;


}
