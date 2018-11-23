package cn.ffcs.uoo.core.resource.service.impl;

import cn.ffcs.uoo.core.resource.entity.TbSystemIndividuationRule;
import cn.ffcs.uoo.core.resource.dao.TbSystemIndividuationRuleMapper;
import cn.ffcs.uoo.core.resource.service.TbSystemIndividuationRuleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-11-16
 */
@Service
public class TbSystemIndividuationRuleServiceImpl extends ServiceImpl<TbSystemIndividuationRuleMapper, TbSystemIndividuationRule> implements TbSystemIndividuationRuleService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }


    @Override
    public void delete(TbSystemIndividuationRule tbSystemIndividuationRule){
        tbSystemIndividuationRule.setStatusCd("1100");
        tbSystemIndividuationRule.setStatusDate(new Date());
        tbSystemIndividuationRule.setUpdateDate(new Date());
        tbSystemIndividuationRule.setUpdateUser(0L);
        updateById(tbSystemIndividuationRule);
    }

    @Override
    public void add(TbSystemIndividuationRule tbSystemIndividuationRule){
        tbSystemIndividuationRule.setCreateDate(new Date());
        tbSystemIndividuationRule.setCreateUser(0L);
        tbSystemIndividuationRule.setStatusCd("1000");
        tbSystemIndividuationRule.setStatusDate(new Date());
        insert(tbSystemIndividuationRule);
    }


    @Override
    public void update(TbSystemIndividuationRule tbSystemIndividuationRule){
        tbSystemIndividuationRule.setUpdateDate(new Date());
        tbSystemIndividuationRule.setUpdateUser(0L);
        tbSystemIndividuationRule.setStatusDate(new Date());
        updateById(tbSystemIndividuationRule);
    }
}
