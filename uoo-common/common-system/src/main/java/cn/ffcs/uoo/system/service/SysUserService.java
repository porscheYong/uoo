package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统域用户Service接口
 * Created by liuxiaodong on 2018/11/12.
 */
public interface SysUserService extends IService<SysUser> {

    Long getId();
    /**
     * 用户登录
     * @param sysUser 用户信息
     */
    String sysUserLogin(SysUser sysUser);

    /**
     * 检查用户注册
     * @param sysUser 用户信息
     */
    String checkRegister(SysUser sysUser);

    /**
     * 用户注册
     * @param sysUser 用户信息
     */
    void sysUserRegister(SysUser sysUser);


}
