package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.dao.TbContactMapper;
import cn.ffcs.uoo.core.personnel.entity.TbContact;
import cn.ffcs.uoo.core.personnel.service.TbContactService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 联系方式 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-13
 */
@Service
public class TbContactServiceImpl extends ServiceImpl<TbContactMapper, TbContact> implements TbContactService {
    @Autowired
    private TbContactMapper tbContactMapper;

    @Override
    public Long getId() {
        return tbContactMapper.getId();
    }

    @Override
    public void delete(TbContact tbContact) {
        tbContactMapper.delete(tbContact);
    }

    @Override
    public List<TbContact> getTbContactByPsnId(Long personnelId, String contactType){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        params.put(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        params.put(BaseUnitConstants.TBCONTACT_CONTACT_TYPE, contactType);
        return this.selectByMap(params);
    }

    @Override
    public Object delTbContactByPsnId(Long personnelId){
        TbContact tbContact = new TbContact();
        tbContact.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        EntityWrapper<TbContact> wrapper = new EntityWrapper<TbContact>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        if(retBool(baseMapper.update(tbContact, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }
}
