package cn.ffcs.uoo.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.dao.SysDataRuleMapper;
import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.service.ISysDataRuleService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.PermDataRule;
import cn.ffcs.uoo.system.vo.SysDataRuleVo;

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

    @Override
    public List<SysDataRule> listByAccout(HashMap<String, Object> map) {
        return baseMapper.listByAccout(map);
    }
    @Override
    public Page<SysDataRuleVo> getDataRulePage(String search, Integer pageSize, Integer pageNo){
        Page<SysDataRuleVo> page = new Page<SysDataRuleVo>(StrUtil.isNullOrEmpty(pageNo)?1:pageNo,
                StrUtil.isNullOrEmpty(pageSize)?10:pageSize);
        List<SysDataRuleVo> list = new ArrayList<>();
        list = baseMapper.getDataRulePage(page,search);
        page.setRecords(list);
        return page;
    }


    /**
     * 获取seq
     * @return
     */
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    /**
     * 失效状态
     * @param sysDataRule
     */
    @Override
    public void delete(SysDataRule sysDataRule){
        sysDataRule.setStatusCd("1100");
        sysDataRule.setStatusDate(new Date());
        sysDataRule.setUpdateDate(new Date());
        sysDataRule.setUpdateUser(StrUtil.isNullOrEmpty(sysDataRule.getUpdateUser())?0L:sysDataRule.getUpdateUser());
        updateById(sysDataRule);
    }



    /**
     * 新增
     */
    @Override
    public void add(SysDataRule sysDataRule){
        sysDataRule.setCreateDate(new Date());
        sysDataRule.setCreateUser(StrUtil.isNullOrEmpty(sysDataRule.getCreateUser())?0L:sysDataRule.getCreateUser());
        sysDataRule.setStatusCd("1000");
        sysDataRule.setStatusDate(new Date());
        insert(sysDataRule);
    }

    /**
     * 更新
     */
    @Override
    public void update(SysDataRule sysDataRule){
        sysDataRule.setUpdateDate(new Date());
        sysDataRule.setUpdateUser(StrUtil.isNullOrEmpty(sysDataRule.getUpdateUser())?0L:sysDataRule.getUpdateUser());
        sysDataRule.setStatusDate(new Date());
        updateById(sysDataRule);
    }
    @Override
    public String getDicItem(String itemValue){
        return baseMapper.getDicItem(itemValue);
    }
    @Override
    public List<PermDataRule> listByPermissionId(Long permId) {
        return baseMapper.listByPermissionId(permId);
    }
    @Override
    public List<SysDataRuleVo> listSysDataRuleVoByGroupId(Long groupId) {
        return baseMapper.listSysDataRuleVoByGroupId(groupId);
    }
}
