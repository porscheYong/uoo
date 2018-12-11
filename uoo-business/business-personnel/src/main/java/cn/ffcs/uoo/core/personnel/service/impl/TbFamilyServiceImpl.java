package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.entity.TbFamily;
import cn.ffcs.uoo.core.personnel.dao.TbFamilyMapper;
import cn.ffcs.uoo.core.personnel.service.TbFamilyService;
import cn.ffcs.uoo.core.personnel.vo.TbFamilyVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@Service
public class TbFamilyServiceImpl extends ServiceImpl<TbFamilyMapper, TbFamily> implements TbFamilyService {

    @Override
    public void save(TbFamily tbFamily){
        baseMapper.save(tbFamily);
    }

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public void delete(TbFamily tbFamily){
        baseMapper.delete(tbFamily);
    }
}
