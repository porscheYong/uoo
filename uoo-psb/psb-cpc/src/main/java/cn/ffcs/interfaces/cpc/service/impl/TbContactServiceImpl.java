package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.dao.TbContactMapper;
import cn.ffcs.interfaces.cpc.pojo.TbContact;
import cn.ffcs.interfaces.cpc.service.TbContactService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by liuxiaodong on 2019/1/11.
 */
@Service
public class TbContactServiceImpl extends ServiceImpl<TbContactMapper, TbContact> implements TbContactService {
    @Override
    public boolean deleteByPersonnelId(Long personnelId) {
        baseMapper.deleteByPersonnelId(personnelId);
        return true;
    }
}
