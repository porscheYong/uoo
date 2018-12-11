package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.dao.SysDeptMapper;
import cn.ffcs.uoo.system.dao.SysLoginLogMapper;
import cn.ffcs.uoo.system.entity.SysDept;
import cn.ffcs.uoo.system.entity.SysLoginLog;
import cn.ffcs.uoo.system.service.SysDeptService;
import cn.ffcs.uoo.system.service.SysLoginLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 * 系统域用户Service实现类
 * Created by liuxiaodong on 2018/11/12.
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {


    @Autowired
    SysLoginLogMapper sysLoginLogMapper;

    @Override
    public Long getId() {
        return sysLoginLogMapper.getId();
    }
}
