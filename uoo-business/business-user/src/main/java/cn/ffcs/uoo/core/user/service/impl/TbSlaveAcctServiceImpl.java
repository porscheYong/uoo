package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.dao.TbSlaveAcctMapper;
import cn.ffcs.uoo.core.user.entity.ListUser;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import cn.ffcs.uoo.core.user.service.TbSlaveAcctService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 从账号 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service
public class TbSlaveAcctServiceImpl extends ServiceImpl<TbSlaveAcctMapper, TbSlaveAcct> implements TbSlaveAcctService {

    @Override
    public Long getId(){
       return baseMapper.getId();
    }

    @Override
    public List<ListUser> getUserList(Long slaveAcctId){
        return baseMapper.getUserList(slaveAcctId);
    }

    @Override
    public List<ListUser> getApplyUserList(Long slaveAcctId){ return  baseMapper.getApplyUserList(slaveAcctId); }

    @Override
    public List<TbAcct> getAcct(Long slaveAcctId){ return  baseMapper.getAcct(slaveAcctId); }
}
