package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.entity.*;
import cn.ffcs.uoo.core.organization.service.*;
import cn.ffcs.uoo.core.organization.service.impl.OrgServiceImpl;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.*;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@RestController
@RequestMapping(value = "/org")
@Api(value = "/org", description = "组织相关操作")
public class OrgController extends BaseController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgRelService orgRelService;

    @Autowired
    private OrgRelTypeService orgRelTypeService;

    @Autowired
    private OrgOrgtypeRelService orgTypeRefService;

    @Autowired
    private OrgPositionRelService orgPositionRelService;

    @Autowired
    private OrgTypeService orgTypeService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private PostService postService;

    @Autowired
    private OrgPostRelService orgPostRelService;

    @Autowired
    private OrgOrgtreeRelService orgOrgtreeRelService;

    @Autowired
    private OrgTreeService orgTreeService;

    @Autowired
    private OgtOrgReltypeConfService ogtOrgReftypeConfService;

    @Autowired
    private OrgLevelService orgLevelService;

    @Autowired
    private OrgPersonRelService orgPersonRelService;

    @Autowired
    private OgtOrgtypeConfService ogtOrgtypeConfService;

    @Autowired
    private OrgContactRelService orgContactRelService;

    @Autowired
    private SolrService solrService;

    @Autowired
    private OrgCertRelService orgCertRelService;


    @ApiOperation(value = "新增组织信息-web", notes = "新增组织信息")
    @UooLog(value = "新增组织信息", key = "addOrg")
    @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
    public ResponseResult<TreeNodeVo> addOrg(@RequestBody OrgVo org){
        ResponseResult<TreeNodeVo> ret = new ResponseResult<TreeNodeVo>();
        
        String msg = orgService.JudgeOrgParams(org);
        if(!StrUtil.isNullOrEmpty(msg)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage(msg);
            return ret;
        };
        Wrapper orgTreeConfWrapper = Condition.create().eq("ORG_ID",org.getOrgRootId()).eq("STATUS_CD","1000");
        OrgTree orgTree  = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgTree == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }
        List<OrgType> orgTypeList = org.getOrgTypeList();
        List<Position> positionList = org.getPositionList();
        List<Post> postList = org.getPostList();

        Wrapper ogtOrgReftypeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                .eq("STATUS_CD","1000");
        List<OgtOrgReltypeConf> ogtOrgReftypeConfList =  ogtOrgReftypeConfService.selectList(ogtOrgReftypeConfWrapper);
        if(ogtOrgReftypeConfList == null || ogtOrgReftypeConfList.size()==0){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树组织关系类型不存在");
            return ret;
        }
        //组织组织树分类
        Wrapper ogtOrgtypeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                .eq("STATUS_CD","1000");
        List<OgtOrgtypeConf> ogtOrgTypeList = ogtOrgtypeConfService.selectList(ogtOrgtypeConfWrapper);
        if(ogtOrgTypeList==null || ogtOrgTypeList.size()==0){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树组织类别不存在");
            return ret;
        }

        Org supOrg = orgService.selectById(org.getSupOrgId());
        String fullName = "";
        if(!StrUtil.isNullOrEmpty(supOrg.getFullName())){
            fullName = supOrg.getFullName()+"/"+org.getOrgName();
        }else{
            fullName = org.getOrgName();
        }
        Org newOrg = new Org();
        String orgCode = orgService.getGenerateOrgCode();
        Long orgId = orgService.getId();
        newOrg.setOrgId(orgId);
        if(org.getLocId()!=null){
            newOrg.setLocId(org.getLocId());
        }
        if(org.getAreaCodeId()!=null){
            newOrg.setAreaCodeId(new Long(org.getAreaCodeId()));
        }
        newOrg.setOrgName(StrUtil.strnull(org.getOrgName()));
        newOrg.setOrgCode(orgCode);
        newOrg.setShortName(StrUtil.strnull(org.getShortName()));
        newOrg.setOrgNameEn(StrUtil.strnull(org.getOrgNameEn()));
        newOrg.setFullName(fullName);
        newOrg.setCityTown(StrUtil.strnull(org.getCityTown()));
        newOrg.setOfficePhone(StrUtil.strnull(org.getOfficePhone()));
        //newOrg.setFoundingTime();
        newOrg.setOrgScale(StrUtil.strnull(org.getOrgScale()));
        newOrg.setOrgLevel(StrUtil.strnull(org.getOrgLevel()));
        newOrg.setOrgPositionLevel(StrUtil.strnull(org.getOrgPositionLevel()));
        if(!StrUtil.isNullOrEmpty(org.getSort())){
            newOrg.setSort(Double.valueOf(org.getSort()));
        }
        newOrg.setOrgContent(StrUtil.strnull(org.getOrgContent()));
        newOrg.setOrgDesc(StrUtil.strnull(org.getOrgDesc()));
        newOrg.setAddress(StrUtil.strnull(org.getAddress()));
        newOrg.setUuid(StrUtil.getUUID());
        newOrg.setStatusCd("1000");
        //新增组织类别
        if(orgTypeList!=null){
            for(OrgType orgType : orgTypeList) {
                Long orgTypeRefId = orgTypeRefService.getId();
                OrgOrgtypeRel orgTypeRef = new OrgOrgtypeRel();
                orgTypeRef.setOrgTypeRelId(orgTypeRefId);
                orgTypeRef.setOrgId(newOrg.getOrgId());
                orgTypeRef.setOrgTypeId(orgType.getOrgTypeId());
                orgTypeRef.setStatusCd("1000");
                orgTypeRefService.add(orgTypeRef);
                //orgTypeRef.insert();
            }
        }

        //新增组织职位
        if(postList!=null){
            for(Post post : postList){
                Long postId = orgPostRelService.getId();
                OrgPostRel orgPostRel = new OrgPostRel();
                orgPostRel.setOrgPostId(postId);
                orgPostRel.setOrgId(newOrg.getOrgId());
                orgPostRel.setPostId(post.getPostId());
                orgPostRel.setStatusCd("1000");
                orgPostRelService.add(orgPostRel);
                //orgPostRel.insert();
            }
        }
       orgService.add(newOrg);

        //org_ref 组织类别推导
        for(OgtOrgReltypeConf orgOrgRel : ogtOrgReftypeConfList){

            //新增组织岗位
            if(positionList!=null){
                for(Position position : positionList){
                    Long orgPositionId = orgPositionRelService.getId();
                    OrgPositionRel orgPosition = new OrgPositionRel();
                    orgPosition.setOrgPositionId(orgPositionId);
                    orgPosition.setOrgId(newOrg.getOrgId());
                    orgPosition.setOrgTreeId(orgOrgRel.getOrgTreeId());
                    orgPosition.setPositionId(position.getPositionId());
                    orgPosition.setStatusCd("1000");
                    orgPositionRelService.add(orgPosition);
                    //orgPosition.insert();
                }
            }



            OrgRel orgRef = new OrgRel();
            Long orgRefId = orgRelService.getId();
            orgRef.setOrgRelId(orgRefId);
            orgRef.setOrgId(newOrg.getOrgId());
            orgRef.setSupOrgId(org.getSupOrgId());
            orgRef.setOrgRelTypeId(orgOrgRel.getOrgRelTypeId());
            orgRef.setStatusCd("1000");
            orgRelService.add(orgRef);
            //orgRef.insert();


            //组织组织树关系
            Long orgOrgtreeRefId = orgOrgtreeRelService.getId();
            OrgOrgtreeRel orgOrgtreeRef = new OrgOrgtreeRel();
            orgOrgtreeRef.setOrgOrgtreeId(orgOrgtreeRefId);
            orgOrgtreeRef.setOrgId(newOrg.getOrgId());
            orgOrgtreeRef.setOrgTreeId(orgOrgRel.getOrgTreeId());
            orgOrgtreeRef.setOrgBizName(newOrg.getOrgName());
            orgOrgtreeRef.setStatusCd("1000");
            //orgOrgtreeRef.insert();
            orgOrgtreeRelService.add(orgOrgtreeRef);


            //组织层级
            Wrapper orgLevelWrapper = Condition.create().eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                    .eq("STATUS_CD","1000")
                    .eq("ORG_ID",org.getSupOrgId());
            List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
            if(orgLevelList != null && orgLevelList.size() > 0){
                OrgLevel orgL = orgLevelList.get(0);
                int lv = orgL.getOrgLevel()+1;
                Long  orgLevelId = orgLevelService.getId();
                OrgLevel orgLevel = new OrgLevel();
                orgLevel.setOrgLevelId(orgLevelId);
                orgLevel.setOrgId(newOrg.getOrgId());
                orgLevel.setOrgLevel(lv);
                orgLevel.setOrgTreeId(orgOrgRel.getOrgTreeId());
                orgLevel.setStatusCd("1000");
                //orgLevelService.insert(orgLevel);
                //orgLevel.insert();
                orgLevelService.add(orgLevel);
            }


            //solr
//            SolrInputDocument input = new SolrInputDocument();
//            input.addField("id", orgRefId);
//            input.addField("orgId", org.getOrgId());
//            input.addField("orgCode", org.getOrgCode());
//            input.addField("orgRelTypeId", orgOrgRel.getOrgRelTypeId());
//            input.addField("orgName", org.getOrgName());
//            //获取系统路径
//            String sysfullName = orgService.getSysFullName(org.getOrgRootId().toString(),org.getSupOrgId().toString());
//            sysfullName = sysfullName+"/"+org.getOrgName();
//            input.addField("fullName",sysfullName);
//            solrService.addDataIntoSolr("org",input);
        }

//        //新增组织证件
//        Wrapper orgCertWrapper = Condition.create()
//                .eq("ORG_ID",newOrg.getOrgId())
//                .eq("STATUS_CD","1000");
//        List<OrgCertRel> orgCertRelcurList = orgCertRelService.selectList(orgCertWrapper);
        List<String> cerList = org.getCertIdList();
        if(cerList != null){
            for(String certId : cerList){
                OrgCertRel orgCertRel = new OrgCertRel();
                Long orgCertRelId = orgCertRelService.getId();
                orgCertRel.setOrgCertId(orgCertRelId);
                orgCertRel.setOrgId(newOrg.getOrgId());
                orgCertRel.setCertId(Integer.valueOf(certId));
                orgCertRel.setStatusCd("1000");
                orgCertRelService.add(orgCertRel);
                //orgCertRel.insert();
            }
        }
        TreeNodeVo vo = new TreeNodeVo();
        vo.setId(orgId.toString());
        vo.setPid(org.getSupOrgId().toString());
        vo.setName(org.getOrgName());
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("新增成功");
        ret.setData(vo);
        return ret;
    }

    @ApiOperation(value = "更新组织信息-web", notes = "更新组织")
    @ApiImplicitParams({
    })
    @UooLog(value = "更新组织信息", key = "updateOrg")
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    public ResponseResult<Void> updateOrg(@RequestBody OrgVo org){
        ResponseResult<Void> ret = new ResponseResult<Void>();
        String msg = orgService.JudgeOrgParams(org);
        if(!StrUtil.isNullOrEmpty(msg)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage(msg);
            return ret;
        };
        if (StrUtil.isNullOrEmpty(org.getOrgId())) {
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        Wrapper orgTreeConfWrapper = Condition.create().eq("ORG_ID",org.getOrgRootId()).eq("STATUS_CD","1000");
        OrgTree orgTree  = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgTree == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }
        List<OrgType> orgTypeList = org.getOrgTypeList();
        List<Position> positionList = org.getPositionList();
        List<Post> postList = org.getPostList();

        Wrapper orgWrapper = Condition.create().eq("ORG_ID",org.getOrgId())
                .eq("STATUS_CD","1000");
        Org o = orgService.selectOne(orgWrapper);
        if(o == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织不存在");
            return ret;
        }
        if(!"1000".equals(org.getStatusCd())){
            Wrapper orgPer = Condition.create().eq("ORG_ID",org.getOrgId()).eq("STATUS_CD","1000");
            int num = orgPersonRelService.selectCount(orgPer);
            if(num>0){
                ret.setState(ResponseResult.STATE_ERROR);
                ret.setMessage("组织下存在员工无法删除");
                return ret;
            }
        }
        Org newOrg = new Org();
        newOrg.setOrgId(org.getOrgId());
        if(org.getLocId()!=null){
            newOrg.setLocId(org.getLocId());
        }
        if(org.getAreaCodeId()!=null){
            newOrg.setAreaCodeId(new Long(org.getAreaCodeId()));
        }
        newOrg.setOrgName(StrUtil.strnull(org.getOrgName()));
        newOrg.setOrgCode(StrUtil.strnull(org.getOrgCode()));
        newOrg.setShortName(StrUtil.strnull(org.getShortName()));
        newOrg.setOrgNameEn(StrUtil.strnull(org.getOrgNameEn()));
        newOrg.setFullName(StrUtil.strnull(org.getFullName()));
        newOrg.setCityTown(StrUtil.strnull(org.getCityTown()));
        newOrg.setOfficePhone(StrUtil.strnull(org.getOfficePhone()));
        //newOrg.setFoundingTime();
        newOrg.setOrgScale(StrUtil.strnull(org.getOrgScale()));
        newOrg.setOrgLevel(StrUtil.strnull(org.getOrgLevel()));
        newOrg.setOrgPositionLevel(StrUtil.strnull(org.getOrgPositionLevel()));
        if(!StrUtil.isNullOrEmpty(org.getSort())){
            newOrg.setSort(Double.valueOf(org.getSort()));
        }
        newOrg.setOrgContent(StrUtil.strnull(org.getOrgContent()));
        newOrg.setOrgDesc(StrUtil.strnull(org.getOrgDesc()));
        newOrg.setAddress(StrUtil.strnull(org.getAddress()));
        newOrg.setUuid(StrUtil.getUUID());

        //newOrg.updateById();

        Wrapper orgTypeWrapper = Condition.create().eq("ORG_ID",org.getOrgId()).eq("STATUS_CD","1000");
        List<OrgOrgtypeRel> orgTypeRefCurList = orgTypeRefService.selectList(orgTypeWrapper);

        Wrapper positionWrapper = Condition.create().eq("ORG_ID",org.getOrgId()).eq("STATUS_CD","1000");
        List<OrgPositionRel> orgPositionCurList = orgPositionRelService.selectList(positionWrapper);

        Wrapper postWrapper = Condition.create().eq("ORG_ID",org.getOrgId()).eq("STATUS_CD","1000");
        List<OrgPostRel> orgPostCurList = orgPostRelService.selectList(postWrapper);


        boolean isExists = false;
        //类别
        if(orgTypeList!=null && orgTypeList.size()>0){
            for(OrgType ot : orgTypeList){
                for(OrgOrgtypeRel otf : orgTypeRefCurList){
                    if(ot.getOrgTypeId().longValue() == otf.getOrgTypeId().longValue()){
                        isExists = true;
                        break;
                    }else{
                        isExists = false;
                    }
                }
                if(!isExists){
                    OrgOrgtypeRel orgTypeRef = new OrgOrgtypeRel();
                    Long orgTypeRelId = orgTypeRefService.getId();
                    orgTypeRef.setOrgTypeRelId(orgTypeRelId);
                    orgTypeRef.setOrgId(org.getOrgId());
                    orgTypeRef.setOrgTypeId(ot.getOrgTypeId());
                    orgTypeRef.setStatusCd("1000");
                    orgTypeRefService.add(orgTypeRef);
                    //orgTypeRef.insert();
                }
            }

            for(OrgOrgtypeRel otf : orgTypeRefCurList){
                for(OrgType ot : orgTypeList){
                    isExists = false;
                    if(ot.getOrgTypeId().longValue() == otf.getOrgTypeId().longValue()){
                        isExists = true;
                        break;
                    }else{
                        isExists = false;
                    }
                }
                if(!isExists){
                    orgTypeRefService.delete(otf);
                }
            }
        }
        //岗位
        if(positionList!=null && positionList.size()>0){
            for(Position p : positionList){
                for(OrgPositionRel op : orgPositionCurList){
                    isExists = false;
                    if(p.getPositionId().longValue() == op.getPositionId().longValue()){
                        isExists = true;
                        break;
                    }
                }
                if(!isExists){
                    Long orgPositionId = orgPositionRelService.getId();
                    OrgPositionRel orgPosition = new OrgPositionRel();
                    orgPosition.setOrgPositionId(orgPositionId);
                    orgPosition.setOrgId(org.getOrgId());
                    orgPosition.setOrgTreeId(orgTree.getOrgTreeId());
                    orgPosition.setPositionId(p.getPositionId());
                    orgPosition.setStatusCd("1000");
                    orgPositionRelService.add(orgPosition);
                    //orgPosition.insert();
                }
            }


            for(OrgPositionRel op:orgPositionCurList){
                for(Position p : positionList){
                    isExists = false;
                    if(p.getPositionId().longValue() == op.getOrgPositionId().longValue()){
                        isExists = true;
                        break;
                    }
                }
                if(!isExists){
                    orgPositionRelService.delete(op);
                }
            }
        }
        //职位 post
        if(postList!=null && postList.size()>0){
            for(Post p : postList){
                for(OrgPostRel op : orgPostCurList){
                    isExists = false;
                    if(p.getPostId().longValue() == op.getPostId().longValue()){
                        isExists = true;
                        break;
                    }
                }
                if(!isExists){
                    Long orgPostId = orgPostRelService.getId();
                    OrgPostRel orgPost = new OrgPostRel();
                    orgPost.setOrgId(org.getOrgId());
                    orgPost.setPostId(p.getPostId());
                    orgPost.setOrgPostId(orgPostId);
                    orgPost.setStatusCd("1000");
                    orgPostRelService.add(orgPost);
                    //orgPost.insert();
                }
            }
            for(OrgPostRel op : orgPostCurList){
                for(Post p : postList){
                    isExists = false;
                    if(p.getPostId().longValue() == op.getPostId().longValue()){
                        isExists = true;
                        break;
                    }
                }
                if(!isExists){
                    orgPostRelService.delete(op);
                }
            }
        }
        //更新组织证件
        Wrapper orgCertWrapper = Condition.create()
                .eq("ORG_ID",org.getOrgId())
                .eq("STATUS_CD","1000");
        List<OrgCertRel> orgCertRelcurList = orgCertRelService.selectList(orgCertWrapper);
        //List<String> cerList = org.getCertIdList();
        List<OrgCertVo> cerList  = org.getOrgCertList();
        if(cerList!=null && cerList.size()>0){
            for(OrgCertVo certVo : cerList){
                for(OrgCertRel ocr : orgCertRelcurList){
                    isExists = false;
                    if(ocr.getCertId().longValue() == certVo.getCertId()){
                        isExists = true;
                        break;
                    }
                }
                if(!isExists){
                    OrgCertRel orgCertRel = new OrgCertRel();
                    Long orgCertRelId = orgCertRelService.getId();
                    orgCertRel.setOrgCertId(orgCertRelId);
                    orgCertRel.setOrgId(org.getOrgId());
                    orgCertRel.setCertId(certVo.getCertId().intValue());
                    orgCertRelService.add(orgCertRel);
                    //orgCertRel.insert();
                }
            }
            for(OrgCertRel ocr : orgCertRelcurList){
                for(OrgCertVo orgCertVo : cerList){
                    isExists = false;
                    if(ocr.getCertId().longValue() == orgCertVo.getCertId()){
                        isExists = true;
                        break;
                    }
                }
                if(!isExists){
                    orgCertRelService.delete(ocr);
                }
            }
        }
        if (!"1000".equals(org.getStatusCd())){
            //删除组织关系
            List<OrgRel> orList = orgRelService.getOrgRel(orgTree.getOrgTreeId().toString(),org.getOrgId().toString());
            for(OrgRel or : orList){

                Wrapper orgTreeRelWrapper = Condition.create().eq("ORG_ID",org.getOrgId())
                        .eq("STATUS_CD","1000")
                        .eq("ORG_TREE_ID",orgTree.getOrgTreeId());
                List<OrgOrgtreeRel> orgOrgtreeRelList = orgOrgtreeRelService.selectList(orgTreeRelWrapper);
                for(OrgOrgtreeRel ootr : orgOrgtreeRelList){
                    orgOrgtreeRelService.delete(ootr);
                }

                Wrapper orgLevelWrapper = Condition.create()
                        .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                        .eq("STATUS_CD","1000")
                        .eq("ORG_ID",org.getOrgId());
                List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
                for(OrgLevel ol : orgLevelList){
                    orgLevelService.delete(ol);
                }

                Wrapper orgPositionWrapper = Condition.create()
                        .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                        .eq("STATUS_CD","1000")
                        .eq("ORG_ID",org.getOrgId());

                List<OrgPositionRel> orgPositionRelList = orgPositionRelService.selectList(orgPositionWrapper);
                for(OrgPositionRel opr : orgPositionRelList){
                    orgPositionRelService.delete(opr);
                }

                //删除证件组织关系
                Wrapper orgCertListWrapper = Condition.create()
                        .eq("STATUS_CD","1000")
                        .eq("ORG_ID",org.getOrgId());
                List<OrgCertRel> orgCertRelList = orgCertRelService.selectList(orgCertListWrapper);
                for(OrgCertRel vo:orgCertRelList){
                    orgCertRelService.delete(vo);
                }
                //删除组织联系人关系
                Wrapper orgContactListWrapper = Condition.create()
                        .eq("STATUS_CD","1000")
                        .eq("ORG_ID",org.getOrgId());
                List<OrgContactRel> orgContactRelList = orgContactRelService.selectList(orgContactListWrapper);
                for(OrgContactRel vo:orgContactRelList){
                    orgContactRelService.delete(vo);
                }
                orgRelService.delete(or);
                //solrService.deleteDataIntoSolr("org",or.getOrgRelId().toString());
            }

        }
        newOrg.setStatusCd("1000");
        newOrg.updateById();
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("更新成功");
        return ret;
    }





    @ApiOperation(value = "查询组织信息-web", notes = "查询组织信息")
    @ApiImplicitParams({

    })
    @UooLog(value = "查询组织信息", key = "getOrg")
    @RequestMapping(value = "/getOrg", method = RequestMethod.GET)
    public ResponseResult<OrgVo> getOrg(String orgId){
        ResponseResult<OrgVo> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        Wrapper orgWrapper = Condition.create().eq("ORG_ID",orgId).eq("STATUS_CD","1000");
        OrgVo org = orgService.selectOrgByOrgId(orgId);
        //Org org = orgService.selectById(orgId);
        if(StrUtil.isNullOrEmpty(org)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织不存在");
            return ret;
        }

        List<OrgType> orgTypeList = orgTypeService.getOrgTypeByOrgId(Integer.valueOf(orgId).longValue());
        org.setOrgTypeList(orgTypeList);
        //组织岗位
        List<Position> positionList = positionService.getOrgPositionByOrgId(Integer.valueOf(orgId).longValue());
        org.setPositionList(positionList);
        //组织职位
        List<Post> postList = postService.getOrgPostByOrgId(Integer.valueOf(orgId).longValue());
        org.setPostList(postList);
        //组织联系人
        List<PsonOrgVo> psonOrgVoList = orgContactRelService.getOrgContact(orgId);
        if(psonOrgVoList!=null && psonOrgVoList.size()>0){
            org.setPsonOrgVoList(psonOrgVoList);
        }
        //组织证件类型
        List<OrgCertVo> orgCertList = orgCertRelService.getOrgCerRelByOrgId(new Long(orgId));
        org.setOrgCertList(orgCertList);

        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        ret.setData(org);
        return ret;
    }




    @ApiOperation(value = "查询组织关系列表分页-web", notes = "查询组织关系列表分页")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织关系列表分页", key = "getOrgRelPage")
    @RequestMapping(value = "/getOrgRelPage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<OrgVo>> getOrgRelPage(Integer orgRootId,
                                                     Integer orgId,
                                                     Integer pageSize,
                                                     Integer pageNo){
        ResponseResult<Page<OrgVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgRootId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织根节点不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        OrgVo orgVo = new OrgVo();
        orgVo.setOrgRootId(orgRootId.toString());
        orgVo.setOrgId(orgId.longValue());
        if(!StrUtil.isNullOrEmpty(pageNo)){
            orgVo.setPageNo(pageNo);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            orgVo.setPageSize(pageSize);
        }
        Page<OrgVo> page = orgService.selectOrgRelPage(orgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        ret.setData(page);
        return ret;
    }


    @ApiOperation(value = "查询组织分页-web", notes = "查询组织分页")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织分页", key = "getOrgPage")
    @RequestMapping(value = "/getOrgPage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<OrgVo>> getOrgPage(String search,
                                                  Integer pageSize,
                                                  Integer pageNo){
        OrgVo orgVo = new OrgVo();
        ResponseResult<Page<OrgVo>> ret = new ResponseResult<>();
        orgVo.setStatusCd("1000");
        if(!StrUtil.isNullOrEmpty(search)){
            orgVo.setSearch(search);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            orgVo.setPageSize(pageSize);
        }
        if(!StrUtil.isNullOrEmpty(pageNo)){
            orgVo.setPageNo(pageNo);
        }
        Page<OrgVo> page = orgService.selectOrgPage(orgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        ret.setData(page);
        return ret;
    }


    @ApiOperation(value = "查询组织额外信息", notes = "查询组织额外信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织额外信息", key = "getOrgExtByOrgId")
    @RequestMapping(value = "/getOrgExtByOrgId", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<HashMap<String,String>> getOrgExtByOrgId(String orgRootId, String orgId){
        ResponseResult<HashMap<String,String>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgRootId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树根节点标识为空");
            return ret;
        }
        HashMap<String,String> listMap = new HashMap<String,String>();
       // String fullName = orgService.getSysFullName(orgRootId,orgId);
        String followOrg = orgTreeService.getOrgTreeNameByOrgId(orgId);
       // listMap.put("FULL_NAME",fullName);
        listMap.put("FOLLOW_ORG",followOrg);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        ret.setData(listMap);
        return ret;
    }

}

