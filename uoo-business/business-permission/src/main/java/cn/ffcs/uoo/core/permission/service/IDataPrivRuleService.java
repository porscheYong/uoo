package cn.ffcs.uoo.core.permission.service;

import cn.ffcs.uoo.core.permission.entity.DataPrivRule;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
public interface IDataPrivRuleService extends IService<DataPrivRule> {
    Long getId();
}
