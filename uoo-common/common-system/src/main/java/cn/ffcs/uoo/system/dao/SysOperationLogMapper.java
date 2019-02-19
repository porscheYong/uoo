package cn.ffcs.uoo.system.dao;


import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.system.entity.SysOperationLog;
import cn.ffcs.uoo.system.vo.LogDTO;
import cn.ffcs.uoo.system.vo.SysOperationLogVO;

public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {
    Long getId();
    List<LogDTO> listLog(Page<LogDTO> page,HashMap<String, Object> map); 
    Long countLog(HashMap<String, Object> map);
    SysOperationLogVO getVO(Long id); 
}