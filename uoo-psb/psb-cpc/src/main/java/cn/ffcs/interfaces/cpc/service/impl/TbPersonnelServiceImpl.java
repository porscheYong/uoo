package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.dao.TbPersonnelMapper;
import cn.ffcs.interfaces.cpc.pojo.TbPersonnel;
import cn.ffcs.interfaces.cpc.service.TbPersonnelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by liuxiaodong on 2019/1/10.
 */
@Service
public class TbPersonnelServiceImpl extends ServiceImpl<TbPersonnelMapper, TbPersonnel> implements TbPersonnelService {
    @Override
    public void insertValueOfPersonnel(TbPersonnel tbPersonnel) {
        baseMapper.insertValueOfPersonnel(tbPersonnel);
    }
}
