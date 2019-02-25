package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.dao.TbAcctExtMapper;
import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import cn.ffcs.uoo.core.user.service.TbAcctExtService;
import cn.ffcs.uoo.core.user.util.IdCardVerification;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 主账号扩展 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service
public class TbAcctExtServiceImpl extends ServiceImpl<TbAcctExtMapper, TbAcctExt> implements TbAcctExtService {

    @Resource
    private TbAcctExtMapper tbAcctExtMapper;

    @Override
    public void removeAcctExt(TbAcctExt tbAcctExt) {
        tbAcctExtMapper.delete(tbAcctExt);
    }

    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public TbAcctExt saveTbAcctExt(TbAcctExt tbAcctExt){
        if(StrUtil.isNullOrEmpty(tbAcctExt.getAcctExtId())){
            tbAcctExt.setAcctExtId(this.getId());
            if(this.insert(tbAcctExt)){
                return tbAcctExt;
            }
        }else{
            if(this.updateById(tbAcctExt)){
                return tbAcctExt;
            }
        }
        return null;
    }

    @Override
    public Object delTbAcctExt(Long slaveAcctId, Long userId){
        TbAcctExt tbAcctExt = new TbAcctExt();
        tbAcctExt.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbAcctExt.setStatusDate(new Date());
        tbAcctExt.setUpdateUser(userId);
        EntityWrapper<TbAcctExt> wrapper = new EntityWrapper<TbAcctExt>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TABLE_SLAVE_ACCT_ID, slaveAcctId);
        if(retBool(baseMapper.update(tbAcctExt, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object checkAcctExt(TbAcctExt tbAcctExt){
        if(!StrUtil.isNullOrEmpty(tbAcctExt)){
            if(!StrUtil.isNullOrEmpty(tbAcctExt.getWorkEmail()) && !StrUtil.checkEmail(tbAcctExt.getWorkEmail())){
                return ResultUtils.error(EumUserResponeCode.EMAIL_ERROR);
            }
            if(!StrUtil.isNullOrEmpty(tbAcctExt.getContactWay()) && !StrUtil.checkTelephoneNumber(tbAcctExt.getContactWay())) {
                return ResultUtils.error(EumUserResponeCode.MOBILE_ERROR);
            }
            if(!StrUtil.isNullOrEmpty(tbAcctExt.getCertNo()) && "1".equals(tbAcctExt.getCertType()) && !IdCardVerification.idCardValidate(tbAcctExt.getCertNo())) {
                return ResultUtils.error(EumUserResponeCode.CERT_NO_ERROR);
            }
            if(!StrUtil.isNullOrEmpty(tbAcctExt.getOpenid()) && StrUtil.isContainChinese(tbAcctExt.getOpenid())){
                return ResultUtils.error(EumUserResponeCode.OPENID_ERROR);
            }
        }
        return null;
    }
}
