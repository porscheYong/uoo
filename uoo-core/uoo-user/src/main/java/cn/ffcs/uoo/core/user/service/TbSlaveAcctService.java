package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.ListUser;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 从账号 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbSlaveAcctService extends IService<TbSlaveAcct> {

    public Long getId();

    //从账号(资源)关联用户信息
    public List<ListUser> getUserList(Long slaveAcctId);

    //从账号(应用)关联用户信息
    public List<ListUser> getApplyUserList(Long slaveAcctId);

    //从账号(应用)关联主账号
    public List<TbAcct> getAcct(Long slaveAcctId);

}
