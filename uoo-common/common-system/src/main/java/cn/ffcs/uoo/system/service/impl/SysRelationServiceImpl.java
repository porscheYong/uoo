package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.dao.SysMenuMapper;
import cn.ffcs.uoo.system.dao.SysRelationMapper;
import cn.ffcs.uoo.system.entity.SysMenu;
import cn.ffcs.uoo.system.entity.SysRelation;
import cn.ffcs.uoo.system.service.SysMenuService;
import cn.ffcs.uoo.system.service.SysRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 * 系统域用户Service实现类
 * Created by liuxiaodong on 2018/11/12.
 */
@Service
public class SysRelationServiceImpl extends ServiceImpl<SysRelationMapper, SysRelation> implements SysRelationService {

    @Autowired
    SysRelationMapper sysRelationMapper;

    @Override
    public Long getId() {
        return sysRelationMapper.getId();
    }
}
