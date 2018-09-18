package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.dao.TbAcctMapper;
import cn.ffcs.uoo.core.personnel.entity.TbAcct;
import cn.ffcs.uoo.core.personnel.service.TbAcctService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 主账号 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service("tbAcctService")
public class TbAcctServiceImpl extends ServiceImpl<TbAcctMapper, TbAcct> implements TbAcctService {

    @Resource
    private TbAcctMapper tbAcctMapper;

    /**
     * 保存主账号
     * @param tbAcct
     */
    @Override
    public long saveAcct(TbAcct tbAcct) {
        return tbAcctMapper.save(tbAcct);
    }

    @Override
    public void removeAcct(TbAcct tbAcct) {
        tbAcctMapper.delete(tbAcct);
    }


}
