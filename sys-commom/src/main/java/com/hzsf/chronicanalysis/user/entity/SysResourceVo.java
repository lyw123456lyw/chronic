package com.hzsf.chronicanalysis.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统资源表
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_resource")
@ApiModel(value="SysResourceVo对象", description="系统资源表")
public class SysResourceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源URL")
    private String url;

    @ApiModelProperty(value = "描述")
    private String description;

    @TableId( type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    private Integer id;


}
