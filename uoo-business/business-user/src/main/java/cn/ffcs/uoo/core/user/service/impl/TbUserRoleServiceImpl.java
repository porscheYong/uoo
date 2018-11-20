package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbUserRole;
import cn.ffcs.uoo.core.user.dao.TbUserRoleMapper;
import cn.ffcs.uoo.core.user.service.TbUserRoleService;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wudj
 * @since 2018-11-13
 */
@Service
public class TbUserRoleServiceImpl<main> extends ServiceImpl<TbUserRoleMapper, TbUserRole> implements TbUserRoleService {

    @Override
    public Long getId(){ return baseMapper.getId(); }

    @Override
    public Object saveUserRole(List<TbRoles> tbRolesList, Long acctId, Long acctType){
        List<TbUserRole> tbUserRoles = new ArrayList<TbUserRole>();
        TbUserRole tbUserRole = null;
        if(tbRolesList != null && tbRolesList.size() > 0 ){
            for(TbRoles tbRoles : tbRolesList){
                tbUserRole = new TbUserRole();
                BeanUtils.copyProperties(tbRoles, tbUserRole);
                tbUserRole.setAcctId(acctId);
                tbUserRole.setAcctType(acctType);
                tbUserRoles.add(tbUserRole);
            }
        }
        if(this.insertBatch(tbUserRoles)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object removeUserRole(Long acctId, Long acctType){
        TbUserRole tbUserRole = new TbUserRole();
        tbUserRole.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        EntityWrapper<TbUserRole> wrapper = new EntityWrapper<TbUserRole>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TABLE_ACCT_ID, acctId);
        wrapper.eq(BaseUnitConstants.TBALE_ACCT_TYPE, acctType);

        if(retBool(baseMapper.update(tbUserRole, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);

    }

    @Override
    public Object updateUserRole(List<TbRoles> tbRolesList, List<TbRoles> oldTbRolesList, Long acctId, Long acctType){
        List<Long> newRoleList = getRoleIdList(tbRolesList);
        List<Long> newRoleListBak =  newRoleList;
        List<Long> oldRoleList = getRoleIdList(oldTbRolesList);
        List<Long> oldRoleListtBak =  oldRoleList;

       //删除 新选角色中没有 的角色
        oldRoleList.removeAll(newRoleList);
        this.updateTbRolesList(oldRoleList, "del", acctId, acctType);
        //获取 相同角色
        oldRoleListtBak.retainAll(newRoleList);
        //新建 新选的角色
        newRoleListBak.removeAll(oldRoleListtBak);
        this.updateTbRolesList(newRoleListBak, "insert", acctId, acctType);
        return null;
    }

    public void updateTbRolesList(List<Long> tbRolesList, String type ,Long acctId, Long acctType){
        if(tbRolesList != null && tbRolesList.size() > 0){
            for(Long roleId : tbRolesList){
                if("del".equals(type)){
                    TbUserRole tbUserRole = new TbUserRole();
                    tbUserRole.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
                    map.put(BaseUnitConstants.TBALE_ROLE_ID, roleId);
                    map.put(BaseUnitConstants.TABLE_ACCT_ID, acctId);
                    map.put(BaseUnitConstants.TBALE_ACCT_TYPE, acctType);
                    this.update(tbUserRole, new EntityWrapper<TbUserRole>().allEq(map));
                }
                if("insert".equals(type)){
                    TbUserRole tbUserRole = new TbUserRole();
                    tbUserRole.setUserRoleId(this.getId());
                    tbUserRole.setRoleId(roleId);
                    tbUserRole.setAcctId(acctId);
                    tbUserRole.setAcctType(acctType);
                    this.insert(tbUserRole);
                }
            }
        }
    }

    public List<Long> getRoleIdList(List<TbRoles> tbRolesList){
        List<Long> list = new ArrayList<Long>();
        if(tbRolesList != null && tbRolesList.size() > 0){
            for(TbRoles tbRoles : tbRolesList){
                list.add(tbRoles.getRoleId());
            }
        }
        return list;
    }


}
