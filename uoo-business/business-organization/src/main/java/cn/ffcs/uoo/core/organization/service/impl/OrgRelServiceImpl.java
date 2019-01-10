package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.core.organization.dao.OrgMapper;
import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.OrgRel;
import cn.ffcs.uoo.core.organization.dao.OrgRelMapper;
import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.entity.OrgType;
import cn.ffcs.uoo.core.organization.service.OrgRelService;
import cn.ffcs.uoo.core.organization.service.OrgService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.OrgRefTypeVo;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
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
 * @since 2018-09-25
 */
@Service
public class OrgRelServiceImpl extends ServiceImpl<OrgRelMapper, OrgRel> implements OrgRelService {
    @Autowired
    private OrgRelMapper orgRelMapper;

    @Autowired
    private OrgMapper orgMapper;

    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public void delete(OrgRel orgRel){
        orgRel.setStatusCd("1100");
        orgRel.setStatusDate(new Date());
        orgRel.setUpdateDate(new Date());
        orgRel.setUpdateUser(StrUtil.isNullOrEmpty(orgRel.getUpdateUser())?0L:orgRel.getUpdateUser());
        updateById(orgRel);
    }

    @Override
    public void add(OrgRel orgRel){
        orgRel.setCreateDate(new Date());
        orgRel.setCreateUser(StrUtil.isNullOrEmpty(orgRel.getCreateUser())?0L:orgRel.getCreateUser());
        orgRel.setStatusCd("1000");
        orgRel.setStatusDate(new Date());
        insert(orgRel);
    }


    @Override
    public void update(OrgRel orgRel){
        orgRel.setUpdateDate(new Date());
        orgRel.setUpdateUser(StrUtil.isNullOrEmpty(orgRel.getUpdateUser())?0L:orgRel.getUpdateUser());
        orgRel.setStatusDate(new Date());
        updateById(orgRel);
    }


    @Override
    public List<TreeNodeVo> queryOrgTree(String orgTreeId, String orgRootId, String refCode, String pid, boolean isRoot,
                                         String orgParams,String orgOrgTypeParams){
        List<TreeNodeVo> volist = new ArrayList<>();
        if(StrUtil.isNullOrEmpty(pid)){
            volist = orgRelMapper.queryOrgTreeRoot(orgTreeId,orgRootId);
        }else{
            volist = orgRelMapper.queryOrgTreeChilden(orgTreeId,pid,orgParams,orgOrgTypeParams);
        }
        for(TreeNodeVo vo : volist){
            isLeaf(vo,orgTreeId);
        }
        return volist;
    }

    /**
     * 是否是叶子节点
     * @param treeNodeVo
     * @return
     */
    public boolean isLeaf(TreeNodeVo treeNodeVo,String orgTreeId){
        int count = orgRelMapper.leafCount(orgTreeId,treeNodeVo.getId());
        if(count>0){
            treeNodeVo.setParent(true);
            return true;
        }
        treeNodeVo.setParent(false);
        return false;
    }

    /**
     * 是否存在子页节点
     * @param orgId
     * @param orgTreeId
     * @return
     */
    @Override
    public boolean isLeaf(String orgId,String orgTreeId){
        int count = orgRelMapper.leafCount(orgTreeId,orgId);
        if(count>0){
            return true;
        }
        return false;
    }


    /**
     * 获取组织关系类型
     * @param org
     * @return
     */
    @Override
    public List<OrgRefTypeVo> getOrgRelType(Org org){
        List<OrgRefTypeVo> orgRefTypeVoList = new ArrayList<>();
        orgRefTypeVoList = orgRelMapper.getOrgRelType(org);
        return orgRefTypeVoList;
    }

    /**
     * 组织类型分页
     * @param orgRefTypeVo
     * @return
     */
    @Override
    public Page<OrgRefTypeVo> selectOrgRelTypePage(OrgRefTypeVo orgRefTypeVo){
        Page<OrgRefTypeVo> page = new Page<OrgRefTypeVo>(orgRefTypeVo.getPageNo()==0?1:orgRefTypeVo.getPageNo()
                ,orgRefTypeVo.getPageSize()==0?10:orgRefTypeVo.getPageSize());
        List<OrgRefTypeVo> list = baseMapper.selectOrgRelTypePage(page,orgRefTypeVo);
        page.setRecords(list);
        return page;
    }

    /**
     * 查询组织关系是否存在
     * @param org
     * @return
     */
    @Override
    public List<OrgRel> selectOrgRel(Org org){
        return baseMapper.selectOrgRel(org);
    }

    /**
     * 搜素组织信息
     * @param search
     * @param orgRootId
     * @return
     */
    @Override
    public List<Org> selectFuzzyOrgRel(String search, String orgRootId){
        return baseMapper.selectFuzzyOrgRel(search,orgRootId);
    }
    /**
     * 搜素组织总量
     * @param search
     * @param orgRootId
     * @return
     */
    @Override
    public int selectFuzzyOrgRelCount(String search, String orgRootId){
        return baseMapper.selectFuzzyOrgRelCount(search,orgRootId);
    }

    /**
     * 查询检索的组织树信息
     * @param orgId
     * @param orgTreeId
     * @return
     */
    @Override
    public List<TreeNodeVo> selectFuzzyOrgRelTree(String orgId,String orgTreeId,boolean isFull){
        List<TreeNodeVo> list = new ArrayList<>();
        if(isFull){
            list = baseMapper.selectFuzzyFullOrgRelTree(orgId,orgTreeId);
            if(list!=null && list.size()>0){
                for(TreeNodeVo vo : list){
                    if(isLeaf(vo.getId(),orgTreeId)){
                        vo.setParent(true);
                    }else{
                        vo.setParent(false);
                    }
                }
            }
        }else{
            list = baseMapper.selectFuzzyOrgRelTree(orgId,orgTreeId);
        }
//        if(list!=null && list.size()>0){
//            if(isFull){
//                return baseMapper.selectFuzzyFullOrgRelTree(orgleafId,orgTreeId);
//            }else{
//                return baseMapper.selectFuzzyOrgRelTree(orgleafId,orgTreeId);
//            }
//        }
        return list;
    }

    /**
     * 查询检索的组织翻页
     * @param orgVo
     * @return
     */
    @Override
    public Page<OrgVo> selectFuzzyOrgRelPage(OrgVo orgVo){
        Page<OrgVo> page = new Page<OrgVo>(orgVo.getPageNo()==0?1:orgVo.getPageNo(),
                orgVo.getPageSize()==0?10:orgVo.getPageSize());
        List<OrgVo> orgVolist = baseMapper.selectFuzzyOrgRelPage(page,orgVo);
        if(orgVolist!=null && orgVolist.size()>0){
            for(OrgVo vo : orgVolist){
                List<OrgVo> orgListVo = orgMapper.getFullOrgList(orgVo.getOrgTreeId().toString(),vo.getOrgId().toString());
                String fullName = "";
                if(orgListVo!=null && orgListVo.size()>0){
                    for(OrgVo vo1 : orgListVo){
                        fullName += vo1.getOrgName()+"->";
                    }
                }
                if(!StrUtil.isNullOrEmpty(fullName)){
                    vo.setFullName(fullName.substring(0,fullName.length()-2));
                }
            }
        }
        page.setRecords(orgVolist);
        return page;
    }

    /**
     * 获取组织全路径列表
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public List<OrgVo> getFullOrgList(String orgTreeId,String orgId){
        return orgMapper.getFullOrgList(orgTreeId,orgId);
    }

    /**
     * 查询树对应的组织关系
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public List<OrgRel> getOrgRel(String orgTreeId, String orgId){
        return baseMapper.getOrgRel(orgTreeId,orgId);
    }

    /**
     * 获取指定组织树层级
     * @param orgRootId
     * @param lv
     * @param curOrgId
     * @param isFull
     * @return
     */
    @Override
    public List<TreeNodeVo> selectTarOrgRelTreeAndLv(String orgRootId,String orgTreeId, String lv, String curOrgId, boolean isFull){
        List<TreeNodeVo> list = new ArrayList<TreeNodeVo>();
        if(isFull){
            list = baseMapper.selectAllTarOrgRelTreeAndLv(orgRootId,orgTreeId,lv,curOrgId,isFull);
        }else{
            list = baseMapper.selectTarOrgRelTreeAndLv(orgRootId,orgTreeId,lv,curOrgId,isFull);
            //判断下级
            for(TreeNodeVo vo : list){
                if(!vo.getLevel().equals(lv)){
                    isLeaf(vo,orgTreeId);
                }
            }
        }
        if(list!=null && list.size()>0){
            return list;
        }
        return null;
    }
}
