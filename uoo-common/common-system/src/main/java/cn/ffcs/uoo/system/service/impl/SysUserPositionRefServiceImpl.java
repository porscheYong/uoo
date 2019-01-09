package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.consts.BaseUnitConstants;
import cn.ffcs.uoo.system.entity.SysUserPositionRef;
import cn.ffcs.uoo.system.dao.SysUserPositionRefMapper;
import cn.ffcs.uoo.system.service.SysUserPositionRefService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 平台用户职位关系 服务实现类
 * </p>
 *
 * @author wudj
 * @since 2019-01-05
 */
@Service
public class SysUserPositionRefServiceImpl extends ServiceImpl<SysUserPositionRefMapper, SysUserPositionRef> implements SysUserPositionRefService {

    @Resource
    SysUserPositionRefMapper sysUserPositionRefMapper;

    @Override
    public Long getId() {
        return sysUserPositionRefMapper.getId();
    }

    @Override
    public Object addSysUserPositionRef(SysUserPositionRef sysUserPositionRef) {
        Long id = this.getId();
        sysUserPositionRef.setUserPositionRefId(id);
        if(retBool(baseMapper.insert(sysUserPositionRef))){
            return true;
        }
        return false;
    }

    @Override
    public Object updateSysUserPositionRef(List<SysUserPositionRef> sysUserPositionRef, String userCode, String orgCode, Long updateUser){
        List<SysUserPositionRef> userPositionRefList = this.getUserPositionRef(userCode, orgCode);
        List<String> newList = getReList(sysUserPositionRef);
        List<String> newListBak = getReList(sysUserPositionRef);
        List<String> oldList = getReList(userPositionRefList);
        List<String> oldListBak = getReList(userPositionRefList);

        //删除 新选角色中没有 的角色
        oldList.removeAll(newList);
        if(oldList != null && oldList.size() > 0){
            List<SysUserPositionRef> delUserPosition = new ArrayList<>();
            for(SysUserPositionRef userPositionRef :userPositionRefList){
                for(String code : oldList){
                    if(code.equals(userPositionRef.getPositionCode())){
                        userPositionRef.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
                        userPositionRef.setStatusDate(new Date());
                        userPositionRef.setUpdateUser(updateUser);
                        delUserPosition.add(userPositionRef);
                    }
                }
            }
            this.updateBatchById(delUserPosition);
        }

        //获取 相同角色
        oldListBak.retainAll(newList);
        newListBak.removeAll(oldListBak);
        if(newListBak != null && newListBak.size() > 0){
            List<SysUserPositionRef> refs = new ArrayList<>();
            for(String code : newListBak){
                SysUserPositionRef positionRef = new SysUserPositionRef();
                positionRef.setUserCode(userCode);
                positionRef.setPositionCode(code);
                positionRef.setUserPositionRefId(this.getId());
                positionRef.setCreateUser(updateUser);
                positionRef.setUpdateUser(updateUser);
                refs.add(positionRef);
            }
            this.insertBatch(refs);
        }

        return null;
    }

    @Override
    public Object delSysUserPositionRef(String userCode, String orgCode, Long updateuser){
        List<SysUserPositionRef> userPositionRefList = this.getUserPositionRef(userCode, orgCode);
        if(userPositionRefList != null && userPositionRefList.size() > 0){
            for(SysUserPositionRef userPositionRef : userPositionRefList){
                userPositionRef.setUpdateUser(updateuser);
                userPositionRef.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
                userPositionRef.setStatusDate(new Date());
                this.updateById(userPositionRef);
            }
        }
        return null;
    }

    @Override
    public List<SysUserPositionRef> getUserPositionRef(String userCode, String orgCode){
        return sysUserPositionRefMapper.getUserPositionRef(userCode, orgCode);
    }

    @Override
    public void delUserPositionDefByUserCode(String userCode, Long updateUser){
        sysUserPositionRefMapper.delUserPositionDef(userCode, updateUser);
    }

    public List<String> getReList(List<SysUserPositionRef> userPositionRefList){
        List<String> list = new ArrayList<String>();
        if(userPositionRefList != null && userPositionRefList.size() > 0){
            for(SysUserPositionRef ref : userPositionRefList){
                list.add(ref.getPositionCode());
            }
            HashSet h = new HashSet(list);
            list.clear();
            list.addAll(h);
        }
        return list;
    }
}
