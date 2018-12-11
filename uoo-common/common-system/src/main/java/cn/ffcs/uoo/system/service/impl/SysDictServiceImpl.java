package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.dao.SysDeptMapper;
import cn.ffcs.uoo.system.dao.SysDictMapper;
import cn.ffcs.uoo.system.entity.SysDept;
import cn.ffcs.uoo.system.entity.SysDict;
import cn.ffcs.uoo.system.service.SysDeptService;
import cn.ffcs.uoo.system.service.SysDictService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 * 系统域用户Service实现类
 * Created by liuxiaodong on 2018/11/12.
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    SysDictMapper sysDictMapper;

    @Override
    public Long getId() {
        return sysDictMapper.getId();
    }
}
