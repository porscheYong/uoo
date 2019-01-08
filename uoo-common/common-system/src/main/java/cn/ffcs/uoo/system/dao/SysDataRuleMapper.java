package cn.ffcs.uoo.system.dao;

import java.util.HashMap;
import java.util.List;

import cn.ffcs.uoo.system.vo.SysDataRuleVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.system.entity.SysDataRule;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

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

    public Long getId();

    List<SysDataRule> listByAccout(HashMap<String, Object> map);

    List<SysDataRuleVo> getDataRulePage(Pagination page, @Param("search")String search);
}
