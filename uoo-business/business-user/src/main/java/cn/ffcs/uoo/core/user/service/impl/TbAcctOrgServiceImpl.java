package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.dao.TbAcctOrgMapper;
import cn.ffcs.uoo.core.user.entity.TbAcctOrg;
import cn.ffcs.uoo.core.user.service.TbAcctOrgService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 主账号与组织关系 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service
public class TbAcctOrgServiceImpl extends ServiceImpl<TbAcctOrgMapper, TbAcctOrg> implements TbAcctOrgService {

    @Resource
    private TbAcctOrgMapper tbAcctOrgMapper;

    @Override
    public long saveAcctOrg(TbAcctOrg tbAcctOrg) {
        return tbAcctOrgMapper.save(tbAcctOrg);
    }

    @Override
    public void removeAcctOrg(TbAcctOrg tbAcctOrg) {
        tbAcctOrgMapper.delete(tbAcctOrg);
    }
}
