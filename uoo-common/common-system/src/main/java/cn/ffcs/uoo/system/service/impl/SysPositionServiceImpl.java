package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.dao.SysPositionMapper;
import cn.ffcs.uoo.system.service.ModifyHistoryService;
import cn.ffcs.uoo.system.service.SysPositionService;
import cn.ffcs.uoo.system.dao.SysPositionMapper;
import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.service.SysPositionService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.SysPositionVo;
import cn.ffcs.uoo.system.vo.SysRoleDTO;
import cn.ffcs.uoo.system.vo.SysUserVo;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 根据不同部门的工作性质、责任轻重、难易程度和所需资格条件等进行分类，在平台上，不对职位进行过细的区分 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@Service
public class SysPositionServiceImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService {


    @Autowired
    private ModifyHistoryService modifyHistoryService;
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
     * @param sysPosition
     */
    @Override
    public void delete(SysPosition sysPosition){
        sysPosition.setStatusCd("1100");
        sysPosition.setStatusDate(new Date());
        sysPosition.setUpdateDate(new Date());
        sysPosition.setUpdateUser(StrUtil.isNullOrEmpty(sysPosition.getUpdateUser())?0L:sysPosition.getUpdateUser());
        updateById(sysPosition);
        modifyHistoryService.addModifyHistory(sysPosition,null,sysPosition.getUpdateUser(),sysPosition.getBatchNumber());
    }



    /**
     * 新增
     */
    @Override
    public void add(SysPosition sysPosition){
        sysPosition.setCreateDate(new Date());
        sysPosition.setCreateUser(StrUtil.isNullOrEmpty(sysPosition.getCreateUser())?0L:sysPosition.getCreateUser());
        sysPosition.setStatusCd("1000");
        sysPosition.setStatusDate(new Date());
        insert(sysPosition);
        modifyHistoryService.addModifyHistory(null,sysPosition,sysPosition.getCreateUser(),sysPosition.getBatchNumber());
    }

    /**
     * 更新
     */
    @Override
    public void update(SysPosition sysPosition){
        sysPosition.setUpdateDate(new Date());
        sysPosition.setUpdateUser(StrUtil.isNullOrEmpty(sysPosition.getUpdateUser())?0L:sysPosition.getUpdateUser());
        sysPosition.setStatusDate(new Date());
        updateById(sysPosition);

        Wrapper depWrapper = Condition.create()
                .eq("POSITION_ID",sysPosition.getPositionId())
                .eq("STATUS_CD","1000");
        SysPosition sysPositionOld = selectOne(depWrapper);
        modifyHistoryService.addModifyHistory(sysPositionOld,sysPosition,sysPosition.getUpdateUser(),sysPosition.getBatchNumber());

    }

    @Override
    public List<TreeNodeVo> selectPositionTree(String positionCode){
        List<TreeNodeVo> vos = new ArrayList<>();
        if(StrUtil.isNullOrEmpty(positionCode)){
            vos = baseMapper.getTreeRoot();
        }else{
            vos = baseMapper.getTreeChild(positionCode);
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

    @Override
    public List<TreeNodeVo> selectTarAllPositionTree(String positionCode){
        List<TreeNodeVo> vo = new ArrayList<>();
        if(StrUtil.isNullOrEmpty(positionCode)){
            vo = baseMapper.selectPositionTree();
        }else{
            vo = baseMapper.selectTarAllPositionTree(positionCode);
        }

        return vo;
    }


    @Override
    public Page<SysPositionVo> getPositionRelPage(SysPositionVo vo){
        Page<SysPositionVo> page = new Page<SysPositionVo>(vo.getPageNo()==0?1:vo.getPageNo(),
                vo.getPageSize()==0?10:vo.getPageSize());
        List<SysPositionVo> list = baseMapper.getPositionRelPage(page,vo);
        page.setRecords(list);
        return page;
    }

    @Override
    public String getRolesByPositionId(String positionId){
        return baseMapper.getRolesByPositionId(positionId);
    }

    @Override
    public SysPositionVo getPosition(String positionId){
        SysPositionVo vo = new SysPositionVo();
        vo = baseMapper.getPosition(positionId);
        if(vo!=null){
            List<SysRoleDTO> l = baseMapper.getPositionRoles(vo.getPositionCode());
            vo.setSysRoleDTOList(l);
        }
        return vo;
    }

    @Override
    public List<SysPositionVo> getSysOrgPosition(String orgCode){
        return baseMapper.getSysOrgPosition(orgCode);
    }
//    @Override
//    public Page<SysPositionVo> selectFuzzyPositionPage(SysPositionVo vo){
//        Page<SysPositionVo> page = new Page<SysPositionVo>(vo.getPageNo()==0?1:vo.getPageNo(),
//                vo.getPageSize()==0?10:vo.getPageSize());
//        List<SysPositionVo> list = baseMapper.selectFuzzyPositionPage(page,vo);
//        page.setRecords(list);
//        return page;
//    }


    @Override
    public int getPositionUserRefCount(String positionCode){
        return baseMapper.getPositionUserRefCount(positionCode);
    }
    @Override
    public int getPositionDepRefCount(String positionCode){
        return baseMapper.getPositionDepRefCount(positionCode);
    }
    @Override
    public int getPositionRoleRefCount(String positionCode){
        return baseMapper.getPositionRoleRefCount(positionCode);
    }

    @Override
    public Page<SysUserVo> getOrgUserPage(String id,
                                              String search,
                                              Integer pageSize,
                                              Integer pageNo,
                                              String isSearchlower){
        Page<SysUserVo> page = new Page<SysUserVo>(StrUtil.isNullOrEmpty(pageNo)?1:pageNo,
                StrUtil.isNullOrEmpty(pageSize)?10:pageSize);
        List<SysUserVo> list = baseMapper.getOrgUserPage(page,id,search,isSearchlower);
        for(SysUserVo vo : list){
            String positions = baseMapper.getUserOrgPosition(vo.getUserCode(),vo.getOrgCode());
            vo.setUserPositions(positions);
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<SysUserVo> getPositionUserPage(String id,
                                               String search,
                                               Integer pageSize,
                                               Integer pageNo,
                                               String isSearchlower){
        Page<SysUserVo> page = new Page<SysUserVo>(StrUtil.isNullOrEmpty(pageNo)?1:pageNo,
                StrUtil.isNullOrEmpty(pageSize)?10:pageSize);
        List<SysUserVo> list = baseMapper.getPositionUserPage(page,id,search,isSearchlower);
        page.setRecords(list);
        return page;
    }




}