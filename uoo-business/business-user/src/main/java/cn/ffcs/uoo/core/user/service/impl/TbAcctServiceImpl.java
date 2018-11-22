package cn.ffcs.uoo.core.user.service.impl;


import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.dao.TbAcctMapper;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.service.TbAcctService;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主账号 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service("tbAcctService")
public class TbAcctServiceImpl extends ServiceImpl<TbAcctMapper, TbAcct> implements TbAcctService {

    @Resource
    private TbAcctMapper tbAcctMapper;

    @Override
    public Long getId(){
        return tbAcctMapper.getId();
    }
    /**
     * 保存主账号
     * @param tbAcct
     */
    @Override
    public long saveAcct(TbAcct tbAcct) {

        //return baseMapper.save(tbAcct);
        return 1L;
    }

    @Override
    public void removeAcct(TbAcct tbAcct) {
        baseMapper.delete(tbAcct);
    }

    @Override
    public List<TbAcct> selectAcctList(TbAcct tbAcct) {
        return baseMapper.selectAcctList(tbAcct);
    }

    public TbAcct selectOneAcct(TbAcct tbAcct) {
        List<TbAcct> acctList = selectAcctList(tbAcct);
        return acctList.get(0);
    }

    @Override
    public List<TbRoles> getTbRoles(Long acctType, Long acctId){
        return baseMapper.getTbRoles(acctType, acctId);
    }

    @Override
    public Object removeTbAcct(Long acctId){
        TbAcct tbAcct = new TbAcct();
        tbAcct.setAcctId(acctId);
        tbAcct.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);

        if(this.updateById(tbAcct)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object getTbAcctByPsnId(Long personelId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_PERSONNEL_ID, personelId);
        return this.selectOne(new EntityWrapper<TbAcct>().allEq(map));
    }

}
