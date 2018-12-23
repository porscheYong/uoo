package cn.ffcs.uoo.system.dao;


import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.system.entity.SysOperationLog;
import cn.ffcs.uoo.system.vo.LogDTO;

public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {
    Long getId();
    List<LogDTO> listLog(HashMap<String, Object> map); 
    Long countLog(HashMap<String, Object> map); 
}