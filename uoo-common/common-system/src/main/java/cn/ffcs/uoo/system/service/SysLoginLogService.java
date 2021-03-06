package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysLoginLog;
import cn.ffcs.uoo.system.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * 系统域用户Service接口
 * Created by liuxiaodong on 2018/11/12.
 */
public interface SysLoginLogService extends IService<SysLoginLog> {
    Long getId();
}
