package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.dao.TbSlaveAcctMapper;
import cn.ffcs.uoo.core.user.entity.ListUser;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import cn.ffcs.uoo.core.user.service.TbSlaveAcctService;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctOrgVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 从账号 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service
public class TbSlaveAcctServiceImpl extends ServiceImpl<TbSlaveAcctMapper, TbSlaveAcct> implements TbSlaveAcctService {

    @Override
    public Long getId(){
       return baseMapper.getId();
    }

    @Override
    public List<ListUser> getUserList(Long slaveAcctId){
        return baseMapper.getUserList(slaveAcctId);
    }

    @Override
    public List<ListUser> getApplyUserList(Long slaveAcctId){ return  baseMapper.getApplyUserList(slaveAcctId); }

    @Override
    public List<TbAcct> getAcct(Long slaveAcctId){ return  baseMapper.getAcct(slaveAcctId); }

    @Override
    public Page<ListSlaveAcctOrgVo> getSlaveAcctOrg(ListSlaveAcctOrgVo slaveAcctOrgVo){

        int pageSize = slaveAcctOrgVo.getPageSize() == 0 ? 5 : slaveAcctOrgVo.getPageSize();
        Page<ListSlaveAcctOrgVo> page = new Page<ListSlaveAcctOrgVo>(slaveAcctOrgVo.getPageNo(),pageSize);
        List<ListSlaveAcctOrgVo> list = baseMapper.getSlaveAcctOrg(page, slaveAcctOrgVo);
        page.setRecords(list);
        return page;
    }
    @Override
    public Object addTbSlaveAcct(TbSlaveAcct tbSlaveAcct){
        if(this.insert(tbSlaveAcct)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public boolean checkSlaveAcct(String slaveAcct, Long acctHostId , Long resourceObjId, Long slaveAcctId, Long acctId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        if(!StrUtil.isNullOrEmpty(slaveAcct)){
            map.put(BaseUnitConstants.TABLE_SLAVE_ACCT, slaveAcct);
        }
        if(!StrUtil.isNullOrEmpty(acctId)){
            map.put(BaseUnitConstants.TABLE_ACCT_ID, acctId);
        }
        map.put(BaseUnitConstants.TB_ACCT_HOST_ID, acctHostId);
        map.put(BaseUnitConstants.TB_RESOURCE_OBJ_ID, resourceObjId);
        TbSlaveAcct tbSlaveAcct = this.selectOne(new EntityWrapper<TbSlaveAcct>().allEq(map));
        if(StrUtil.isNullOrEmpty(tbSlaveAcct)){
            return false;
        }
        if(!StrUtil.isNullOrEmpty(slaveAcctId)){
            if(slaveAcctId.equals(tbSlaveAcct.getSlaveAcctId())){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object delTbSlaveAcct(Long slaveAcctId){
        TbSlaveAcct tbSlaveAcct = new TbSlaveAcct();
        tbSlaveAcct.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbSlaveAcct.setStatusDate(new Date());
        EntityWrapper<TbSlaveAcct> wrapper = new EntityWrapper<TbSlaveAcct>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TABLE_SLAVE_ACCT_ID, slaveAcctId);
        if(retBool(baseMapper.update(tbSlaveAcct, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object updateTbSlaveAcct(TbSlaveAcct tbSlaveAcct){
        if(retBool(baseMapper.updateById(tbSlaveAcct))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

}
