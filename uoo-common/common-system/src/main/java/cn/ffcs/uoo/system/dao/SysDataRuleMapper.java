package cn.ffcs.uoo.system.dao;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.system.entity.SysDataRule;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
只有需要权限控制的表才进行登记 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
public interface SysDataRuleMapper extends BaseMapper<SysDataRule> {
    List<SysDataRule> listByAccout(HashMap<String, Object> map);
}
