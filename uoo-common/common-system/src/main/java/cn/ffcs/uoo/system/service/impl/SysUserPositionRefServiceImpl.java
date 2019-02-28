package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.consts.BaseUnitConstants;
import cn.ffcs.uoo.system.entity.SysUserPositionRef;
import cn.ffcs.uoo.system.dao.SysUserPositionRefMapper;
import cn.ffcs.uoo.system.service.SysUserPositionRefService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.SysUserPositionRefVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.USER_CODE, sysUserPositionRef.getUserCode());
        map.put(BaseUnitConstants.POSITION_CODE, sysUserPositionRef.getPositionCode());
        SysUserPositionRef userPositionRef = this.selectOne(new EntityWrapper<SysUserPositionRef>().allEq(map));
        if(!StrUtil.isNullOrEmpty(userPositionRef)){
            return true;
        }
        Long id = this.getId();
        sysUserPositionRef.setUserPositionRefId(id);
        if(retBool(baseMapper.insert(sysUserPositionRef))){
            return true;
        }
        return false;
    }

    @Override
    public Object updateSysUserPositionRef(List<SysUserPositionRefVo> sysUserPositionRef, String userCode, String orgCode, Long updateUser){
        List<SysUserPositionRefVo> userPositionRefList = this.getUserPositionRef(userCode, orgCode);
        List<String> newList = getReList(sysUserPositionRef);
        List<String> newListBak = getReList(sysUserPositionRef);
        List<String> oldList = getReList(userPositionRefList);
        List<String> oldListBak = getReList(userPositionRefList);

        //删除 新选角色中没有 的角色
        oldList.removeAll(newList);
        if(oldList != null && oldList.size() > 0){
            List<SysUserPositionRef> delUserPosition = new ArrayList<>();
            for(SysUserPositionRefVo userPositionRefVo :userPositionRefList){
                for(String code : oldList){
                    if(code.equals(userPositionRefVo.getPositionCode())){
                        SysUserPositionRef userPositionRef = new SysUserPositionRef();
                        BeanUtils.copyProperties(userPositionRefVo, userPositionRef);
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
                Map<String, Object> map = new HashMap<String, Object>(16);
                map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
                map.put(BaseUnitConstants.USER_CODE, userCode);
                map.put(BaseUnitConstants.POSITION_CODE, code);
                SysUserPositionRef userPositionRef = this.selectOne(new EntityWrapper<SysUserPositionRef>().allEq(map));
                if(StrUtil.isNullOrEmpty(userPositionRef)){
                    SysUserPositionRef positionRef = new SysUserPositionRef();
                    positionRef.setUserCode(userCode);
                    positionRef.setPositionCode(code);
                    positionRef.setUserPositionRefId(this.getId());
                    positionRef.setCreateUser(updateUser);
                    positionRef.setUpdateUser(updateUser);
                    refs.add(positionRef);
                }
            }
            this.insertBatch(refs);
        }

        return null;
    }

    @Override
    public Object delSysUserPositionRef(String userCode, String orgCode, Long updateuser){
        List<SysUserPositionRefVo> userPositionRefList = this.getUserPositionRef(userCode, orgCode);
        if(userPositionRefList != null && userPositionRefList.size() > 0){
            for(SysUserPositionRefVo userPositionRefVo : userPositionRefList){
                SysUserPositionRef userPositionRef = new SysUserPositionRef();
                BeanUtils.copyProperties(userPositionRefVo, userPositionRef);
                userPositionRef.setUpdateUser(updateuser);
                userPositionRef.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
                userPositionRef.setStatusDate(new Date());
                this.updateById(userPositionRef);
            }
        }
        return null;
    }

    @Override
    public List<SysUserPositionRefVo> getUserPositionRef(String userCode, String orgCode){
        return sysUserPositionRefMapper.getUserPositionRef(userCode, orgCode);
    }

    @Override
    public void delUserPositionDefByUserCode(String userCode, Long updateUser){
        sysUserPositionRefMapper.delUserPositionDef(userCode, updateUser);
    }

    public List<String> getReList(List<SysUserPositionRefVo> userPositionRefList){
        List<String> list = new ArrayList<String>();
        if(userPositionRefList != null && userPositionRefList.size() > 0){
            for(SysUserPositionRefVo ref : userPositionRefList){
                list.add(ref.getPositionCode());
            }
            HashSet h = new HashSet(list);
            list.clear();
            list.addAll(h);
        }
        return list;
    }
}
