package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.TbAccountOrgRel;
import cn.ffcs.uoo.core.user.dao.TbAccountOrgRelMapper;
import cn.ffcs.uoo.core.user.service.TbAccountOrgRelService;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.ListAcctOrgVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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

    @Override
    public Long getId(){return baseMapper.getId(); }

    @Override
    public Object saveAcctOrg(List<ListAcctOrgVo> acctOrgVoList, Long acctId){
        List<TbAccountOrgRel> tbAccountOrgRels = new ArrayList<TbAccountOrgRel>();
        TbAccountOrgRel tbAccountOrgRel = null;
        if(acctOrgVoList != null && acctOrgVoList.size() > 0){
            for (ListAcctOrgVo acctOrg : acctOrgVoList){
                tbAccountOrgRel = new TbAccountOrgRel();
                BeanUtils.copyProperties(acctOrg, tbAccountOrgRel);
                tbAccountOrgRel.setAcctHostId(this.getId());
                tbAccountOrgRel.setAcctId(acctId);
                tbAccountOrgRels.add(tbAccountOrgRel);
            }
        }
        if(this.insertBatch(tbAccountOrgRels)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object removeAcctOrg(Long personnelId,Long acctId, Long orgId){
        TbAccountOrgRel tbAccountOrgRel = new TbAccountOrgRel();
        tbAccountOrgRel.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbAccountOrgRel.setStatusDate(new Date());
        EntityWrapper<TbAccountOrgRel> wrapper = new EntityWrapper<TbAccountOrgRel>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TABLE_ACCT_ID, acctId);
        if(!StrUtil.isNullOrEmpty(orgId)){
            wrapper.eq(BaseUnitConstants.TABLE_ORG_ID, orgId);
        }
        if(retBool(baseMapper.update(tbAccountOrgRel, wrapper))){
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
        TbAccountOrgRel accountOrgRel = this.selectOne(new EntityWrapper<TbAccountOrgRel>().allEq(map));
        if(StrUtil.isNullOrEmpty(accountOrgRel)){
            tbAccountOrgRel.setAcctHostId(this.getId());
            if(retBool(baseMapper.insert(tbAccountOrgRel))){
                return ResultUtils.success(null);
            }
        }else{
            return ResultUtils.error(EumUserResponeCode.ACCT_ORG_REL_IS_EXIST);
        }
        return null;
    }
}
