package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.entity.TbEdu;
import cn.ffcs.uoo.core.personnel.dao.TbEduMapper;
import cn.ffcs.uoo.core.personnel.service.TbEduService;
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
public class TbEduServiceImpl extends ServiceImpl<TbEduMapper, TbEdu> implements TbEduService {

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public void delete(TbEdu tbEdu){
        baseMapper.delete(tbEdu);
    }
}
