package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysUserPositionRef;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 平台用户职位关系 服务类
 * </p>
 *
 * @author wudj
 * @since 2019-01-05
 */
public interface SysUserPositionRefService extends IService<SysUserPositionRef> {

    Long getId();

    /**
     * 新增
     * @param sysUserPositionRef
     * @return
     */
    public Object addSysUserPositionRef(SysUserPositionRef sysUserPositionRef);

    /**
     * 更新
     * @param sysUserPositionRef
     * @param userCode
     * @param orgCode
     * @param updateUser
     * @return
     */
    public Object updateSysUserPositionRef(List<SysUserPositionRef> sysUserPositionRef, String userCode, String orgCode, Long updateUser);

    /**
     * 删除
     * @param userCode
     * @param orgCode
     * @param updateuser
     * @return
     */
    public Object delSysUserPositionRef(String userCode, String orgCode, Long updateuser);

    /**
     * 获取用户职位
     * @param userCode
     * @param orgCode
     * @return
     */
    public List<SysUserPositionRef> getUserPositionRef(String userCode, String orgCode);

    /**
     * 删除
     * @param userCode
     * @param updateUser
     */
    public void delUserPositionDefByUserCode(String userCode, Long updateUser);
}
