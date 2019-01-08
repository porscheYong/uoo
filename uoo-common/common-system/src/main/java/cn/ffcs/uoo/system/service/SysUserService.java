package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.vo.SysUserDeptPositionVo;
import com.baomidou.mybatisplus.plugins.Page;
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

    /**
     * 新增人员，检验所以信息
     * @param sysUser
     * @return
     */
    String checkAllRegister(SysUser sysUser);

    /**
     * 新增人员
     * @param sysUser
     */
    Object addOrUpdateUser(SysUser sysUser);

    /**
     * 根据人员标识 获取人员信息
     * @param userId
     * @return
     */
    SysUser getSysUserById(Long userId);

    /**
     * 人员归属组织
     * @param userCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<SysUserDeptPositionVo> getUserDeptPosition(String userCode, Integer pageNo, Integer pageSize);
}
