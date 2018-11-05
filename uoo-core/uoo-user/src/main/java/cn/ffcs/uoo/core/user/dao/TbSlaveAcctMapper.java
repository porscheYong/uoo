package cn.ffcs.uoo.core.user.dao;

import cn.ffcs.uoo.core.user.entity.ListUser;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 从账号 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbSlaveAcctMapper extends BaseMapper<TbSlaveAcct> {

    public Long getId();

    public List<ListUser> getUserList(Long slaveAcctId);

    public List<ListUser> getApplyUserList(Long slaveAcctId);

    public List<TbAcct> getAcct(Long slaveAcctId);

}
