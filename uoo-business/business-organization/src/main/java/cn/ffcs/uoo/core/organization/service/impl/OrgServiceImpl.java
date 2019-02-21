package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.*;
import cn.ffcs.uoo.core.organization.dao.OrgMapper;
import cn.ffcs.uoo.core.organization.service.*;
import cn.ffcs.uoo.core.organization.util.OrgConstant;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.AreaCodeVo;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import cn.ffcs.uoo.core.organization.vo.PageVo;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Service("orgService")
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {


    @Autowired
    private OrgTypeService orgTypeService;

    @Autowired
    private OrgOrgtreeRelService orgOrgtreeRelService;

    @Autowired
    private OrgRelService orgRelService;

    @Autowired
    private OrgRelTypeService orgRelTypeService;

    @Autowired
    private ModifyHistoryService modifyHistoryService;

    @Override
    public Long getId(){
        return baseMapper.getId();
    }


    @Override
    public void delete(Org org){
        org.setStatusCd("1100");
        org.setStatusDate(new Date());
        org.setUpdateDate(new Date());
        org.setUpdateUser(StrUtil.isNullOrEmpty(org.getUpdateUser())?0L:org.getUpdateUser());
        updateById(org);
    }

    @Override
    public void add(Org org){
        org.setCreateDate(new Date());
        org.setCreateUser(StrUtil.isNullOrEmpty(org.getCreateUser())?0L:org.getCreateUser());
        org.setStatusCd("1000");
        org.setStatusDate(new Date());
        insert(org);
    }


    @Override
    public void update(Org org){
        org.setUpdateDate(new Date());
        org.setUpdateUser(StrUtil.isNullOrEmpty(org.getUpdateUser())?0L:org.getUpdateUser());
        org.setStatusDate(new Date());
        updateById(org);
    }
    /**
     * 查询组织列表
     * @param org
     * @return
     */
    @Override
    public List<Org> getOrgList(Org org){
        return baseMapper.getOrgList(org);
    }

    @Override
    public String getGenerateOrgCode(){
        Long seq = baseMapper.getOrgCodeSeq();
        DecimalFormat decimalFormat = new DecimalFormat("000000000");
        String orgCodeEnd = decimalFormat.format(seq);
        return OrgConstant.ORG_CODE_N + orgCodeEnd;
    }

    /**
     * 查询组织关系分页
     * @param orgVo
     * @return
     */
    @Override
    public Page<OrgVo> selectOrgRelPage(OrgVo orgVo){

        Page<OrgVo> page = new Page<OrgVo>(orgVo.getPageNo()==0?1:orgVo.getPageNo(),
                orgVo.getPageSize()==0?10:orgVo.getPageSize());
        List<OrgVo> orgVolist = baseMapper.selectOrgRelPage(page,orgVo);
//        for(OrgVo o : orgVolist){
//            List<OrgType> orgTypeList = orgTypeService.getOrgTypeByOrgId(o.getOrgId());
//            String orgTypeSplit = "";
//            if(orgTypeList!=null && orgTypeList.size()>0){
//                for(OrgType ot:orgTypeList){
//                    orgTypeSplit +=ot.getOrgTypeName()+",";
//                }
//            }
//            orgTypeSplit = orgTypeSplit.substring(0,orgTypeSplit.length()-1);
//            o.setOrgTypeSplit(orgTypeSplit);
//            //o.setOrgTypeList(orgTypeList);
//        }
        page.setRecords(orgVolist);
        return page;
    }

    /**
     * 组织分页
     * @param orgVo
     * @return
     */
    @Override
    public Page<OrgVo> selectOrgPage(OrgVo orgVo){
        Page<OrgVo> page = new Page<OrgVo>(orgVo.getPageNo()==0?1:orgVo.getPageNo(),
                orgVo.getPageSize()==0?10:orgVo.getPageSize());
        List<OrgVo> orgVolist = baseMapper.selectOrgPage(page,orgVo);
        if(orgVolist!=null && orgVolist.size()>0){
            for(OrgVo vo : orgVolist){
                List<OrgVo> orgListVo = baseMapper.getFullOrgList("1",vo.getOrgId().toString());
                String fullName = "";
                if(orgListVo!=null && orgListVo.size()>0){
                    for(OrgVo vo1 : orgListVo){
                        fullName += vo1.getOrgName()+"->";
                    }
                }
                if(!StrUtil.isNullOrEmpty(fullName)){
                    vo.setFullName(fullName.substring(0,fullName.length()-2));
                }
                String trees = baseMapper.getAppOrgTrees(vo.getOrgId().toString());
                vo.setOrgTreeInfos(trees);
//                if(!StrUtil.isNullOrEmpty(orgVo.getOrgTreeId())){
//                    com.baomidou.mybatisplus.mapper.Wrapper orgOrgTreeWrapper = Condition.create()
//                            .eq("ORG_TREE_ID",orgVo.getOrgTreeId())
//                            .eq("ORG_ID",vo.getOrgId())
//                            .eq("STATUS_CD","1000");
//                    List<OrgOrgtreeRel> orgOrgtreeList = orgOrgtreeRelService.selectList(orgOrgTreeWrapper);
//                    if(orgOrgtreeList!=null && orgOrgtreeList.size()>0){
//                        vo.setFlag(1);
//                    }
//                }

            }
        }
        page.setRecords(orgVolist);
        return page;
    }
    @Override
    public String JudgeOrgParams(OrgVo org){
        if(StrUtil.isNullOrEmpty(org.getStatusCd())){
            return "组织状态不能为空";
        }
        if(StrUtil.isNullOrEmpty(org.getOrgTreeId())){
            return "组织树标识不能为空";
        }
        if(org.getOrgTypeList() == null || org.getOrgTypeList().size() < 0){
            return "组织类别不能为空";
        }
        if(StrUtil.isNullOrEmpty(org.getAreaCodeId())){
            return "组织区号不能为空";
        }
//        if(org.getPositionList() == null || org.getPositionList().size() <0){
//            return "组织岗位不能为空";
//        }
//        if(org.getPostList() == null || org.getPostList().size() <0){
//            return "组织职位不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getOrgRootId())){
//            return "组织树根节点不能为空";
//        }
        if(StrUtil.isNullOrEmpty(org.getOrgName())){
            return "组织名称不能为空";
        }
//        if(StrUtil.isNullOrEmpty(org.getLocId())){
//            return "组织行政区域不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getSupOrgId())){
//            return "组织父节点不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getShortName())){
//            return "组织简称不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getCityTown())){
//            return "城乡属性不能为空";
//        }
        return "";
    }

//    @Override
//    public void insertByObj(OrgVo org){
//        baseMapper.insertByObj(org);
//    }


    @Override
    public String getSysFullName(String orgTreeId,String orgId){
        return baseMapper.getSysFullName(orgTreeId,orgId);
    }

    @Override
    public OrgVo selectOrgByOrgId(String orgId,String orgTreeId){
        return baseMapper.selectOrgByOrgId(orgId,orgTreeId);
    }
    @Override
    public List<PoliticalLocation> getOrgLoc(String orgId){
        return baseMapper.getOrgLoc(orgId);
    }

    @Override
    public List<AreaCodeVo> getOrgAreaCode(String orgId){
        return baseMapper.getOrgAreaCode(orgId);
    }



    /**
     * 获取组织名称全路
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public String getFullOrgNameList(String orgTreeId,String orgId,String split){
        List<OrgVo> voList = baseMapper.getFullOrgList(orgTreeId,orgId);
        String fullName = "";
        if(voList!=null && voList.size()>0){
            for(int i=0;i<voList.size();i++){
                fullName += voList.get(i).getOrgName();
                if(!StrUtil.isNullOrEmpty(split)){
                    fullName += split;
                }
            }
            if(!StrUtil.isNullOrEmpty(fullName) && !StrUtil.isNullOrEmpty(split)){
                fullName = fullName.substring(0,fullName.length()-split.length());
            }
        }
        return fullName;
    }


    /**
     * 获取组织标识名称全路径
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public String getFullOrgIdList(String orgTreeId,String orgId,String split){
        List<OrgVo> voList = baseMapper.getFullOrgList(orgTreeId,orgId);
        String fullNameId = "";
        if(voList!=null && voList.size()>0){
            for(int i=0;i<voList.size();i++){
                fullNameId += voList.get(i).getOrgId();
                if(!StrUtil.isNullOrEmpty(split)){
                    fullNameId += split;
                }
            }
            if(!StrUtil.isNullOrEmpty(fullNameId) && !StrUtil.isNullOrEmpty(split)){
                fullNameId = fullNameId.substring(0,fullNameId.length()-split.length());
            }
        }
        return fullNameId;
    }


    @Override
    public String JudgeMoveOrg(Long orgId,Long parentOrgId,String orgName,Long orgTreeId){
        if(StrUtil.isNullOrEmpty(orgId)){
            return "组织标识不能为空";
        }
        if(StrUtil.isNullOrEmpty(parentOrgId)){
            return "组织父节点不能为空";
        }
        OrgVo curOrg = selectOrgByOrgId(orgId.toString(),orgTreeId.toString());
        if(StrUtil.isNullOrEmpty(curOrg)){
            return "组织不存在";
        }
        if(!curOrg.getOrgName().equals(orgName)){
            return "组织名称和现有组织名称不匹配";
        }
        OrgVo parentOrg = selectOrgByOrgId(parentOrgId.toString(),orgTreeId.toString());
        if(StrUtil.isNullOrEmpty(parentOrg)){
            return "移动的父组织不存在";
        }
        // TODO: 2019/1/28 组织全程
        String fullNameSplit = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),orgId.toString(),",");
        String fullParentNameSplit = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),parentOrgId.toString(),",");
        fullNameSplit+=",";
        fullParentNameSplit+=",";
        if(fullParentNameSplit.contains(fullNameSplit)){
            return "节点不能移动到该节点的子节点上";
        }
//        String fullName = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),orgId.toString(),"");
//        com.baomidou.mybatisplus.mapper.Wrapper orgOrgTreeWrapper = Condition.create()
//                .eq("ORG_TREE_ID",orgTreeId)
//                .eq("STATUS_CD","1000")
//                .like("ORG_BIZ_FULL_NAME",fullName,SqlLike.RIGHT);
//        int count = orgOrgtreeRelService.selectCount(orgOrgTreeWrapper);
//        if(count>50){
//            return "移动组织的下级组织数量太大，请联系管理员操作";
//        }
        return "";
    }


    @Override
    public String moveOrg(Long orgId,Long parentOrgId,Long orgTreeId,Long userId,String batchNumber){
        String str = "";
        OrgVo parentOrg = selectOrgByOrgId(parentOrgId.toString(),orgTreeId.toString());
        if(StrUtil.isNullOrEmpty(parentOrg)){
            return "移动的父组织不存在";
        }
        //当前节点的父节点 全路径
        // TODO: 2019/1/28 组织全程
        String fullNameSplit = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),orgId.toString(),",");
        String fullParentNameSplit = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),parentOrgId.toString(),",");
        fullNameSplit+=",";
        fullParentNameSplit+=",";
        if(fullParentNameSplit.contains(fullNameSplit)){
            return "节点不能移动到该节点的子节点上";
        }
        String fullNameId = orgOrgtreeRelService.getFullBizOrgIdList(orgTreeId.toString(),orgId.toString(),"");
        com.baomidou.mybatisplus.mapper.Wrapper orgOrgTreeWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgTreeId)
                .eq("STATUS_CD","1000")
                .like("ORG_BIZ_FULL_ID",fullNameId,SqlLike.RIGHT);
//        int count = orgOrgtreeRelService.selectCount(orgOrgTreeWrapper);
//        if(count>50){
//            return "移动组织的下级组织数量太大，请联系管理员操作";
//        }
        List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
        orgRelTypeListCur = orgRelTypeService.getOrgRelType(orgTreeId.toString());
        OrgRelType ortCur = new OrgRelType();
        if(orgRelTypeListCur!=null && orgRelTypeListCur.size()>0){
            ortCur = orgRelTypeListCur.get(0);
        }
        com.baomidou.mybatisplus.mapper.Wrapper orgRelWrapper = Condition.create()
                .eq("REF_CODE",ortCur.getRefCode())
                .eq("STATUS_CD","1000")
                .eq("ORG_ID",orgId);
        OrgRel orgRel = orgRelService.selectOne(orgRelWrapper);
        OrgRel orgRelOld = new OrgRel();
        BeanUtils.copyProperties(orgRel,orgRelOld);
        orgRel.setUpdateUser(userId);
        orgRel.setParentOrgId(parentOrgId);
        orgRelService.update(orgRel);
        modifyHistoryService.addModifyHistory(orgRelOld,orgRel,userId,batchNumber);

        List<OrgOrgtreeRel> orgOrgTreeRels =  orgOrgtreeRelService.selectList(orgOrgTreeWrapper);
        if(orgOrgTreeRels!=null && orgOrgTreeRels.size()>0){
            for(OrgOrgtreeRel ootr : orgOrgTreeRels){
                String fullName = orgOrgtreeRelService.getFullBizOrgNameList(orgTreeId.toString(),ootr.getOrgId().toString(),"");
                String fullOrgId = orgOrgtreeRelService.getFullBizOrgIdList(orgTreeId.toString(),ootr.getOrgId().toString(),",");
                fullOrgId =","+fullOrgId+",";
                ootr.setOrgBizFullName(fullName);
                ootr.setOrgBizFullId(fullOrgId);
                orgOrgtreeRelService.update(ootr);
            }
        }
        return str;
    }


    @Override
    public List<TreeNodeVo> getFullOrgVo(String orgTreeId, String orgId){
        List<OrgVo> orgList = baseMapper.getFullOrgList(orgTreeId,orgId);
        List<TreeNodeVo> treeNodes = new ArrayList<>();
        if(orgList!=null && orgList.size()>0){
            for(OrgVo vo:orgList){
                TreeNodeVo nodeVo = new TreeNodeVo();
                nodeVo.setId(vo.getOrgId().toString());
                if(!StrUtil.isNullOrEmpty(vo.getSupOrgId())){
                    nodeVo.setPid(vo.getSupOrgId().toString());
                }
                nodeVo.setName(vo.getOrgName());
                treeNodes.add(nodeVo);
            }
        }
        return treeNodes;
    }
}
