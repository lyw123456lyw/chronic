package com.hzsf.chronicanalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzsf.chronicanalysis.user.entity.SysMenuRoleVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统菜单角色关联表 Mapper 接口
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Mapper
public interface SysMenuRoleMapper extends BaseMapper<SysMenuRoleVo> {

}
