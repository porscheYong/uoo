package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.util.StrUtil;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.entity.SysPositiontRoleRef;
import cn.ffcs.uoo.system.dao.SysPositiontRoleRefMapper;
import cn.ffcs.uoo.system.service.ISysPositiontRoleRefService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 对一定职位可以默认具备一些角色 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-20
 */
@Service
public class SysPositiontRoleRefServiceImpl extends ServiceImpl<SysPositiontRoleRefMapper, SysPositiontRoleRef> implements ISysPositiontRoleRefService {
    /**
     * 获取seq
     * @return
     */
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    /**
     * 失效状态
     * @param sysPositiontRoleRef
     */
    @Override
    public void delete(SysPositiontRoleRef sysPositiontRoleRef){
        sysPositiontRoleRef.setStatusCd("1100");
        sysPositiontRoleRef.setStatusDate(new Date());
        sysPositiontRoleRef.setUpdateDate(new Date());
        sysPositiontRoleRef.setUpdateUser(StrUtil.isNullOrEmpty(sysPositiontRoleRef.getUpdateUser())?0L:sysPositiontRoleRef.getUpdateUser());
        updateById(sysPositiontRoleRef);
    }



    /**
     * 新增
     */
    @Override
    public void add(SysPositiontRoleRef sysPositiontRoleRef){
        sysPositiontRoleRef.setCreateDate(new Date());
        sysPositiontRoleRef.setCreateUser(StrUtil.isNullOrEmpty(sysPositiontRoleRef.getCreateUser())?0L:sysPositiontRoleRef.getCreateUser());
        sysPositiontRoleRef.setStatusCd("1000");
        sysPositiontRoleRef.setStatusDate(new Date());
        insert(sysPositiontRoleRef);
    }

    /**
     * 更新
     */
    @Override
    public void update(SysPositiontRoleRef sysPositiontRoleRef){
        sysPositiontRoleRef.setUpdateDate(new Date());
        sysPositiontRoleRef.setUpdateUser(StrUtil.isNullOrEmpty(sysPositiontRoleRef.getUpdateUser())?0L:sysPositiontRoleRef.getUpdateUser());
        sysPositiontRoleRef.setStatusDate(new Date());
        updateById(sysPositiontRoleRef);
    }

    /**
     * 更新
     */
    @Override
    public List<SysPositiontRoleRef> getCurRoleList(String positionCode){
        return baseMapper.getCurRoleList(positionCode);
    }

}
