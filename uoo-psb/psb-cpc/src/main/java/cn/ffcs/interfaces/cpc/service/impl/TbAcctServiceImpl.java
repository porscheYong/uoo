package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.dao.TbAcctMapper;
import cn.ffcs.interfaces.cpc.pojo.TbAcct;
import cn.ffcs.interfaces.cpc.service.TbAcctService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by liuxiaodong on 2019/1/11.
 */
@Service
public class TbAcctServiceImpl extends ServiceImpl<TbAcctMapper, TbAcct> implements TbAcctService {
    @Override
    public TbAcct selectByPersonnelId(Long personnelId) {
        return baseMapper.selectByPersonnelId(personnelId);
    }
}
