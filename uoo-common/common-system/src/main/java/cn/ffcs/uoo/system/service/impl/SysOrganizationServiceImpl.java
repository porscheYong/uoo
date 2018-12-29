package cn.ffcs.uoo.system.service.impl;


import cn.ffcs.uoo.system.dao.SysOrganizationMapper;
import cn.ffcs.uoo.system.entity.SysOrganization;
import cn.ffcs.uoo.system.service.SysOrganizationService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@Service
public class SysOrganizationServiceImpl extends ServiceImpl<SysOrganizationMapper, SysOrganization> implements SysOrganizationService {

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
     * @param sysOrganization
     */
    @Override
    public void delete(SysOrganization sysOrganization){
        sysOrganization.setStatusCd("1100");
        sysOrganization.setStatusDate(new Date());
        sysOrganization.setUpdateDate(new Date());
        sysOrganization.setUpdateUser(0L);
        updateById(sysOrganization);
    }



    /**
     * 新增
     */
    @Override
    public void add(SysOrganization sysOrganization){
        sysOrganization.setCreateDate(new Date());
        sysOrganization.setCreateUser(0L);
        sysOrganization.setStatusCd("1000");
        sysOrganization.setStatusDate(new Date());
        insert(sysOrganization);
    }

    /**
     * 更新
     */
    @Override
    public void update(SysOrganization sysOrganization){
        sysOrganization.setUpdateDate(new Date());
        sysOrganization.setUpdateUser(0L);
        sysOrganization.setStatusDate(new Date());
        updateById(sysOrganization);
    }

    /**
     * 获取组织树
     * @param id
     * @return
     */
    @Override
    public List<TreeNodeVo> selectOrgTree(String id){
        List<TreeNodeVo> vos = new ArrayList<>();
        if(StrUtil.isNullOrEmpty(id)){
            vos = baseMapper.getTreeRoot();
        }else{
            vos = baseMapper.getTreeChild(id);
        }
        return vos;
    }

    /**
     *  获取全量组织树
     * @return
     */
    @Override
    public List<TreeNodeVo> selectOrgTree(){
        List<TreeNodeVo> vos = new ArrayList<>();
        return vos;
    }
}
