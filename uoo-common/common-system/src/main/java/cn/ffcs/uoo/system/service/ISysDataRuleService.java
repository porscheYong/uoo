package cn.ffcs.uoo.system.service;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.vo.PermDataRule;
import cn.ffcs.uoo.system.vo.SysDataRuleVo;

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

    Page<SysDataRuleVo> getDataRulePage(String search, Integer pageSize, Integer pageNo);
    public List<SysDataRuleVo> listSysDataRuleVoByGroupId(Long groupId);
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param sysDataRule
     */
    public void delete(SysDataRule sysDataRule);

    /**
     * 新增
     */
    public void add(SysDataRule sysDataRule);

    /**
     * 更新
     */
    public void update(SysDataRule sysDataRule);



    public String getDicItem(String itemValue);
    
    public List<PermDataRule> listByPermissionId(Long permId);
}
