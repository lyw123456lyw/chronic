package com.hzsf.chronicanalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzsf.chronicanalysis.user.entity.SysResourceRoleVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统资源角色关联表 Mapper 接口
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Mapper
public interface SysResourceRoleMapper extends BaseMapper<SysResourceRoleVo> {

}
