package com.hzsf.chronicanalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzsf.chronicanalysis.user.entity.SysRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 慢性病防控角色表 Mapper 接口
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleVo> {

    @Select("<script>" +
            "SELECT role.* FROM `sys_role`role inner join sys_user_role urole on urole.role_id = role.id" +
            "</script>")
    List<SysRoleVo> getRoleList(Integer userId);
}
