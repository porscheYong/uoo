package cn.ffcs.uoo.core.permission.dao;

import cn.ffcs.uoo.core.permission.entity.DataPrivRule;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
public interface DataPrivRuleMapper extends BaseMapper<DataPrivRule> {
    Long getId();
}
