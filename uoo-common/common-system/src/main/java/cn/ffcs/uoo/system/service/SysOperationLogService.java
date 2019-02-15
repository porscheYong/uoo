package cn.ffcs.uoo.system.service;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.system.entity.SysOperationLog;
import cn.ffcs.uoo.system.vo.LogDTO;
import cn.ffcs.uoo.system.vo.SysOperationLogVO;

/**
 * 系统域用户Service接口
 * Created by liuxiaodong on 2018/11/12.
 */
public interface SysOperationLogService extends IService<SysOperationLog> {
    Long getId();
    List<LogDTO> listLog(Page<LogDTO> page,HashMap<String, Object> map); 
    Long countLog(HashMap<String, Object> map); 
    SysOperationLogVO getVO(Long id);
}
