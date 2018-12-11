package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.DataPrivRuleMapper;
import cn.ffcs.uoo.core.permission.entity.DataPrivRule;
import cn.ffcs.uoo.core.permission.service.IDataPrivRuleService;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@Service
public class DataPrivRuleServiceImpl extends ServiceImpl<DataPrivRuleMapper, DataPrivRule> implements IDataPrivRuleService {
    @Autowired
    private DataPrivRuleMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }

}
