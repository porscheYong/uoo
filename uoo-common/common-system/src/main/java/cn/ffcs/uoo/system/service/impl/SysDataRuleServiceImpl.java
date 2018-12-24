package cn.ffcs.uoo.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.dao.SysDataRuleMapper;
import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.service.ISysDataRuleService;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
只有需要权限控制的表才进行登记 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
@Service
public class SysDataRuleServiceImpl extends ServiceImpl<SysDataRuleMapper, SysDataRule> implements ISysDataRuleService {

}
