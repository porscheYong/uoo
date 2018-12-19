package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.Api.service.ExpandovalueService;
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
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import sun.swing.StringUIClientPropertyKey;

import javax.annotation.Resource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AmqpTemplate template;


    @Autowired
    private ExpandovalueService expandovalueService;


    @ApiOperation(value = "新增组织信息-web", notes = "新增组织信息")
    @UooLog(value = "新增组织信息", key = "addOrg")
    @RequestMapping(value = "/addOrg", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<TreeNodeVo> addOrg(@RequestBody OrgVo org){
        ResponseResult<TreeNodeVo> ret = new ResponseResult<TreeNodeVo>();
        
        String msg = orgService.JudgeOrgParams(org);
        if(!StrUtil.isNullOrEmpty(msg)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage(msg);
            return ret;
        };
        Wrapper orgTreeConfWrapper = Condition.create().eq("ORG_TREE_ID",org.getOrgTreeId()).eq("STATUS_CD","1000");
        OrgTree orgTree  = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgTree == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }
        List<OrgType> orgTypeList = org.getOrgTypeList();
        List<Position> positionList = org.getPositionList();
        List<Post> postList = org.getPostList();
        //组织联系人
        List<PsonOrgVo> psonorgList = org.getPsonOrgVoList();
        //获取组织扩展属性
        List<ExpandovalueVo> extValueList = org.getExpandovalueVoList();

        List<PoliticalLocation> politicalLocationList = org.getPoliticalLocationList();
        if(politicalLocationList==null || politicalLocationList.size()==0){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("行政管理区域不存在");
            return ret;
        }
        PoliticalLocation pl = politicalLocationList.get(0);




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

        if(StrUtil.isNullOrEmpty(org.getSupOrgId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("父节点不存在");
            return ret;
        }
        Org supOrg = orgService.selectById(org.getSupOrgId());
        if(supOrg==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("父节点为空");
            return ret;
        }




        String fullName = "";
        if(!StrUtil.isNullOrEmpty(supOrg.getFullName())){
            fullName = supOrg.getFullName()+org.getOrgName();
        }else{
            fullName = org.getOrgName();
        }
        Org newOrg = new Org();
        String orgCode = orgService.getGenerateOrgCode();
        Long orgId = orgService.getId();
        newOrg.setOrgId(orgId);

        if(!StrUtil.isNullOrEmpty(pl.getLocId())){
            newOrg.setLocId(pl.getLocId());
        }
        if(!StrUtil.isNullOrEmpty(org.getAreaCodeId())){
            newOrg.setAreaCodeId(new Long(org.getAreaCodeId()));
        }
        newOrg.setOrgName(StrUtil.strnull(org.getOrgName()));
        newOrg.setOrgCode(orgCode);
        newOrg.setShortName(StrUtil.strnull(org.getShortName()));
        newOrg.setOrgNameEn(StrUtil.strnull(org.getOrgNameEn()));
        newOrg.setFullName(fullName);
        newOrg.setCityTown(StrUtil.strnull(org.getCityTown()));
        newOrg.setOfficePhone(StrUtil.strnull(org.getOfficePhone()));
        if(!StrUtil.isNullOrEmpty(org.getFoundingTime())){
            newOrg.setFoundingTime(DateUtils.getDatebystr(org.getFoundingTime(),"yyyy-MM-dd"));
        }
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
            }
        }


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
                }
            }


            Wrapper orgReltypeConfWrapper = Condition.create()
                    .eq("ORG_REL_TYPE_ID",orgOrgRel.getOrgRelTypeId())
                    .eq("STATUS_CD","1000");
            OrgRelType ort = orgRelTypeService.selectOne(orgReltypeConfWrapper);



            OrgRel orgRef = new OrgRel();
            Long orgRefId = orgRelService.getId();
            orgRef.setOrgRelId(orgRefId);
            orgRef.setOrgId(newOrg.getOrgId());
            orgRef.setParentOrgId(org.getSupOrgId());
            orgRef.setRefCode(ort.getRefCode());
            orgRef.setStatusCd("1000");
            orgRelService.add(orgRef);


            //组织组织树关系
            Long orgOrgtreeRefId = orgOrgtreeRelService.getId();
            OrgOrgtreeRel orgOrgtreeRef = new OrgOrgtreeRel();
            orgOrgtreeRef.setOrgOrgtreeId(orgOrgtreeRefId);
            orgOrgtreeRef.setOrgId(newOrg.getOrgId());
            orgOrgtreeRef.setOrgTreeId(orgOrgRel.getOrgTreeId());
            orgOrgtreeRef.setOrgBizName(StrUtil.isNullOrEmpty(org.getOrgBizName())?org.getOrgName():StrUtil.strnull(org.getOrgBizName()));
            orgOrgtreeRef.setStatusCd("1000");
            orgOrgtreeRelService.add(orgOrgtreeRef);



            //组织层级
            Wrapper orgLevelWrapper = Condition.create()
                    .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
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

        //新增组织证件
//        Wrapper orgCertWrapper = Condition.create()
//                .eq("ORG_ID",newOrg.getOrgId())
//                .eq("STATUS_CD","1000");
//        List<OrgCertRel> orgCertRelcurList = orgCertRelService.selectList(orgCertWrapper);
        List<String> cerList = org.getCertIdList();
//        if(orgCertRelcurList == null){
        if(cerList!=null && cerList.size()>0){
            for(String certId : cerList){
                OrgCertRel orgCertRel = new OrgCertRel();
                Long orgCertRelId = orgCertRelService.getId();
                orgCertRel.setOrgCertId(orgCertRelId);
                orgCertRel.setOrgId(newOrg.getOrgId());
                orgCertRel.setCertId(Integer.valueOf(certId));
                orgCertRelService.add(orgCertRel);
                //orgCertRel.insert();
            }
        }
        //新增组织联系人
        if(psonorgList!=null && psonorgList.size()>0){
            for(PsonOrgVo vo : psonorgList){
                Long orgConRelId = orgContactRelService.getId();
                OrgContactRel orgConRel = new OrgContactRel();
                orgConRel.setPersonnelId(vo.getPersonnelId());
                orgConRel.setOrgId(newOrg.getOrgId());
                orgConRel.setOrgContactRelId(orgConRelId);
                orgContactRelService.add(orgConRel);
            }
        }
//        }
        //新增组织扩展属性
        boolean isExitExp = false;
        if(extValueList!=null && extValueList.size()>0){
            ResponseResult<ExpandovalueVo> publicRet = new ResponseResult<ExpandovalueVo>();
            for(ExpandovalueVo extVo : extValueList){
                extVo.setTableName("TB_ORG");
                extVo.setRecordId(newOrg.getOrgId().toString());
                publicRet = expandovalueService.addExpandoInfo(extVo);
            }
            isExitExp = true;
        }
        orgService.add(newOrg);

        if (isExitExp) {

            String orgMarkCodeRet = jdbcTemplate.execute(new ConnectionCallback<String>() {
                @Override
                public String doInConnection(Connection conn) throws SQLException, DataAccessException {
                    CallableStatement cstmt = null;
                    String result = "";
                    try {
                        cstmt = conn.prepareCall("{CALL P_ORG_CNTRT_MGMT_GENERATOR (?,?)}");
                        cstmt.setObject(1, orgCode);
                        cstmt.registerOutParameter(2, Types.VARCHAR);
                        cstmt.execute();
                        if(!StrUtil.isNullOrEmpty(cstmt.getString(2))){
                            result = cstmt.getString(2).toString();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (cstmt != null) {
                            cstmt.close();
                            cstmt = null;
                        }
                        if (conn != null) {
                            conn.close();
                            conn = null;
                        }
                    }
                    return result;
                }
            });
        }
        TreeNodeVo vo = new TreeNodeVo();
        vo.setId(newOrg.getOrgId().toString());
        vo.setPid(org.getSupOrgId().toString());
        vo.setName(newOrg.getOrgName());
        //mq
        String mqmsg = "{\"type\":\"org\",\"handle\":\"insert\",\"context\":{\"column\":\"orgId\",\"value\":"+orgId+"}}" ;
        template.convertAndSend("message_sharing_center_queue",mqmsg);
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
        Wrapper orgTreeConfWrapper = Condition.create().eq("ORG_TREE_ID",org.getOrgTreeId()).eq("STATUS_CD","1000");
        OrgTree orgTree  = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgTree == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }
        List<OrgType> orgTypeList = org.getOrgTypeList();
        List<Position> positionList = org.getPositionList();
        List<Post> postList = org.getPostList();
        List<PoliticalLocation> politicalLocationList = org.getPoliticalLocationList();
        List<PsonOrgVo> psonList = org.getPsonOrgVoList();

        List<ExpandovalueVo> expList = org.getExpandovalueVoList();


        if(politicalLocationList==null || politicalLocationList.size()==0){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("行政管理区域不存在");
            return ret;
        }
        PoliticalLocation pl = politicalLocationList.get(0);
        Wrapper orgWrapper = Condition.create()
                .eq("ORG_ID",org.getOrgId())
                .eq("STATUS_CD","1000");
        Org o = orgService.selectOne(orgWrapper);
        if(o == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织不存在");
            return ret;
        }
        List<OrgRel> orList = orgRelService.getOrgRel(orgTree.getOrgTreeId().toString(),
                org.getOrgId().toString());
        if(orList==null || orList.size()<1){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织关系不存在");
            return ret;
        }
        if(!"1000".equals(org.getStatusCd())){
            Wrapper orgPer = Condition.create()
                    .eq("ORG_ID",org.getOrgId())
                    .eq("STATUS_CD","1000");
            int num = orgPersonRelService.selectCount(orgPer);
            if(num>0){
                ret.setState(ResponseResult.STATE_ERROR);
                ret.setMessage("组织下存在员工无法删除");
                return ret;
            }
        }
        Org newOrg = new Org();
        newOrg.setOrgId(org.getOrgId());
        if(!StrUtil.isNullOrEmpty(pl.getLocId())){
            newOrg.setLocId(pl.getLocId());
        }
        if(!StrUtil.isNullOrEmpty(org.getAreaCodeId())){
            newOrg.setAreaCodeId(new Long(org.getAreaCodeId()));
        }

        List<OrgVo> orgListVo = orgRelService.getFullOrgList("1",newOrg.getOrgId().toString());
        if(orgListVo!=null && orgListVo.size()>0){
            if(orgListVo.size()==1){
                newOrg.setFullName(org.getOrgName());
            }else{
                String fullName = "";
                for(int i=0;i<orgListVo.size()-1;i++){
                    fullName += orgListVo.get(i).getOrgName();
                }
                fullName+=org.getOrgName();
                newOrg.setFullName(fullName);
            }
        }

        newOrg.setOrgName(StrUtil.strnull(org.getOrgName()));
        //newOrg.setOrgCode(StrUtil.strnull(org.getOrgCode()));
        newOrg.setShortName(StrUtil.strnull(org.getShortName()));
        newOrg.setOrgNameEn(StrUtil.strnull(org.getOrgNameEn()));
        //newOrg.setFullName(StrUtil.strnull(org.getFullName()));
        newOrg.setCityTown(StrUtil.strnull(org.getCityTown()));
        newOrg.setOfficePhone(StrUtil.strnull(org.getOfficePhone()));
        if(!StrUtil.isNullOrEmpty(org.getFoundingTime())){
            newOrg.setFoundingTime(DateUtils.getDatebystr(org.getFoundingTime(),"yyyy-MM-dd"));
        }
        newOrg.setOrgScale(StrUtil.strnull(org.getOrgScale()));
        newOrg.setOrgLevel(StrUtil.strnull(org.getOrgLevel()));
        newOrg.setOrgPositionLevel(StrUtil.strnull(org.getOrgPositionLevel()));
        if(!StrUtil.isNullOrEmpty(org.getSort())){
            newOrg.setSort(Double.valueOf(org.getSort()));
        }
        newOrg.setOrgContent(StrUtil.strnull(org.getOrgContent()));
        newOrg.setOrgDesc(StrUtil.strnull(org.getOrgDesc()));
        newOrg.setAddress(StrUtil.strnull(org.getAddress()));
        //newOrg.setUuid(StrUtil.getUUID());

        //newOrg.updateById();

        Wrapper orgTypeWrapper = Condition.create()
                .eq("ORG_ID",org.getOrgId())
                .eq("STATUS_CD","1000");
        List<OrgOrgtypeRel> orgTypeRefCurList = orgTypeRefService.selectList(orgTypeWrapper);

        Wrapper positionWrapper = Condition.create()
                .eq("ORG_ID",org.getOrgId())
                .eq("STATUS_CD","1000");
        List<OrgPositionRel> orgPositionCurList = orgPositionRelService.selectList(positionWrapper);

        Wrapper postWrapper = Condition.create()
                .eq("ORG_ID",org.getOrgId())
                .eq("STATUS_CD","1000");
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
                    orgTypeRef.setOrgId(org.getOrgId());
                    orgTypeRef.setOrgTypeId(ot.getOrgTypeId());
                    orgTypeRef.setStatusCd("1000");
                    orgTypeRef.setOrgTypeRelId(orgTypeRelId);
                    orgTypeRefService.add(orgTypeRef);
                }
            }
            isExists = false;
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
        }else{
            if(orgTypeRefCurList!=null && orgTypeRefCurList.size()>0){
                for(OrgOrgtypeRel otf : orgTypeRefCurList){
                    orgTypeRefService.delete(otf);
                }
            }
        }
        //岗位
        isExists = false;
        if(positionList!=null && positionList.size()>0){
            for(Position p : positionList){
                isExists = false;
                for(OrgPositionRel op : orgPositionCurList){
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
                }
            }

            isExists = false;
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
        }else{
            if(orgPositionCurList!=null && orgPositionCurList.size()>0){
                for(OrgPositionRel op:orgPositionCurList){
                    orgPositionRelService.delete(op);
                }
            }
        }
        //职位 post
        isExists = false;
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
                }
            }
            isExists = false;
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
        }else{
            if(orgPostCurList!=null && orgPostCurList.size()>0){
                for(OrgPostRel op : orgPostCurList){
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
        List<OrgCertVo> cerList  = org.getOrgCertVoList();
        isExists = false;
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
                }
            }
            isExists = false;
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
        }else{
            if(orgCertRelcurList!=null && orgCertRelcurList.size()>0){
                for(OrgCertRel ocr : orgCertRelcurList){
                    orgCertRelService.delete(ocr);
                }
            }
        }
        //组织联系人
        Wrapper orgContactList1Wrapper = Condition.create()
                .eq("STATUS_CD","1000")
                .eq("ORG_ID",org.getOrgId());
        List<OrgContactRel> orgContactRelCurList = orgContactRelService.selectList(orgContactList1Wrapper);
        isExists = false;
        if(psonList!=null && psonList.size()>0){
            for(PsonOrgVo vo : psonList){
                for(OrgContactRel oct : orgContactRelCurList){
                    isExists = false;
                    if((oct.getPersonnelId().toString()).equals(vo.getPersonnelId())){
                        isExists = true;
                        break;
                    }
                }
                if(!isExists){
                    Long orgConRelId = orgContactRelService.getId();
                    OrgContactRel orgConRel = new OrgContactRel();
                    orgConRel.setPersonnelId(vo.getPersonnelId());
                    orgConRel.setOrgId(newOrg.getOrgId());
                    orgConRel.setOrgContactRelId(orgConRelId);
                    orgContactRelService.add(orgConRel);
                }
            }
            isExists = false;
            for(OrgContactRel oct : orgContactRelCurList){
                for(PsonOrgVo vo : psonList){
                    isExists = false;
                    if((oct.getPersonnelId().toString()).equals(vo.getPersonnelId())){
                        isExists = true;
                        break;
                    }
                }
                if(!isExists){
                    orgContactRelService.delete(oct);
                }
            }
        }else{
            if(orgContactRelCurList!=null && orgContactRelCurList.size()>0){
                for(OrgContactRel oct : orgContactRelCurList){
                    orgContactRelService.delete(oct);
                }
            }
        }
        //组织扩展类型
        ResponseResult<List<ExpandovalueVo>> publicRet = expandovalueService.queryExpandovalueVoList("TB_ORG",org.getOrgId().toString());
        List<ExpandovalueVo> curExtList = publicRet.getData();
        isExists = false;
        if(expList!=null && expList.size()>0){
            for(ExpandovalueVo  vo : expList){
                isExists = false;
                if(curExtList!=null && curExtList.size()>0){
                    for(ExpandovalueVo curVo : curExtList){
                        if((vo.getValueId().toString()).equals(curVo.getValueId().toString())){
                            isExists=true;
                            break;
                        }
                    }
                }
                ResponseResult<ExpandovalueVo> voret;
                if(!isExists){
                    //新增
                    ExpandovalueVo voadd = new ExpandovalueVo();
                    voadd.setTableName("TB_ORG");
                    voadd.setColumnName(vo.getColumnName());
                    voadd.setRecordId(newOrg.getOrgId().toString());
                    voadd.setData(vo.getData());
                    voret = expandovalueService.addExpandoInfo(voadd);

                }else{
                    //更新
                    TbExpandovalue voupdate = new TbExpandovalue();
                    voupdate.setData(vo.getData());
                    voupdate.setValueId(vo.getValueId());
                    expandovalueService.updateTbExpandovalue(voupdate);
                }
            }
            if(curExtList!=null && curExtList.size()>0){
                for(ExpandovalueVo curVo : curExtList){
                    for(ExpandovalueVo  vo : expList){
                        if((vo.getValueId().toString()).equals(curVo.getValueId().toString())){
                            isExists=true;
                            break;
                        }
                    }
                    if(!isExists){
                        //删除
                        expandovalueService.removeTbExpandovalue(curVo.getValueId(),0L);
                    }
                }
            }
        }else{
            if(curExtList!=null && curExtList.size()>0){
                for(ExpandovalueVo vo : curExtList){
                    //删除所有
                    expandovalueService.removeTbExpandovalue(vo.getValueId(),0L);
                }
            }
        }


        //更新组织组织树关系
        Wrapper orgTreeRelOneWrapper = Condition.create()
                .eq("ORG_ID",org.getOrgId())
                .eq("STATUS_CD","1000")
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId());
        OrgOrgtreeRel orgOrgtreeRelOne = orgOrgtreeRelService.selectOne(orgTreeRelOneWrapper);
        if(orgOrgtreeRelOne!=null){
            if(!StrUtil.isNullOrEmpty(org.getOrgBizName())) {
                orgOrgtreeRelOne.setOrgBizName(org.getOrgBizName());
                orgOrgtreeRelService.update(orgOrgtreeRelOne);
            }
        }


        if (!"1000".equals(org.getStatusCd())){
            //删除扩展类型
            if(curExtList!=null && curExtList.size()>0){
                for(ExpandovalueVo vo : curExtList){
                    //删除所有
                    expandovalueService.removeTbExpandovalue(vo.getValueId(),0L);
                }
            }
            //删除组织关系
            for(OrgRel or : orList){

                Wrapper orgTreeRelWrapper = Condition.create()
                        .eq("ORG_ID",org.getOrgId())
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
                newOrg.setStatusCd("1000");
                orgService.update(newOrg);
                String mqmsg = "{\"type\":\"org\",\"handle\":\"update\",\"context\":{\"column\":\"orgId\",\"value\":"+newOrg.getOrgId()+"}}" ;
                template.convertAndSend("message_sharing_center_queue",mqmsg);
                ret.setState(ResponseResult.STATE_OK);
                ret.setMessage("更新成功");
                return ret;
            }
        }
        newOrg.setStatusCd("1000");
        orgService.update(newOrg);
        String mqmsg = "{\"type\":\"org\",\"handle\":\"update\",\"context\":{\"column\":\"orgId\",\"value\":"+newOrg.getOrgId()+"}}" ;
        template.convertAndSend("message_sharing_center_queue",mqmsg);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("更新成功");
        return ret;
    }



    @ApiOperation(value = "组织删除", notes = "组织删除")
    @ApiImplicitParams({

    })
    @UooLog(value = "组织删除", key = "deleteOrg")
    @RequestMapping(value = "/deleteOrg", method = RequestMethod.GET)
    public ResponseResult<String> deleteOrg(String orgTreeId,
                                            String orgId,
                                            String supOrgId){
        ResponseResult<String> ret = new ResponseResult<String>();
        if (StrUtil.isNullOrEmpty(orgTreeId)) {
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识不能为空");
            return ret;
        }
        if (StrUtil.isNullOrEmpty(orgId)) {
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if (StrUtil.isNullOrEmpty(supOrgId)) {
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织父节点不能为空");
            return ret;
        }

        Wrapper orgTreeConfWrapper = Condition.create().eq("ORG_TREE_ID",orgTreeId).eq("STATUS_CD","1000");
        OrgTree orgTree  = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgTree == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }
        //Wrapper leafOrgConfWrapper = Condition.create().eq("PARENT_ORG_ID",orgId).eq("STATUS_CD","1000");
        if(orgRelService.isLeaf(orgId,orgTreeId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织下存在组织无法删除");
            return ret;
        }
//        Wrapper orgPer = Condition.create()
//                .eq("ORG_ID",orgId)
//                .eq("STATUS_CD","1000");
//        int num = orgPersonRelService.selectCount(orgPer);
        List<OrgPersonRel> oplist = orgPersonRelService.getOrgPsnRel(orgTree.getOrgTreeId().toString(),orgId);
        if(oplist!=null && oplist.size()>0){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织下存在员工无法删除");
            return ret;
        }
//        List<OrgRel> orgRelList = orgRelService.getOrgRel(orgTreeId,orgId);
//        if(orgRelList!=null && orgRelList.size()>1){
//            ret.setState(ResponseResult.STATE_ERROR);
//            ret.setMessage("组织被其他组织树引用无法删除");
//            return ret;
//        }
        Wrapper orgWrapper = Condition.create()
                .eq("ORG_ID",orgId)
                .eq("STATUS_CD","1000");
        Org org = orgService.selectOne(orgWrapper);
        List<OrgRel> orgRelList = orgRelService.getOrgRel(orgTreeId,orgId);
        for(OrgRel orgRel : orgRelList){
            orgRelService.delete(orgRel);
            Wrapper orgTreeRelWrapper = Condition.create()
                    .eq("ORG_ID",org.getOrgId())
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


        ResponseResult<List<ExpandovalueVo>> publicRet = expandovalueService.queryExpandovalueVoList("TB_ORG",org.getOrgId().toString());
        List<ExpandovalueVo> curExtList = publicRet.getData();
        if(curExtList!=null && curExtList.size()>0){
            for(ExpandovalueVo vo : curExtList){
                //删除所有
                expandovalueService.removeTbExpandovalue(vo.getValueId(),0L);
            }
        }

        String mqmsg = "{\"type\":\"org\",\"handle\":\"update\",\"context\":{\"column\":\"orgId\",\"value\":"+orgId+"}}" ;
        template.convertAndSend("message_sharing_center_queue",mqmsg);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("删除成功");
        return ret;
    }




    @ApiOperation(value = "查询组织信息-web", notes = "查询组织信息")
    @ApiImplicitParams({

    })
    @UooLog(value = "查询组织信息", key = "getOrg")
    @RequestMapping(value = "/getOrg", method = RequestMethod.GET)
    public ResponseResult<OrgVo> getOrg(String orgTreeId,String orgId){
        ResponseResult<OrgVo> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgTreeId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识不能为空");
            return ret;
        }
        //查看组织树是否存在
        Wrapper orgTreeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgTreeId)
                .eq("STATUS_CD","1000");
        OrgTree orgTree  = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgTree == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }

//        Wrapper orgWrapper = Condition.create()
//                .eq("ORG_ID",orgId)
//                .eq("STATUS_CD","1000");
        OrgVo org = orgService.selectOrgByOrgId(orgId,orgTree.getOrgTreeId().toString());
        //Org org = orgService.selectById(orgId);
        if(StrUtil.isNullOrEmpty(org)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织不存在");
            return ret;
        }

        List<OrgType> orgTypeList = orgTypeService.getOrgTypeByOrgId(new Long(orgId));
        org.setOrgTypeList(orgTypeList);
        //组织岗位
        List<Position> positionList = positionService.getOrgPositionByOrgId(new Long(orgId));
        org.setPositionList(positionList);
        //组织职位
        List<Post> postList = postService.getOrgPostByOrgId(new Long(orgId));
        org.setPostList(postList);
        //组织联系人
        List<PsonOrgVo> psonOrgVoList = orgContactRelService.getOrgContact(orgId);
        if(psonOrgVoList!=null && psonOrgVoList.size()>0){
            org.setPsonOrgVoList(psonOrgVoList);
        }
        //组织证件类型
        List<OrgCertVo> orgCertList = orgCertRelService.getOrgCerRelByOrgId(new Long(orgId));
        org.setOrgCertVoList(orgCertList);

        //行政管理区域 2
        List<PoliticalLocation> pl = orgService.getOrgLoc(orgId);
        org.setPoliticalLocationList(pl);

        //区域编码
        List<AreaCodeVo> areaList = orgService.getOrgAreaCode(orgId);
        if(areaList!=null && areaList.size()>0){
            org.setAreaCode(StrUtil.strnull(areaList.get(0).getAreaCode()));
        }

        //营销化小编码
        ResponseResult<List<ExpandovalueVo>> Hxret = expandovalueService.queryExpandovalueVoList("TB_ORG",orgId);
        if(Hxret.getState()==ResponseResult.STATE_OK){
            org.setExpandovalueVoList(Hxret.getData());
        }else{
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("调用[business-public]接口[/tbExpandovalue/getValueVoList]异常:"+Hxret.getMessage());
            ret.setData(org);
            return ret;
        }



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
    public ResponseResult<Page<OrgVo>> getOrgRelPage(String orgRootId,
                                                     String orgTreeId,
                                                     String orgId,
                                                     String sortField,
                                                     String sortOrder,
                                                     Integer pageSize,
                                                     Integer pageNo){
        ResponseResult<Page<OrgVo>> ret = new ResponseResult<>();

        if(StrUtil.isNullOrEmpty(orgTreeId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识不能为空");
            return ret;
        }

        if(!StrUtil.isNullOrEmpty(sortField)){
            if(StrUtil.isNullOrEmpty(sortOrder)){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("排序方式不能为空");
                return ret;
            }
            if(!"DESC".equals(sortOrder.toUpperCase()) && !"ASC".equals(sortOrder.toUpperCase())){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("排序参数不对");
                return ret;
            }
        }

        if(!StrUtil.isNullOrEmpty(sortOrder)){
            if(StrUtil.isNullOrEmpty(sortField)){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("排序字段不能为空");
                return ret;
            }
        }

//        if(StrUtil.isNullOrEmpty(orgRootId)){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织根节点不能为空");
//            return ret;
//        }
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        Wrapper orgTreeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgTreeId)
                .eq("STATUS_CD","1000");
        OrgTree orgTree  = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgTree == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }

        OrgVo orgVo = new OrgVo();

        orgVo.setOrgRootId(orgTree.getOrgId());
        orgVo.setOrgId(new Long(orgId));
        orgVo.setOrgTreeId(orgTree.getOrgTreeId());
        orgVo.setSortField(StrUtil.strnull(sortField));
        orgVo.setSortOrder(StrUtil.strnull(sortOrder));
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
    public ResponseResult<HashMap<String,String>> getOrgExtByOrgId(String orgTreeId,String orgRootId, String orgId){
        ResponseResult<HashMap<String,String>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识为空");
            return ret;
        }
//        if(StrUtil.isNullOrEmpty(orgRootId)){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织树根节点标识为空");
//            return ret;
//        }
        HashMap<String,String> listMap = new HashMap<String,String>();
       // String fullName = orgService.getSysFullName(orgRootId,orgId);
        String followOrg = orgTreeService.getOrgTreeNameByOrgId(orgId);
        String orgTypeInfo = orgTypeService.getOrgTypeInfoByOrgId(orgId);
       // listMap.put("FULL_NAME",fullName);
        listMap.put("FOLLOW_ORG",followOrg);
        listMap.put("ORG_TYPE_INFO",orgTypeInfo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        ret.setData(listMap);
        return ret;
    }

}

