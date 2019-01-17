package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.TbAccountOrgRel;
import cn.ffcs.uoo.core.user.dao.TbAccountOrgRelMapper;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import cn.ffcs.uoo.core.user.service.RabbitMqService;
import cn.ffcs.uoo.core.user.service.TbAccountOrgRelService;
import cn.ffcs.uoo.core.user.service.TbAcctService;
import cn.ffcs.uoo.core.user.service.TbSlaveAcctService;
import cn.ffcs.uoo.core.user.util.CopyUtils;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.AccountOrgRelVo;
import cn.ffcs.uoo.core.user.vo.ListAcctOrgVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wudj
 * @since 2018-11-08
 */
@Service
public class TbAccountOrgRelServiceImpl extends ServiceImpl<TbAccountOrgRelMapper, TbAccountOrgRel> implements TbAccountOrgRelService {

    @Autowired
    private TbSlaveAcctService tbSlaveAcctService;

    @Autowired
    private TbAcctService tbAcctService;

    @Autowired
    private RabbitMqService rabbitMqService;

    @Override
    public Long getId(){return baseMapper.getId(); }

    @Override
    public Object saveAcctOrg(List<ListAcctOrgVo> acctOrgVoList, Long acctId, Long userId){
        List<TbAccountOrgRel> tbAccountOrgRels = new ArrayList<TbAccountOrgRel>();
        TbAccountOrgRel tbAccountOrgRel = null;
        if(acctOrgVoList != null && acctOrgVoList.size() > 0){
            for (ListAcctOrgVo acctOrg : acctOrgVoList){
                tbAccountOrgRel = new TbAccountOrgRel();
                BeanUtils.copyProperties(acctOrg, tbAccountOrgRel);
                tbAccountOrgRel.setAcctOrgRelId(this.getId());
                tbAccountOrgRel.setAcctId(acctId);
                tbAccountOrgRel.setOrgTreeId(acctOrg.getOrgTreeId());
                tbAccountOrgRel.setCreateUser(userId);
                tbAccountOrgRel.setUpdateUser(userId);
                tbAccountOrgRels.add(tbAccountOrgRel);
            }
        }
        if(this.insertBatch(tbAccountOrgRels)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object removeAcctOrg(Long personnelId, Long acctId, Long orgId, Long orgTreeId, Long userId){
        List<TbSlaveAcct> tbSlaveAcctList = baseMapper.findSlaveAcct(orgId, acctId, orgTreeId);
        if(tbSlaveAcctList != null && tbSlaveAcctList.size() > 0 ){
            for(TbSlaveAcct tbSlaveAcct : tbSlaveAcctList){
                tbSlaveAcctService.delAllTbSlaveAcct(tbSlaveAcct.getSlaveAcctId(), userId);
            }
        }
        TbAccountOrgRel tbAccountOrgRel = new TbAccountOrgRel();
        tbAccountOrgRel.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbAccountOrgRel.setStatusDate(new Date());
        tbAccountOrgRel.setUpdateUser(userId);
        EntityWrapper<TbAccountOrgRel> wrapper = new EntityWrapper<TbAccountOrgRel>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TABLE_ACCT_ID, acctId);
        if(!StrUtil.isNullOrEmpty(orgId)){
            wrapper.eq(BaseUnitConstants.TABLE_ORG_ID, orgId);
        }
        if(!StrUtil.isNullOrEmpty(orgTreeId)){
            wrapper.eq(BaseUnitConstants.TB_ORG_TREE_ID, orgTreeId);
        }
        if(retBool(baseMapper.update(tbAccountOrgRel, wrapper))){
            if(!StrUtil.isNullOrEmpty(personnelId)){
                rabbitMqService.sendMqMsg("person", "update", "personnelId", personnelId);
            }
            return ResultUtils.success(personnelId);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object addAcctOrg(TbAccountOrgRel tbAccountOrgRel){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_ORG_ID, tbAccountOrgRel.getOrgId());
        map.put(BaseUnitConstants.TABLE_ACCT_ID, tbAccountOrgRel.getAcctId());
        map.put(BaseUnitConstants.TB_ORG_TREE_ID, tbAccountOrgRel.getOrgTreeId());
        TbAccountOrgRel accountOrgRel = this.selectOne(new EntityWrapper<TbAccountOrgRel>().allEq(map));
        if(StrUtil.isNullOrEmpty(accountOrgRel)){
            tbAccountOrgRel.setAcctOrgRelId(this.getId());
            if(retBool(baseMapper.insert(tbAccountOrgRel))){
                TbAcct tbAcct = tbAcctService.getTbAcctById(tbAccountOrgRel.getAcctId());
                rabbitMqService.sendMqMsg("person", "update", "personnelId", tbAcct.getPersonnelId());
                return ResultUtils.success(null);
            }
        }else{
            return ResultUtils.error(EumUserResponeCode.ACCT_ORG_REL_IS_EXIST);
        }
        return null;
    }

    @Override
    public Object updateAcctOrg(AccountOrgRelVo tbAccountOrgRel){
        //从账号 组织变更
        if(!StrUtil.isNullOrEmpty(tbAccountOrgRel.getSlaveAcctId())){
            TbAccountOrgRel acctOrg = this.addOrUpdateAcctOrg(tbAccountOrgRel);

            TbSlaveAcct tbSlaveAcct = new TbSlaveAcct();
            tbSlaveAcct.setSlaveAcctId(tbAccountOrgRel.getSlaveAcctId());
            tbSlaveAcct.setUpdateUser(tbAccountOrgRel.getUserId());
            tbSlaveAcct.setAcctOrgRelId(acctOrg.getAcctOrgRelId());
            tbSlaveAcctService.updateTbSlaveAcct(tbSlaveAcct);
        }else{
            //主账号 组织变更
            TbAccountOrgRel accountOrgRel = this.getTbAcctOrgRel(tbAccountOrgRel);
            if(!StrUtil.isNullOrEmpty(accountOrgRel)){
                if(!accountOrgRel.getAcctOrgRelId().equals(tbAccountOrgRel.getAcctOrgRelId())){
                    return ResultUtils.error(EumUserResponeCode.ACCT_ORG_REL_IS_EXIST);
                }
            }
            TbAccountOrgRel orgRel = new TbAccountOrgRel();
            BeanUtils.copyProperties(tbAccountOrgRel, orgRel);
            baseMapper.updateById(orgRel);
        }
        TbAcct tbAcct = tbAcctService.getTbAcctById(tbAccountOrgRel.getAcctId());
        if(!StrUtil.isNullOrEmpty(tbAcct)){
            rabbitMqService.sendMqMsg("person", "update", "personnelId", tbAcct.getPersonnelId());
        }
        return ResultUtils.success(null);
    }

    @Override
    public TbAccountOrgRel addOrUpdateAcctOrg(AccountOrgRelVo tbAccountOrgRel){
        TbAccountOrgRel accountOrgRel = this.getTbAcctOrgRel(tbAccountOrgRel);
        TbAccountOrgRel acctOrgRel = new TbAccountOrgRel();

        if(StrUtil.isNullOrEmpty(accountOrgRel)){
            Long acctOrgRelId = this.getId();
            CopyUtils.copyProperties(tbAccountOrgRel, acctOrgRel);
            acctOrgRel.setCreateUser(tbAccountOrgRel.getUserId());
            acctOrgRel.setUpdateUser(tbAccountOrgRel.getUserId());
            acctOrgRel.setAcctOrgRelId(acctOrgRelId);
            baseMapper.insert(acctOrgRel);
            return acctOrgRel;
        }
        CopyUtils.copyProperties(tbAccountOrgRel, accountOrgRel);
        baseMapper.updateById(accountOrgRel);
        return accountOrgRel;
    }

    public TbAccountOrgRel getTbAcctOrgRel(AccountOrgRelVo tbAccountOrgRel){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_ORG_ID, tbAccountOrgRel.getOrgId());
        map.put(BaseUnitConstants.TABLE_ACCT_ID, tbAccountOrgRel.getAcctId());
        map.put(BaseUnitConstants.TB_ORG_TREE_ID, tbAccountOrgRel.getOrgTreeId());
        TbAccountOrgRel accountOrgRel = this.selectOne(new EntityWrapper<TbAccountOrgRel>().allEq(map));
        if(!StrUtil.isNullOrEmpty(accountOrgRel)){
            return accountOrgRel;
        }
        return null;
    }
}
