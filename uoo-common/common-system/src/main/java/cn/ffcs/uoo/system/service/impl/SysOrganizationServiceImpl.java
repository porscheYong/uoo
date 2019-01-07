package cn.ffcs.uoo.system.service.impl;


import cn.ffcs.uoo.system.dao.SysOrganizationMapper;
import cn.ffcs.uoo.system.entity.SysOrganization;
import cn.ffcs.uoo.system.service.SysOrganizationService;
import cn.ffcs.uoo.system.service.SysPositionService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.SysOrganizationVo;
import cn.ffcs.uoo.system.vo.SysPositionVo;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysPositionService sysPositionService;
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
        if(vos!=null && vos.size()>0){
            for(TreeNodeVo vo : vos){
                if(isLeaf(vo.getId())){
                    vo.setParent(true);
                }else{
                    vo.setParent(false);
                }
            }
        }
        return vos;
    }

    public boolean isLeaf(String id){
        int num = baseMapper.isleaf(id);
        if(num>0){
            return true;
        }
        return false;
    }

    /**
     *  获取全量组织树
     * @return
     */
    @Override
    public List<TreeNodeVo> selectOrgTree(){
        List<TreeNodeVo> vos = new ArrayList<>();
        vos = baseMapper.selectOrgTree();
        return vos;
    }

    /**
     * 检索组织树
     * @param vo
     * @return
     */
    @Override
    public Page<SysOrganizationVo> selectFuzzyOrgRelPage(SysOrganizationVo vo){
        Page<SysOrganizationVo> page = new Page<SysOrganizationVo>(vo.getPageNo()==0?1:vo.getPageNo(),
                vo.getPageSize()==0?10:vo.getPageSize());
        List<SysOrganizationVo> orgVolist = baseMapper.selectFuzzyOrgRelPage(page,vo);
        page.setRecords(orgVolist);
        return page;
    }

    /**
     * 获取组织树
     * @param vo
     * @return
     */
    @Override
    public List<TreeNodeVo> getRestructOrgRelTree(SysOrganizationVo vo){
        List<TreeNodeVo> list = new ArrayList<>();
        list = baseMapper.getRestructOrgRelTree(vo);
        return list;
    }

    @Override
    public Page<SysOrganizationVo> getOrgRelPage(SysOrganizationVo vo){
        Page<SysOrganizationVo> page = new Page<SysOrganizationVo>(vo.getPageNo()==0?1:vo.getPageNo(),
                vo.getPageSize()==0?10:vo.getPageSize());
        List<SysOrganizationVo> list = new ArrayList<>();
        list = baseMapper.getOrgRelPage(page,vo);
        page.setRecords(list);
        return page;
    }

    @Override
    public SysOrganizationVo getOrg(String orgCode){
        SysOrganizationVo vo = baseMapper.getOrg(orgCode);
        if(vo!=null){
            List<SysPositionVo> list = sysPositionService.getSysOrgPosition(orgCode);
            vo.setSysPositionVos(list);
        }
        return vo;
    }
}
