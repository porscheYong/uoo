package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.dao.TbOrgMapper;
import cn.ffcs.interfaces.cpc.pojo.TbOrg;
import cn.ffcs.interfaces.cpc.service.TbOrgService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author lxd
 * @since 2019-01-10
 */
@Service
public class TbOrgServiceImpl extends ServiceImpl<TbOrgMapper, TbOrg> implements TbOrgService {
    @Override
    public void insertChannel(TbOrg tbOrg) {
        baseMapper.insertChannel(tbOrg);
    }
}
