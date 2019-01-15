package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.system.consts.BaseUnitConstants;
import cn.ffcs.uoo.system.dao.SysUserMapper;
import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.entity.SysUserDeptRef;
import cn.ffcs.uoo.system.dao.SysUserDeptRefMapper;
import cn.ffcs.uoo.system.service.SysUserDeptRefService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.SysUserDeptVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 对部门可选岗位的限定 服务实现类
 * </p>
 *
 * @author wudj
 * @since 2019-01-05
 */
@Service
public class SysUserDeptRefServiceImpl extends ServiceImpl<SysUserDeptRefMapper, SysUserDeptRef> implements SysUserDeptRefService {

    @Resource
    SysUserDeptRefMapper sysUserDeptRefMapper;

    @Override
    public Long getId() {
        return sysUserDeptRefMapper.getId();
    }

    @Override
    public Object addSysUserDeptRef(SysUserDeptRef sysUserDeptRef) {
        Long id = this.getId();
        sysUserDeptRef.setDeptPositionRefId(id);
        if(retBool(baseMapper.insert(sysUserDeptRef))){
            return true;
        }
        return false;
    }

    @Override
    public Object updateSysUserDeptRef(SysUserDeptRef sysUserDeptRef){
        if(retBool(baseMapper.updateById(sysUserDeptRef))){
            return true;
        }
        return false;
    }

    @Override
    public Object delSysUserDeptRef(SysUserDeptRef sysUserDeptRef){
        sysUserDeptRef.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        sysUserDeptRef.setStatusDate(new Date());
        if(retBool(baseMapper.updateById(sysUserDeptRef))){
            return true;
        }
        return false;
    }

    @Override
    public Page<SysUserDeptRef> getUserDeptRefByUserCode(String userCode, Integer pageNo, Integer pageSize){
        Wrapper wrapper= Condition.create().eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE).eq(BaseUnitConstants.TB_SYS_USER_CODE, userCode);
        Page<SysUserDeptRef> page = this.selectPage(new Page<SysUserDeptRef>(StrUtil.intiPageNo(pageNo), StrUtil.intiPageSize(pageSize)), wrapper);
        return page;
    }

    @Override
    public String checkAllRegister(SysUserDeptRef sysUserDeptRef){
        if(StrUtil.isNullOrEmpty(sysUserDeptRef.getOrgCode())){
            return "请选组织";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TB_SYS_USER_CODE, sysUserDeptRef.getUserCode());
        map.put(BaseUnitConstants.TB_SYS_ORG_CODE, sysUserDeptRef.getOrgCode());
        SysUserDeptRef userDeptRef = this.selectOne(new EntityWrapper<SysUserDeptRef>().allEq(map));
        if(!StrUtil.isNullOrEmpty(userDeptRef)){
            if(!StrUtil.isNullOrEmpty(sysUserDeptRef.getDeptPositionRefId()) && !userDeptRef.getDeptPositionRefId().equals(sysUserDeptRef.getDeptPositionRefId())){
                return "请不要重复选择组织";
            }
        }
        return null;
    }

    @Override
    public void delUserDeptDefByUserCode(String userCode, Long updateUser){
        sysUserDeptRefMapper.delUserDeptDef(userCode, updateUser);
    }

    @Override
    public Page<SysUserDeptVo> getUserDeptByUserCode(String userCode, int pageNo, int pageSize){
        Page<SysUserDeptVo> page = new Page<>(StrUtil.intiPageNo(pageNo), StrUtil.intiPageSize(pageSize));
        page.setRecords(sysUserDeptRefMapper.getUserDeptByUserCode(page, userCode));
        return page;
    }
}
