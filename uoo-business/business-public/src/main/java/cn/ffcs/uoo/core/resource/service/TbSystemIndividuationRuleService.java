package cn.ffcs.uoo.core.resource.service;

import cn.ffcs.uoo.core.resource.entity.TbSystemIndividuationRule;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-11-16
 */
public interface TbSystemIndividuationRuleService extends IService<TbSystemIndividuationRule> {

    public Long getId();

    public void delete(TbSystemIndividuationRule tbSystemIndividuationRule);

    public void add(TbSystemIndividuationRule tbSystemIndividuationRule);

    public void update(TbSystemIndividuationRule tbSystemIndividuationRule);

}
