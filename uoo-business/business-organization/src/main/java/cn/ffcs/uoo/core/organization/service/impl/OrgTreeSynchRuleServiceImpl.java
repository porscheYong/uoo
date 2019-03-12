package cn.ffcs.uoo.core.organization.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.organization.dao.OrgOrgtreeRelMapper;
import cn.ffcs.uoo.core.organization.dao.OrgTreeSynchRuleMapper;
import cn.ffcs.uoo.core.organization.entity.OrgOrgtreeRel;
import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.entity.OrgTreeSynchRule;
import cn.ffcs.uoo.core.organization.entity.OrgUpdateCheckResult;
import cn.ffcs.uoo.core.organization.entity.OrgUpdateCheckResult.OrgOperateType;
import cn.ffcs.uoo.core.organization.service.IOrgTreeSynchRuleService;
import cn.ffcs.uoo.core.organization.vo.OrgTreeSynchRuleVO;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-03-04
 */
@Service
public class OrgTreeSynchRuleServiceImpl extends ServiceImpl<OrgTreeSynchRuleMapper, OrgTreeSynchRule> implements IOrgTreeSynchRuleService {
    @Autowired
    private OrgOrgtreeRelMapper orgOrgtreeRelMapper;
    
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public List<OrgTree> getRelTree(Long orgTreeId) {
        return baseMapper.getRelTree(orgTreeId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public OrgUpdateCheckResult check(OrgOperateType orgOperateType, Long orgId,Long orgTreeId) {
        //先查出对应的规则
        //当前组织是不是引用自哪个树的  
        List<OrgOrgtreeRel> rels = orgOrgtreeRelMapper.selectList(Condition.create().eq("ORG_ID", orgId).eq("ORG_TREE_ID", orgTreeId).eq("STATUS_CD", "1000"));
        Set<Long> froms=new HashSet<>();
        if(rels!=null){
            for (OrgOrgtreeRel orgOrgtreeRel : rels) {
                if(orgOrgtreeRel.getFromOrgTreeId()!=null){
                    froms.add(orgOrgtreeRel.getFromOrgTreeId()) ;
                }
            }
        }
        List<OrgTree> beTree = baseMapper.getBeRelTree(orgTreeId);//被哪些树引用了
        OrgUpdateCheckResult res=new OrgUpdateCheckResult();
        switch (orgOperateType) {
        case ADD:
            //当前组织属于哪个树   这个树有没有被其他人引用  , 
            res.setVaild(true);
            if(orgTreeId==null||orgTreeId==0){
                res.setSync(false);
            }else{
                //先查规则 如果新增或者全部情况下要同步 那就同步 否则不用
                if(beTree!=null&&!beTree.isEmpty()){
                    boolean hasRule=false;
                    for (OrgTree orgTree : beTree) {
                        List<OrgTreeSynchRule> list = baseMapper.selectList(Condition.create().eq("STATUS_CD", "1000").eq("TO_ORG_TREE_ID", orgTree.getOrgTreeId()).eq("FROM_ORG_TREE_ID", orgTreeId).in("OPERATE_TYPE", new String[]{"insert","all"}));
                        if(list!=null&&!list.isEmpty()){
                          //同步到哪几个树 也就是现在的树被哪些树引用了
                            res.getSyncOrgTreeIds().add(orgTree.getOrgTreeId());
                            hasRule=true;
                        }
                    }
                    //如果这个组织树被其他人引用了  那么就要去同步到被引用的那里，
                    res.setSync(hasRule);
                    
                    
                    
                }
            }
            break;
        default:
            //如果是来自于别的树 不能修改删除
            res.setVaild(froms.isEmpty());
            if(beTree!=null&&!beTree.isEmpty()){
                boolean hasRule=false;
                for (OrgTree orgTree : beTree) {
                    List<OrgTreeSynchRule> list = baseMapper.selectList(Condition.create().eq("STATUS_CD", "1000").eq("TO_ORG_TREE_ID", orgTree.getOrgTreeId()).eq("FROM_ORG_TREE_ID", orgTreeId).in("OPERATE_TYPE", new String[]{orgOperateType==OrgOperateType.DELETE?"delete":"update","all"}));
                    if(list!=null&&!list.isEmpty()){
                        hasRule=true;
                      //同步到哪几个树 也就是现在的树被哪些树引用了
                        res.getSyncOrgTreeIds().add(orgTree.getOrgTreeId());
                    }
                }
                //如果这个组织树被其他人引用了  那么就要去同步到被引用的那里，
                res.setSync(hasRule);
            }
            break;
        }
        return res;
    }

    @Override
    public List<OrgTreeSynchRuleVO> listByToOrgTreeId(Long orgTreeId) {
        return baseMapper.listByToOrgTreeId(orgTreeId);
    }
}
