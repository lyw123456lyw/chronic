package com.hzsf.chronicanalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzsf.chronicanalysis.user.entity.SysResourceVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统资源表 Mapper 接口
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Mapper
public interface SysResourceMapper extends BaseMapper<SysResourceVo> {

}
