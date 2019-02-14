package cn.ffcs.uoo.system.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.dao.SysOperationLogMapper;
import cn.ffcs.uoo.system.entity.SysOperationLog;
import cn.ffcs.uoo.system.service.SysOperationLogService;
import cn.ffcs.uoo.system.vo.LogDTO;


/*
 * 系统域用户Service实现类
 * Created by liuxiaodong on 2018/11/12.
 */
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements SysOperationLogService {


    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public List<LogDTO> listLog(Page<LogDTO> page,HashMap<String, Object> map) {
        return baseMapper.listLog(page,map);
    }

    @Override
    public Long countLog(HashMap<String, Object> map) {
        return baseMapper.countLog(map);
    }
}
