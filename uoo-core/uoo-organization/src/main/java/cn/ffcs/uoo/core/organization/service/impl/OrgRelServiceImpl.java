package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.OrgRel;
import cn.ffcs.uoo.core.organization.dao.OrgRelMapper;
import cn.ffcs.uoo.core.organization.entity.OrgType;
import cn.ffcs.uoo.core.organization.service.OrgRelService;
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

    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public void delete(OrgRel orgRel){
        orgRel.setStatusCd("1100");
        orgRel.setStatusDate(new Date());
        orgRel.setUpdateDate(new Date());
        orgRel.setUpdateUser(0L);
        updateById(orgRel);
    }

    @Override
    public void add(OrgRel orgRel){
        orgRel.setCreateDate(new Date());
        orgRel.setCreateUser(0L);
        orgRel.setStatusCd("1000");
        orgRel.setStatusDate(new Date());
        insert(orgRel);
    }


    @Override
    public void update(OrgRel orgRel){
        orgRel.setUpdateDate(new Date());
        orgRel.setUpdateUser(0L);
        orgRel.setStatusDate(new Date());
        updateById(orgRel);
    }


    @Override
    public List<TreeNodeVo> queryOrgTree(String orgRootId, String refCode, String pid, boolean isRoot){
        List<TreeNodeVo> volist = new ArrayList<>();
        if(StrUtil.isNullOrEmpty(pid)){
            volist = orgRelMapper.queryOrgTreeRoot(orgRootId);
        }else{
            volist = orgRelMapper.queryOrgTreeChilden(orgRootId,pid);
        }
        for(TreeNodeVo vo : volist){
            isLeaf(vo,orgRootId);
        }
        return volist;
    }

    /**
     * 是否是叶子节点
     * @param treeNodeVo
     * @return
     */
    public boolean isLeaf(TreeNodeVo treeNodeVo,String orgRootId){
        List<TreeNodeVo> li = orgRelMapper.isLeaf(orgRootId,treeNodeVo.getId());
        if(li==null || li.size()<0){
            treeNodeVo.setParent(false);
            return false;
        }
        treeNodeVo.setParent(true);
        return true;
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
     * @param orgleafId
     * @param orgRootId
     * @return
     */
    @Override
    public List<TreeNodeVo> selectFuzzyOrgRelTree(String orgleafId,String orgRootId,boolean isFull){
        List<TreeNodeVo> list = baseMapper.selectFuzzyOrgRelTree(orgleafId,orgRootId);
        if(list!=null && list.size()>0){
            if(isFull){
                return baseMapper.selectFuzzyFullOrgRelTree(orgleafId,orgRootId);
            }else{
                return baseMapper.selectFuzzyOrgRelTree(orgleafId,orgRootId);
            }
        }
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
        page.setRecords(orgVolist);
        return page;
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
}
