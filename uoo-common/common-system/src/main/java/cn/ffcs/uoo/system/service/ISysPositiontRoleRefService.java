package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysPositiontRoleRef;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 对一定职位可以默认具备一些角色 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-20
 */
public interface ISysPositiontRoleRefService extends IService<SysPositiontRoleRef> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param sysPositiontRoleRef
     */
    public void delete(SysPositiontRoleRef sysPositiontRoleRef);

    /**
     * 新增
     */
    public void add(SysPositiontRoleRef sysPositiontRoleRef);

    /**
     * 更新
     */
    public void update(SysPositiontRoleRef sysPositiontRoleRef);


    public List<SysPositiontRoleRef> getCurRoleList(String positionCode);

}
