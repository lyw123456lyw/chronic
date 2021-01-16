package com.hzsf.chronicanalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzsf.chronicanalysis.user.entity.SysMenuVo;
import com.hzsf.chronicanalysis.user.entity.SysResourceVo;
import com.hzsf.chronicanalysis.user.entity.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 慢性病防控用户表 Mapper 接口
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
public interface SysUserMapper extends BaseMapper<SysUserVo> {

    /**
     * SELECT
     *      resource.*,
     *      rrole.role_id
     *      FROM
     *      sys_resource resource
     *      INNER JOIN sys_resource_role rrole ON rrole.resource_id = resource.id
     *      WHERE
     *      rrole.role_id IN ( SELECT urole.id FROM sys_user sysuser INNER JOIN sys_user_role urole ON urole.role_id = sysuser.id )
     * @param userId
     * @return
     */
    @Select("<script>" +
            "SELECT\n" +
            "     resource.*,\n" +
            "     rrole.role_id\n" +
            "     FROM\n" +
            "     sys_resource resource\n" +
            "     INNER JOIN sys_resource_role rrole ON rrole.resource_id = resource.id\n" +
            "     WHERE\n" +
            "     rrole.role_id IN ( SELECT urole.id FROM sys_user sysuser INNER JOIN sys_user_role urole ON urole.role_id = sysuser.id " +
            "<where>" +
            "<if test = \"userId != null\">" +
            "sysuser.id = #{userId}\n" +
            "</if>" +
            "</where>" +
            ")" +
            "<script>")
    List<SysResourceVo> queryResourceListByUserId(@Param("userId") Integer userId);

    /**
     * SELECT
     * 	menu.*,
     * 	mrole.role_id
     * FROM
     * 	sys_menu menu
     * 	INNER JOIN sys_menu_role mrole ON mrole.menu_id = menu.id
     * WHERE
     * 	mrole.role_id IN ( SELECT urole.id FROM sys_user sysuser INNER JOIN sys_user_role urole ON urole.role_id = sysuser.id )
     */
    @Select("<script>" +
            "SELECT\n" +
            "     menu.*,\n" +
            "     mrole.role_id\n" +
            "     FROM\n" +
            "     sys_menu menu\n" +
            "     INNER JOIN sys_menu_role mrole ON mrole.menu_id = menu.id \n" +
            "     WHERE\n" +
            "     rrole.role_id IN ( SELECT urole.id FROM sys_user sysuser INNER JOIN sys_user_role urole ON urole.role_id = sysuser.id " +
            "<where>" +
            "<if test = \"userId != null\">" +
            "sysuser.id = #{userId}\n" +
            "</if>" +
            "</where>" +
            ")" +
            "<script>")
    List<SysMenuVo> queryMenuListByUserId(@Param("userId") Integer userId);
}
