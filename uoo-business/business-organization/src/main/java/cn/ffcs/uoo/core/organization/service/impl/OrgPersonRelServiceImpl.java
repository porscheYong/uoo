package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.OrgPersonRel;
import cn.ffcs.uoo.core.organization.dao.OrgPersonRelMapper;
import cn.ffcs.uoo.core.organization.entity.Post;
import cn.ffcs.uoo.core.organization.service.OrgPersonRelService;
import cn.ffcs.uoo.core.organization.service.OrgService;
import cn.ffcs.uoo.core.organization.service.PostService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
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
 * @since 2018-10-21
 */
@Service
public class OrgPersonRelServiceImpl extends ServiceImpl<OrgPersonRelMapper, OrgPersonRel> implements OrgPersonRelService {

    @Autowired
    private OrgService orgService;

    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public void delete(OrgPersonRel orgPersonRel){
        orgPersonRel.setStatusCd("1100");
        orgPersonRel.setStatusDate(new Date());
        orgPersonRel.setUpdateDate(new Date());
        orgPersonRel.setUpdateUser(StrUtil.isNullOrEmpty(orgPersonRel.getUpdateUser())?0L:orgPersonRel.getUpdateUser());
        updateById(orgPersonRel);
    }


    @Override
    public void add(OrgPersonRel orgPersonRel){
        orgPersonRel.setCreateDate(new Date());
        orgPersonRel.setCreateUser(StrUtil.isNullOrEmpty(orgPersonRel.getCreateUser())?0L:orgPersonRel.getCreateUser());
        orgPersonRel.setStatusCd("1000");
        orgPersonRel.setStatusDate(new Date());
        insert(orgPersonRel);
    }


    @Override
    public void update(OrgPersonRel orgPersonRel){
        orgPersonRel.setUpdateDate(new Date());
        orgPersonRel.setUpdateUser(StrUtil.isNullOrEmpty(orgPersonRel.getUpdateUser())?0L:orgPersonRel.getUpdateUser());
        orgPersonRel.setStatusDate(new Date());
        updateById(orgPersonRel);
    }

    @Override
    public String judgeOrgPsnParams(PsonOrgVo psonOrgVo){
        if(StrUtil.isNullOrEmpty(psonOrgVo.getPersonnelId())){
            return "人员标识不能为空";
        }
        if(StrUtil.isNullOrEmpty(psonOrgVo.getRefType())){
            return "组织人员关系类型不能为空";
        }
        if(StrUtil.isNullOrEmpty(psonOrgVo.getProperty())){
            return "用工性质不能为空";
        }
        if(StrUtil.isNullOrEmpty(psonOrgVo.getOrgId())){
            return "组织标识不能为空";
        }
        if(StrUtil.isNullOrEmpty(psonOrgVo.getOrgRootId())){
            return "组织树根节点不能为空";
        }
        return null;
    }

    /**
     * vo转换成对象
     * @param psonOrgVo
     * @return
     */
    @Override
    public OrgPersonRel convertObj(PsonOrgVo psonOrgVo){
        OrgPersonRel orgPersonRel = new OrgPersonRel();
//        if(!StrUtil.isNullOrEmpty(psonOrgVo.getPersonnelId())){
//            orgPersonRel.setOrgPersonId(psonOrgVo.getOrgPersonId());
//        }
        orgPersonRel.setOrgId(psonOrgVo.getOrgId());
        orgPersonRel.setPersonnelId(psonOrgVo.getPersonnelId());
        if(!StrUtil.isNullOrEmpty(psonOrgVo.getPostId())){
            orgPersonRel.setPostId(Long.valueOf(psonOrgVo.getPostId()));
        }
        orgPersonRel.setRefType(StrUtil.strnull(psonOrgVo.getRefType()));
        orgPersonRel.setProperty(StrUtil.strnull(psonOrgVo.getProperty()));
        orgPersonRel.setDoubleName(StrUtil.strnull(psonOrgVo.getDoubleName()));
        orgPersonRel.setSort(StrUtil.isNullOrEmpty(psonOrgVo.getSort())?1:Double.valueOf(psonOrgVo.getSort()));
        orgPersonRel.setStatusCd(StrUtil.isNullOrEmpty(psonOrgVo.getStatusCd())?"1000":psonOrgVo.getStatusCd());
        return orgPersonRel;
    }

    /**
     * 获取人员组织关系
     * @param psonOrgVo
     * @return
     */
    @Override
    public List<PsonOrgVo> getPerOrgRelList(PsonOrgVo psonOrgVo){
        return baseMapper.getPerOrgRelList(psonOrgVo);
    }

    /**
     * 获取人员组织关系翻页
     * @param psonOrgVo
     * @return
     */
    @Override
    public Page<PsonOrgVo> selectPerOrgRelPage(PsonOrgVo psonOrgVo){
        Page<PsonOrgVo> page = new Page<PsonOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo()
                ,psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<PsonOrgVo> list = baseMapper.selectPerOrgRelPage(page,psonOrgVo);
        for(PsonOrgVo psOrg : list){
            if(!StrUtil.isNullOrEmpty(psOrg.getOrgId())){
                Wrapper orgWrapper = Condition.create()
                        .eq("ORG_ID", psOrg.getOrgId())
                        .eq("STATUS_CD", "1000");
                Org org = orgService.selectOne(orgWrapper);
                psOrg.setOrgName(StrUtil.strnull(org.getOrgName()));
            }
        }
        page.setRecords(list);
        return page;
    }

    /**
     * 获取人员组织关系翻页
     * @param psonOrgVo
     * @return
     */
    @Override
    public Page<PsonOrgVo> selectOrgRelsByPerIdPage(PsonOrgVo psonOrgVo){
        Page<PsonOrgVo> page = new Page<PsonOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo()
                ,psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<PsonOrgVo> list = baseMapper.selectOrgRelsByPerIdPage(page,psonOrgVo);
        for(PsonOrgVo psOrg : list){
            if(!StrUtil.isNullOrEmpty(psOrg.getPostId())){
                Post post = baseMapper.getPost(psOrg.getPostId());
                psOrg.setPostName(StrUtil.strnull(post.getPostName()));
            }
        }
        page.setRecords(list);
        return page;
    }


    /**
     * 获取全量人员组织关系翻页
     * @param psonOrgVo
     * @return
     */
    @Override
    public Page<PsonOrgVo> selectAllPerOrgRelPage(PsonOrgVo psonOrgVo){
        Page<PsonOrgVo> page = new Page<PsonOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo()
                ,psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<PsonOrgVo> list = baseMapper.selectAllPerOrgRelPage(page,psonOrgVo);
        for(PsonOrgVo psOrg : list){
            if(!StrUtil.isNullOrEmpty(psOrg.getOrgId())){
                Wrapper orgWrapper = Condition.create()
                        .eq("ORG_ID", psOrg.getOrgId())
                        .eq("STATUS_CD", "1000");
                Org org = orgService.selectOne(orgWrapper);
                psOrg.setOrgName(StrUtil.strnull(org.getOrgName()));
            }
        }
        page.setRecords(list);
        return page;
    }

    /**
     * 用户组织查询翻页
     * @param psonOrgVo
     * @return
     */
    @Override
    public Page<PsonOrgVo> selectUserOrgRelPage(PsonOrgVo psonOrgVo){
        Page<PsonOrgVo> page = new Page<PsonOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo()
                ,psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<PsonOrgVo> list = baseMapper.selectUserOrgRelPage(page,psonOrgVo);
//        for(PsonOrgVo vo : list){
//            String cert = baseMapper.getCert(vo.getPersonnelId().toString());
//            vo.setCertNo(cert);
//        }
        page.setRecords(list);
        return page;
    }

    /**
     * 用户组织查询翻页
     * @param psonOrgVo
     * @return
     */
    @Override
    public Page<PsonOrgVo> selectAllUserOrgRelPage(PsonOrgVo psonOrgVo){
        Page<PsonOrgVo> page = new Page<PsonOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo()
                ,psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<PsonOrgVo> list = baseMapper.selectAllUserOrgRelPage(page,psonOrgVo);
//        for(PsonOrgVo vo : list){
//            String cert = baseMapper.getCert(vo.getPersonnelId().toString());
//            vo.setCertNo(cert);
//        }
        page.setRecords(list);
        return page;
    }
    /**
     * 人员查询
     * @param psonOrgVo
     * @return
     */
    @Override
    public Page<PsonOrgVo> selectPerOrOrgRelPage(PsonOrgVo psonOrgVo){
        Page<PsonOrgVo> page = new Page<PsonOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo()
                ,psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<PsonOrgVo> list = baseMapper.selectPerOrOrgRelPage(page,psonOrgVo);
        page.setRecords(list);
        return page;
    }

    /**
     * 查询组织人员检索
     * @param psonOrgVo
     * @return
     */
    @Override
    public Page<PsonOrgVo> selectFuzzyOrgPsnPage(PsonOrgVo psonOrgVo){
        Page<PsonOrgVo> page = new Page<PsonOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo()
                ,psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<PsonOrgVo> list = baseMapper.selectFuzzyOrgPsnPage(page,psonOrgVo);
        page.setRecords(list);
        return page;
    }

    /**
     * 查询组织树组织人员
     * @param orgTreeId
     * @param personnelId
     * @return
     */
    @Override
    public List<OrgPersonRel> getOrgPsnByOrgAndPsnId(String orgTreeId,String personnelId,String orgId){
        List<OrgPersonRel> list = new ArrayList<OrgPersonRel>();
        list = baseMapper.getOrgPsnByOrgTreeAndPsnId(orgTreeId,personnelId,orgId);
        return list;
    }
    /**
     * 查询组织树组织人员
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public List<OrgPersonRel> getOrgPsnRel(String orgTreeId,String orgId){
        return baseMapper.getOrgPsnRel(orgTreeId,orgId);
    }
    /**
     * 查询组织树账号人员
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public List<OrgPersonRel>  getOrgAcctRel(String orgTreeId,String orgId){
        return baseMapper. getOrgAcctRel(orgTreeId,orgId);
    }
}
