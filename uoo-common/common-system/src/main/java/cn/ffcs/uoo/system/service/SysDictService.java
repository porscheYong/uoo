package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysDict;
import cn.ffcs.uoo.system.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

/**
 * 系统域用户Service接口
 * Created by liuxiaodong on 2018/11/12.
 */
public interface SysDictService extends IService<SysDict> {
    Long getId();
}