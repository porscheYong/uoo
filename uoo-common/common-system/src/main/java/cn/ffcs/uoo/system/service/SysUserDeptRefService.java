package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysUserDeptRef;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 对部门可选岗位的限定 服务类
 * </p>
 *
 * @author wudj
 * @since 2019-01-05
 */
public interface SysUserDeptRefService extends IService<SysUserDeptRef> {

    Long getId();

    /**
     * 新增
     * @param sysUserDeptRef
     * @return
     */
    public Object addSysUserDeptRef(SysUserDeptRef sysUserDeptRef);

    /**
     * 更新
     * @param sysUserDeptRef
     * @return
     */
    public Object updateSysUserDeptRef(SysUserDeptRef sysUserDeptRef);

    /**
     * 删除
     * @param sysUserDeptRef
     * @return
     */
    public Object delSysUserDeptRef(SysUserDeptRef sysUserDeptRef);

    /**
     * 根据userCode 获取用户组织信息
     * @param userCode
     * @return
     */
    public Page<SysUserDeptRef> getUserDeptRefByUserCode(String userCode, Integer pageNo, Integer pageSize);

    /**
     * 信息验证
     * @param sysUserDeptRef
     * @return
     */
    public String checkAllRegister(SysUserDeptRef sysUserDeptRef);

    /**
     * 删除
     * @param userCode
     * @param updateUser
     */
    public void delUserDeptDefByUserCode(String userCode, Long updateUser);


}
