package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysDept;
import cn.ffcs.uoo.system.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * 系统域用户Service接口
 * Created by liuxiaodong on 2018/11/12.
 */
public interface SysDeptService extends IService<SysDept> {
    Long getId();
}
