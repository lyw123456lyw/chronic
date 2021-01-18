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
 * 系统资源角色关联表
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_resource_role")
@ApiModel(value="SysResourceRoleVo对象", description="系统资源角色关联表")
public class SysResourceRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "资源ID")
    private Integer resourceId;

    @TableId( type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    private Integer id;


}
