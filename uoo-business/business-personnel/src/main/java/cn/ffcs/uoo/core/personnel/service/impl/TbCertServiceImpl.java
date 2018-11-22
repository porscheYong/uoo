package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.dao.TbCertMapper;
import cn.ffcs.uoo.core.personnel.entity.TbCert;
import cn.ffcs.uoo.core.personnel.service.TbCertService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zipkin2.Call;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 证件 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service
public class TbCertServiceImpl extends ServiceImpl<TbCertMapper, TbCert> implements TbCertService {


    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public void delete(TbCert tbCert) {
        baseMapper.delete(tbCert);
    }

    @Override
    public TbCert getTbCertByPersonnelId(Long personnelId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        params.put(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        return this.selectOne(new EntityWrapper<TbCert>().allEq(params));
    }

    @Override
    public Object delTbCertByPsnId(Long personnelId){
        TbCert tbCert = new TbCert();
        tbCert.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        EntityWrapper<TbCert> wrapper = new EntityWrapper<TbCert>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        if(retBool(baseMapper.update(tbCert, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }

    @Override
    public Object getCertInfo(String keyWord, Integer pageNo, Integer pageSize){
        pageNo = pageNo == 0 ? 1 : pageNo;
        pageSize = pageSize == 0 ? 5 : pageSize;

        Page<TbCert> page =  this.selectPage(new Page<TbCert>(pageNo, pageSize), new EntityWrapper<TbCert>().like(BaseUnitConstants.TBCERT_CERT_NO, keyWord).or().like(BaseUnitConstants.TBCERT_CERT_NAME, keyWord));
        return ResultUtils.success(page);
    }
}
