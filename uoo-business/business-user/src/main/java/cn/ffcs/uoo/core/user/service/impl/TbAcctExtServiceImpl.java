package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.dao.TbAcctExtMapper;
import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import cn.ffcs.uoo.core.user.service.TbAcctExtService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 主账号扩展 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service
public class TbAcctExtServiceImpl extends ServiceImpl<TbAcctExtMapper, TbAcctExt> implements TbAcctExtService {

    @Resource
    private TbAcctExtMapper tbAcctExtMapper;

    @Override
    public void removeAcctExt(TbAcctExt tbAcctExt) {
        tbAcctExtMapper.delete(tbAcctExt);
    }

    @Override
    public Long getId(){
        return baseMapper.getId();
    }
}
