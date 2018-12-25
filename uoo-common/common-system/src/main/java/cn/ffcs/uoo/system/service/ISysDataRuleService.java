package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysDataRule;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
只有需要权限控制的表才进行登记 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
public interface ISysDataRuleService extends IService<SysDataRule> {
    List<SysDataRule> listByAccout(HashMap<String, Object> map);
}
