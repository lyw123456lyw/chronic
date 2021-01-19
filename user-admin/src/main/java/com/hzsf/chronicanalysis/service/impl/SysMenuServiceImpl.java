package com.hzsf.chronicanalysis.service.impl;

import com.hzsf.chronicanalysis.mapper.SysMenuMapper;
import com.hzsf.chronicanalysis.mapper.SysUserMapper;
import com.hzsf.chronicanalysis.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzsf.chronicanalysis.service.ISysUserService;
import com.hzsf.chronicanalysis.user.entity.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author 张杰
 * @since 2021-01-16
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuVo> implements ISysMenuService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysMenuVo> getTree(Integer userId) {
        List<SysMenuVo> sysMenuVoList = sysUserMapper.queryMenuListByUserId(userId);
        List<SysMenuVo> topList = sysMenuVoList.parallelStream().filter(a -> a.getParentId().equals(0)).collect(Collectors.toList());
        topList.parallelStream().forEach(a->setChild(a,sysMenuVoList));
        return topList;
    }

    public void setChild(SysMenuVo sysMenuVo,List<SysMenuVo> sysMenuVoList){
        List<SysMenuVo> child = sysMenuVoList.parallelStream().filter(
                a -> {
                    Long aLong = Long.valueOf(String.valueOf(a.getParentId()));
                    Long kid = Long.valueOf(String.valueOf(sysMenuVo.getId())) ;
                    return aLong.equals(kid) ;
                }).collect(Collectors.toList());
        sysMenuVo.setChildren(child);
        if (!CollectionUtils.isEmpty(child)) {
            child.stream().forEach(c -> {
                setChild(c, sysMenuVoList);
            });
        }
    }
}
