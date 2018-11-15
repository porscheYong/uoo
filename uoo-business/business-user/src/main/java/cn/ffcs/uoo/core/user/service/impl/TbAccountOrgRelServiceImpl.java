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

import java.util.ArrayList;
import java.util.List;

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
    public Object saveAcctOrg(List<ListAcctOrgVo> acctOrgVoList){
        List<TbAccountOrgRel> tbAccountOrgRels = new ArrayList<TbAccountOrgRel>();
        TbAccountOrgRel tbAccountOrgRel = null;
        if(acctOrgVoList != null && acctOrgVoList.size() > 0){
            for (ListAcctOrgVo acctOrg : acctOrgVoList){
                tbAccountOrgRel = new TbAccountOrgRel();
                BeanUtils.copyProperties(acctOrg, tbAccountOrgRel);
                tbAccountOrgRels.add(tbAccountOrgRel);
            }
        }
        if(this.insertBatch(tbAccountOrgRels)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object removeAcctOrg(Long acctId, Long orgId){
        TbAccountOrgRel tbAccountOrgRel = new TbAccountOrgRel();
        tbAccountOrgRel.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        EntityWrapper<TbAccountOrgRel> wrapper = new EntityWrapper<TbAccountOrgRel>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TABLE_ACCT_ID, acctId);
        if(!StrUtil.isNullOrEmpty(orgId)){
            wrapper.eq(BaseUnitConstants.TABLE_ORG_ID, orgId);
        }
        if(retBool(baseMapper.update(tbAccountOrgRel, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }
}
