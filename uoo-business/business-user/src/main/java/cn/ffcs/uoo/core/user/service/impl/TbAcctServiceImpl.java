package cn.ffcs.uoo.core.user.service.impl;


import cn.ffcs.uoo.core.user.dao.TbAcctMapper;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.service.TbAcctService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public Long getId(){
        return tbAcctMapper.getId();
    }
    /**
     * 保存主账号
     * @param tbAcct
     */
    @Override
    public long saveAcct(TbAcct tbAcct) {

        //return baseMapper.save(tbAcct);
        return 1L;
    }

    @Override
    public void removeAcct(TbAcct tbAcct) {
        baseMapper.delete(tbAcct);
    }

    @Override
    public List<TbAcct> selectAcctList(TbAcct tbAcct) {
        return baseMapper.selectAcctList(tbAcct);
    }

    public TbAcct selectOneAcct(TbAcct tbAcct) {
        List<TbAcct> acctList = selectAcctList(tbAcct);
        return acctList.get(0);
    }


}
