package cn.ffcs.uoo.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.system.entity.SysDataRuleGroup;

/**
 * <p>
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-28
 */
public interface SysDataRuleGroupMapper extends BaseMapper<SysDataRuleGroup> {
    Long getId();
    List<SysDataRuleGroup> listByPermCode(String permCode);
    List<SysDataRuleGroup> listByAccout(@Param("accout")String accout,@Param("treeId")Long treeId,@Param("tableNames")List<String> tableNames);
}
