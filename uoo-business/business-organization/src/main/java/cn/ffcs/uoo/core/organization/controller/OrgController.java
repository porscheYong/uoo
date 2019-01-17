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
import org.springframework.beans.BeanUtils;
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
import java.io.IOException;
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

    @Autowired
    private CommonSystemService commonSystemService;

    @Autowired
    private ModifyHistoryService modifyHistoryService;

    @Autowired
    private OrgRelController orgRelController;

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

        List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
        orgRelTypeListCur = orgRelTypeService.getOrgRelType(orgTree.getOrgTreeId().toString());
        if(orgRelTypeListCur==null || orgRelTypeListCur.size()<1){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织关系类型不存在");
            return ret;
        }
        OrgRelType ortCur = orgRelTypeListCur.get(0);


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


        String batchNumber = modifyHistoryService.getBatchNumber();

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
        newOrg.setCreateUser(org.getUpdateUser());
        if("1".equals(orgTree.getOrgTreeId().toString())){
            newOrg.setStandardFlag(1L);
        }else{
            newOrg.setStandardFlag(0L);
        }
        //新增组织类别
        if(orgTypeList!=null){
            for(OrgType orgType : orgTypeList) {
                Long orgTypeRefId = orgTypeRefService.getId();
                OrgOrgtypeRel orgTypeRef = new OrgOrgtypeRel();
                orgTypeRef.setOrgTypeRelId(orgTypeRefId);
                orgTypeRef.setOrgId(newOrg.getOrgId());
                orgTypeRef.setOrgTypeId(orgType.getOrgTypeId());
                orgTypeRef.setStatusCd("1000");
                orgTypeRef.setCreateUser(org.getUpdateUser());
                orgTypeRefService.add(orgTypeRef);
                modifyHistoryService.addModifyHistory(null,orgTypeRef,org.getUpdateUser(),batchNumber);
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
                orgPostRel.setCreateUser(org.getUpdateUser());
                orgPostRelService.add(orgPostRel);
                modifyHistoryService.addModifyHistory(null,orgPostRel,org.getUpdateUser(),batchNumber);
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
                    orgPosition.setCreateUser(org.getUpdateUser());
                    orgPositionRelService.add(orgPosition);
                    modifyHistoryService.addModifyHistory(null,orgPosition,org.getUpdateUser(),batchNumber);
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
            orgRef.setCreateUser(org.getUpdateUser());
            orgRelService.add(orgRef);
            modifyHistoryService.addModifyHistory(null,orgRef,org.getUpdateUser(),batchNumber);


            //组织组织树关系
            List<OrgOrgtreeRel> ootrList = orgOrgtreeRelService.getFullBizOrgList(orgTree.getOrgTreeId().toString(),org.getSupOrgId().toString());
            String fullBizName = "";
            if(ootrList!=null && ootrList.size()>0){
                for(int i=0;i<ootrList.size();i++){
                    fullBizName += ootrList.get(i).getOrgBizName();
                }
                fullBizName+=StrUtil.strnull(org.getOrgName());
            }else{
                fullBizName+=StrUtil.strnull(org.getOrgName());
            }
            Long orgOrgtreeRefId = orgOrgtreeRelService.getId();
            OrgOrgtreeRel orgOrgtreeRef = new OrgOrgtreeRel();
            orgOrgtreeRef.setOrgOrgtreeId(orgOrgtreeRefId);
            orgOrgtreeRef.setOrgId(newOrg.getOrgId());
            orgOrgtreeRef.setOrgTreeId(orgOrgRel.getOrgTreeId());
            orgOrgtreeRef.setOrgBizName(StrUtil.isNullOrEmpty(org.getOrgBizName())?org.getOrgName():StrUtil.strnull(org.getOrgBizName()));
            orgOrgtreeRef.setOrgBizFullName(fullBizName);
            orgOrgtreeRef.setStatusCd("1000");
            orgOrgtreeRef.setCreateUser(org.getUpdateUser());
            if(!StrUtil.isNullOrEmpty(org.getSort())){
                orgOrgtreeRef.setSort(Integer.valueOf(org.getSort()));
            }
            orgOrgtreeRelService.add(orgOrgtreeRef);
            modifyHistoryService.addModifyHistory(null,orgOrgtreeRef,org.getUpdateUser(),batchNumber);


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
                orgLevel.setCreateUser(org.getUpdateUser());
                orgLevelService.add(orgLevel);
                modifyHistoryService.addModifyHistory(null,orgLevel,org.getUpdateUser(),batchNumber);
            }
        }

        //新增组织证件
        List<String> cerList = org.getCertIdList();
        if(cerList!=null && cerList.size()>0){
            for(String certId : cerList){
                OrgCertRel orgCertRel = new OrgCertRel();
                Long orgCertRelId = orgCertRelService.getId();
                orgCertRel.setOrgCertId(orgCertRelId);
                orgCertRel.setOrgId(newOrg.getOrgId());
                orgCertRel.setCertId(Integer.valueOf(certId));
                orgCertRel.setCreateUser(org.getUpdateUser());
                orgCertRelService.add(orgCertRel);
                modifyHistoryService.addModifyHistory(null,orgCertRel,org.getUpdateUser(),batchNumber);
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
                orgConRel.setCreateUser(org.getUpdateUser());
                orgContactRelService.add(orgConRel);
                modifyHistoryService.addModifyHistory(null,orgConRel,org.getUpdateUser(),batchNumber);
            }
        }
        orgService.add(newOrg);
        modifyHistoryService.addModifyHistory(null,newOrg,org.getUpdateUser(),batchNumber);
//        }
        //新增营销组织扩展属性
        if("0401".equals(ortCur.getRefCode())){
            //boolean isExitExp = false;
            if(extValueList!=null && extValueList.size()>0){
                ResponseResult<ExpandovalueVo> publicRet = new ResponseResult<ExpandovalueVo>();
                for(ExpandovalueVo extVo : extValueList){
                    extVo.setTableName("TB_ORG");
                    extVo.setRecordId(newOrg.getOrgId().toString());
                    publicRet = expandovalueService.addExpandoInfo(extVo);
                }
                //isExitExp = true;
            }


            //if (isExitExp) {

            String orgMarkCodeRet = jdbcTemplate.execute(new ConnectionCallback<String>() {
                @Override
                public String doInConnection(Connection conn) throws SQLException, DataAccessException {
                    CallableStatement cstmt = null;
                    String result = "";
                    try {
                        cstmt = conn.prepareCall("{CALL P_ORG_CNTRT_MGMT_GENERATOR (?,?,?)}");
                        cstmt.setObject(1, orgCode);
                        cstmt.registerOutParameter(2, Types.VARCHAR);
                        cstmt.registerOutParameter(3, Types.VARCHAR);
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
            //}
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

        List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
        orgRelTypeListCur = orgRelTypeService.getOrgRelType(orgTree.getOrgTreeId().toString());
        if(orgRelTypeListCur==null || orgRelTypeListCur.size()<1){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织关系类型不存在");
            return ret;
        }
        OrgRelType ortCur = orgRelTypeListCur.get(0);



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

        String batchNumber = modifyHistoryService.getBatchNumber();

        Org newOrg = new Org();
        newOrg.setOrgId(org.getOrgId());
        newOrg.setUpdateUser(org.getUpdateUser());
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
                    orgTypeRef.setCreateUser(org.getUpdateUser());
                    orgTypeRefService.add(orgTypeRef);
                    modifyHistoryService.addModifyHistory(null,orgTypeRef,org.getUpdateUser(),batchNumber);
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
                    otf.setUpdateUser(org.getUpdateUser());
                    orgTypeRefService.delete(otf);
                    modifyHistoryService.addModifyHistory(otf,null,org.getUpdateUser(),batchNumber);
                }
            }
        }else{
            if(orgTypeRefCurList!=null && orgTypeRefCurList.size()>0){
                for(OrgOrgtypeRel otf : orgTypeRefCurList){
                    otf.setUpdateUser(org.getUpdateUser());
                    orgTypeRefService.delete(otf);
                    modifyHistoryService.addModifyHistory(otf,null,org.getUpdateUser(),batchNumber);
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
                    orgPosition.setCreateUser(org.getUpdateUser());
                    orgPositionRelService.add(orgPosition);
                    modifyHistoryService.addModifyHistory(null,orgPosition,org.getUpdateUser(),batchNumber);
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
                    op.setUpdateUser(org.getUpdateUser());
                    orgPositionRelService.delete(op);
                    modifyHistoryService.addModifyHistory(op,null,org.getUpdateUser(),batchNumber);
                }
            }
        }else{
            if(orgPositionCurList!=null && orgPositionCurList.size()>0){
                for(OrgPositionRel op:orgPositionCurList){
                    op.setUpdateUser(org.getUpdateUser());
                    orgPositionRelService.delete(op);
                    modifyHistoryService.addModifyHistory(op,null,org.getUpdateUser(),batchNumber);
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
                    orgPost.setCreateUser(org.getUpdateUser());
                    orgPostRelService.add(orgPost);
                    modifyHistoryService.addModifyHistory(null,orgPost,org.getUpdateUser(),batchNumber);
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
                    op.setUpdateUser(org.getUpdateUser());
                    orgPostRelService.delete(op);
                    modifyHistoryService.addModifyHistory(op,null,org.getUpdateUser(),batchNumber);
                }
            }
        }else{
            if(orgPostCurList!=null && orgPostCurList.size()>0){
                for(OrgPostRel op : orgPostCurList){
                    op.setUpdateUser(org.getUpdateUser());
                    orgPostRelService.delete(op);
                    modifyHistoryService.addModifyHistory(op,null,org.getUpdateUser(),batchNumber);
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
                    orgCertRel.setCreateUser(org.getUpdateUser());
                    orgCertRelService.add(orgCertRel);
                    modifyHistoryService.addModifyHistory(null,orgCertRel,org.getUpdateUser(),batchNumber);
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
                    ocr.setUpdateUser(org.getUpdateUser());
                    orgCertRelService.delete(ocr);
                    modifyHistoryService.addModifyHistory(ocr,null,org.getUpdateUser(),batchNumber);
                }
            }
        }else{
            if(orgCertRelcurList!=null && orgCertRelcurList.size()>0){
                for(OrgCertRel ocr : orgCertRelcurList){
                    ocr.setUpdateUser(org.getUpdateUser());
                    orgCertRelService.delete(ocr);
                    modifyHistoryService.addModifyHistory(ocr,null,org.getUpdateUser(),batchNumber);
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
                    orgConRel.setCreateUser(org.getUpdateUser());
                    orgContactRelService.add(orgConRel);
                    modifyHistoryService.addModifyHistory(null,orgConRel,org.getUpdateUser(),batchNumber);
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
                    oct.setUpdateUser(org.getUpdateUser());
                    orgContactRelService.delete(oct);
                    modifyHistoryService.addModifyHistory(oct,null,org.getUpdateUser(),batchNumber);
                }
            }
        }else{
            if(orgContactRelCurList!=null && orgContactRelCurList.size()>0){
                for(OrgContactRel oct : orgContactRelCurList){
                    oct.setUpdateUser(org.getUpdateUser());
                    orgContactRelService.delete(oct);
                    modifyHistoryService.addModifyHistory(oct,null,org.getUpdateUser(),batchNumber);
                }
            }
        }

        ResponseResult<List<ExpandovalueVo>> publicRet = expandovalueService.queryExpandovalueVoList("TB_ORG",org.getOrgId().toString());
        List<ExpandovalueVo> curExtList = publicRet.getData();
        //营销组织扩展类型
        if("0401".equals(ortCur.getRefCode()) && "1000".equals(org.getStatusCd())){

            //更新营销组织属性
            isExists = false;
            if(expList!=null && expList.size()>0){
                for(ExpandovalueVo  vo : expList){
                    if(StrUtil.isNullOrEmpty(vo.getData())){
                        continue;
                    }
                    isExists = false;
                    if(curExtList!=null && curExtList.size()>0){
                        for(ExpandovalueVo curVo : curExtList){
                            if(StrUtil.isNullOrEmpty(vo.getValueId())){
                                isExists=false;
                                break;
                            }
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
                    ResponseResult<TbExpandovalue>  rr =null;
                    for(ExpandovalueVo curVo : curExtList){
                        isExists = false;
                        for(ExpandovalueVo  vo : expList){
                            if(StrUtil.isNullOrEmpty(vo.getValueId())){
                                isExists=false;
                                break;
                            }
                            if((vo.getValueId().toString()).equals(curVo.getValueId().toString())){
                                isExists=true;
                                break;
                            }
                        }
                        if(!isExists){
                            //删除
                            rr = expandovalueService.removeTbExpandovalue(curVo.getValueId(),0L);
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
            //更新组织 并且新增营销化小编码 curExtList.size()>0 && StrUtil.isNullOrEmpty(org.getOrgMartCode())
            if(expList!=null && expList.size()>0 && StrUtil.isNullOrEmpty(org.getOrgMartCode()) && StrUtil.isNullOrEmpty(o.getOrgMartCode())){
                String orgMarkCodeRet = jdbcTemplate.execute(new ConnectionCallback<String>() {
                    @Override
                    public String doInConnection(Connection conn) throws SQLException, DataAccessException {
                        CallableStatement cstmt = null;
                        String result = "";
                        try {
                            cstmt = conn.prepareCall("{CALL P_ORG_CNTRT_MGMT_GENERATOR (?,?,?)}");
                            cstmt.setObject(1, o.getOrgCode());
                            cstmt.registerOutParameter(2, Types.VARCHAR);
                            cstmt.registerOutParameter(3, Types.VARCHAR);
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
            if((expList==null || expList.size()==0) && !StrUtil.isNullOrEmpty(o.getOrgMartCode())){
                //删除营销属性
                String orgMarkCodeRet = jdbcTemplate.execute(new ConnectionCallback<String>() {
                    @Override
                    public String doInConnection(Connection conn) throws SQLException, DataAccessException {
                        CallableStatement cstmt = null;
                        String result = "";
                        try {
                            cstmt = conn.prepareCall("{CALL P_ORG_CNTRT_MGMT_DEL (?,?)}");
                            cstmt.setObject(1, o.getOrgCode());
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
        }
        //更新组织组织树关系
        Wrapper orgTreeRelOneWrapper = Condition.create()
                .eq("ORG_ID",org.getOrgId())
                .eq("STATUS_CD","1000")
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId());
        OrgOrgtreeRel orgOrgtreeRelOne = orgOrgtreeRelService.selectOne(orgTreeRelOneWrapper);
        OrgOrgtreeRel orgOrgtreeRelOLd = new OrgOrgtreeRel();
        BeanUtils.copyProperties(orgOrgtreeRelOne,orgOrgtreeRelOLd);
        if(orgOrgtreeRelOne!=null){
            if(!StrUtil.isNullOrEmpty(org.getOrgBizName())) {
                orgOrgtreeRelOne.setOrgBizName(org.getOrgBizName());
            }

            List<OrgOrgtreeRel> ootrList = orgOrgtreeRelService.getFullBizOrgList(orgTree.getOrgTreeId().toString(),org.getOrgId().toString());
            if(ootrList!=null && ootrList.size()>0){
                if(ootrList.size()==1){
                    orgOrgtreeRelOne.setOrgBizFullName(org.getOrgBizName());
                }else{
                    String fullName = "";
                    for(int i=0;i<ootrList.size()-1;i++){
                        fullName += ootrList.get(i).getOrgBizName();
                    }
                    fullName+=orgOrgtreeRelOne.getOrgBizName();
                    orgOrgtreeRelOne.setOrgBizFullName(fullName);
                }
            }
            if(!StrUtil.isNullOrEmpty(org.getSort())){
                orgOrgtreeRelOne.setSort(Integer.valueOf(org.getSort()));
            }
            orgOrgtreeRelOne.setUpdateUser(org.getUpdateUser());
            orgOrgtreeRelService.update(orgOrgtreeRelOne);
            modifyHistoryService.addModifyHistory(orgOrgtreeRelOLd,orgOrgtreeRelOne,org.getUpdateUser(),batchNumber);
        }


        if (!"1000".equals(org.getStatusCd())){

            if("0401".equals(ortCur.getRefCode())) {
                //删除扩展类型
                if(curExtList!=null && curExtList.size()>0){
                    for(ExpandovalueVo vo : curExtList){
                        //删除所有
                        expandovalueService.removeTbExpandovalue(vo.getValueId(),0L);
                    }
                }
                String orgMarkCodeRet = jdbcTemplate.execute(new ConnectionCallback<String>() {
                    @Override
                    public String doInConnection(Connection conn) throws SQLException, DataAccessException {
                        CallableStatement cstmt = null;
                        String result = "";
                        try {
                            cstmt = conn.prepareCall("{CALL P_ORG_CNTRT_MGMT_DEL (?,?)}");
                            cstmt.setObject(1, o.getOrgCode());
                            cstmt.registerOutParameter(2, Types.VARCHAR);
                            cstmt.execute();
                            if (!StrUtil.isNullOrEmpty(cstmt.getString(2))) {
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
            //删除组织关系
            for(OrgRel or : orList){

                Wrapper orgTreeRelWrapper = Condition.create()
                        .eq("ORG_ID",org.getOrgId())
                        .eq("STATUS_CD","1000")
                        .eq("ORG_TREE_ID",orgTree.getOrgTreeId());
                List<OrgOrgtreeRel> orgOrgtreeRelList = orgOrgtreeRelService.selectList(orgTreeRelWrapper);
                if(orgOrgtreeRelList!=null && orgOrgtreeRelList.size()>0){
                    for(OrgOrgtreeRel ootr : orgOrgtreeRelList){
                        ootr.setUpdateUser(org.getUpdateUser());
                        orgOrgtreeRelService.delete(ootr);
                        modifyHistoryService.addModifyHistory(ootr,null,org.getUpdateUser(),batchNumber);
                    }
                }
                Wrapper orgLevelWrapper = Condition.create()
                        .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                        .eq("STATUS_CD","1000")
                        .eq("ORG_ID",org.getOrgId());
                List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
                if(orgLevelList!=null && orgLevelList.size()>0){
                    for(OrgLevel ol : orgLevelList){
                        ol.setUpdateUser(org.getUpdateUser());
                        orgLevelService.delete(ol);
                        modifyHistoryService.addModifyHistory(ol,null,org.getUpdateUser(),batchNumber);
                    }
                }
                Wrapper orgPositionWrapper = Condition.create()
                        .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                        .eq("STATUS_CD","1000")
                        .eq("ORG_ID",org.getOrgId());
                List<OrgPositionRel> orgPositionRelList = orgPositionRelService.selectList(orgPositionWrapper);
                if(orgPositionRelList!=null && orgPositionRelList.size()>0){
                    for(OrgPositionRel opr : orgPositionRelList){
                        opr.setUpdateUser(org.getUpdateUser());
                        orgPositionRelService.delete(opr);
                        modifyHistoryService.addModifyHistory(opr,null,org.getUpdateUser(),batchNumber);
                    }
                }


                //删除证件组织关系
                Wrapper orgCertListWrapper = Condition.create()
                        .eq("STATUS_CD","1000")
                        .eq("ORG_ID",org.getOrgId());
                List<OrgCertRel> orgCertRelList = orgCertRelService.selectList(orgCertListWrapper);
                if(orgCertRelList!=null && orgCertRelList.size()>0){
                    for(OrgCertRel vo:orgCertRelList){
                        vo.setUpdateUser(org.getUpdateUser());
                        orgCertRelService.delete(vo);
                        modifyHistoryService.addModifyHistory(vo,null,org.getUpdateUser(),batchNumber);
                    }
                }

                //删除组织联系人关系
                Wrapper orgContactListWrapper = Condition.create()
                        .eq("STATUS_CD","1000")
                        .eq("ORG_ID",org.getOrgId());
                List<OrgContactRel> orgContactRelList = orgContactRelService.selectList(orgContactListWrapper);
                if(orgContactRelList!=null && orgContactRelList.size()>0){

                    for(OrgContactRel vo:orgContactRelList){
                        vo.setUpdateUser(org.getUpdateUser());
                        orgContactRelService.delete(vo);
                        modifyHistoryService.addModifyHistory(vo,null,org.getUpdateUser(),batchNumber);
                    }
                }

                or.setUpdateUser(org.getUpdateUser());
                orgRelService.delete(or);
                modifyHistoryService.addModifyHistory(or,null,org.getUpdateUser(),batchNumber);
                newOrg.setStatusCd("1000");
                newOrg.setUpdateUser(org.getUpdateUser());
                newOrg.setStandardFlag(0L);
                orgService.update(newOrg);
                modifyHistoryService.addModifyHistory(o,newOrg,org.getUpdateUser(),batchNumber);
                String mqmsg = "{\"type\":\"org\",\"handle\":\"update\",\"context\":{\"column\":\"orgId\",\"value\":"+newOrg.getOrgId()+"}}" ;
                template.convertAndSend("message_sharing_center_queue",mqmsg);
                ret.setState(ResponseResult.STATE_OK);
                ret.setMessage("更新成功");
                return ret;
            }
        }
        newOrg.setStatusCd("1000");
        orgService.update(newOrg);
        modifyHistoryService.addModifyHistory(o,newOrg,org.getUpdateUser(),batchNumber);
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
                                            String supOrgId,
                                            Long userId){
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


        List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
        orgRelTypeListCur = orgRelTypeService.getOrgRelType(orgTreeId);
        if(orgRelTypeListCur==null || orgRelTypeListCur.size()<1){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织关系类型不存在");
            return ret;
        }
        OrgRelType ortCur = orgRelTypeListCur.get(0);
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

        String batchNumber = modifyHistoryService.getBatchNumber();

        Wrapper orgWrapper = Condition.create()
                .eq("ORG_ID",orgId)
                .eq("STATUS_CD","1000");
        Org org = orgService.selectOne(orgWrapper);
        Org oldOrg = new Org();
        BeanUtils.copyProperties(org,oldOrg);
        org.setStandardFlag(0L);
        orgService.update(org);
        modifyHistoryService.addModifyHistory(oldOrg,org,userId,batchNumber);


        List<OrgRel> orgRelList = orgRelService.getOrgRel(orgTreeId,orgId);
        for(OrgRel orgRel : orgRelList){
            orgRel.setUpdateUser(userId);
            orgRelService.delete(orgRel);
            modifyHistoryService.addModifyHistory(orgRel,null,userId,batchNumber);
            Wrapper orgTreeRelWrapper = Condition.create()
                    .eq("ORG_ID",org.getOrgId())
                    .eq("STATUS_CD","1000")
                    .eq("ORG_TREE_ID",orgTree.getOrgTreeId());
            List<OrgOrgtreeRel> orgOrgtreeRelList = orgOrgtreeRelService.selectList(orgTreeRelWrapper);
            for(OrgOrgtreeRel ootr : orgOrgtreeRelList){
                ootr.setUpdateUser(userId);
                orgOrgtreeRelService.delete(ootr);
                modifyHistoryService.addModifyHistory(ootr,null,userId,batchNumber);
            }

            Wrapper orgLevelWrapper = Condition.create()
                    .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                    .eq("STATUS_CD","1000")
                    .eq("ORG_ID",org.getOrgId());
            List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
            for(OrgLevel ol : orgLevelList){
                ol.setUpdateUser(userId);
                orgLevelService.delete(ol);
                modifyHistoryService.addModifyHistory(ol,null,userId,batchNumber);
            }

            Wrapper orgPositionWrapper = Condition.create()
                    .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                    .eq("STATUS_CD","1000")
                    .eq("ORG_ID",org.getOrgId());

            List<OrgPositionRel> orgPositionRelList = orgPositionRelService.selectList(orgPositionWrapper);
            for(OrgPositionRel opr : orgPositionRelList){
                opr.setUpdateUser(userId);
                orgPositionRelService.delete(opr);
                modifyHistoryService.addModifyHistory(opr,null,userId,batchNumber);
            }
        }
        //删除证件组织关系
        Wrapper orgCertListWrapper = Condition.create()
                .eq("STATUS_CD","1000")
                .eq("ORG_ID",org.getOrgId());
        List<OrgCertRel> orgCertRelList = orgCertRelService.selectList(orgCertListWrapper);
        for(OrgCertRel vo:orgCertRelList){
            vo.setUpdateUser(userId);
            orgCertRelService.delete(vo);
            modifyHistoryService.addModifyHistory(vo,null,userId,batchNumber);
        }
        //删除组织联系人关系
        Wrapper orgContactListWrapper = Condition.create()
                .eq("STATUS_CD","1000")
                .eq("ORG_ID",org.getOrgId());
        List<OrgContactRel> orgContactRelList = orgContactRelService.selectList(orgContactListWrapper);
        for(OrgContactRel vo:orgContactRelList){
            vo.setUpdateUser(userId);
            orgContactRelService.delete(vo);
            modifyHistoryService.addModifyHistory(vo,null,userId,batchNumber);
        }

        if("0401".equals(ortCur.getRefCode())) {
            //删除扩展类型
            ResponseResult<List<ExpandovalueVo>> publicRet = expandovalueService.queryExpandovalueVoList("TB_ORG",org.getOrgId().toString());
            List<ExpandovalueVo> curExtList = publicRet.getData();
            if(curExtList!=null && curExtList.size()>0){
                for(ExpandovalueVo vo : curExtList){
                    //删除所有
                    expandovalueService.removeTbExpandovalue(vo.getValueId(),0L);
                }
            }
            String orgMarkCodeRet = jdbcTemplate.execute(new ConnectionCallback<String>() {
                @Override
                public String doInConnection(Connection conn) throws SQLException, DataAccessException {
                    CallableStatement cstmt = null;
                    String result = "";
                    try {
                        cstmt = conn.prepareCall("{CALL P_ORG_CNTRT_MGMT_DEL (?,?)}");
                        cstmt.setObject(1, org.getOrgCode());
                        cstmt.registerOutParameter(2, Types.VARCHAR);
                        cstmt.execute();
                        if (!StrUtil.isNullOrEmpty(cstmt.getString(2))) {
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

//        if(StrUtil.isNullOrEmpty(orgTreeId)){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织树标识不能为空");
//            return ret;
//        }
        OrgVo org = new OrgVo();
        if(!StrUtil.isNullOrEmpty(orgTreeId)){
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
            org = orgService.selectOrgByOrgId(orgId,orgTreeId);
            if(StrUtil.isNullOrEmpty(org)){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织不存在");
                return ret;
            }

            if(!"1".equals(orgTreeId) &&
                    !StrUtil.isNullOrEmpty(org.getStandardFlag()) &&
                    "1".equals(org.getStandardFlag())){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("属于标准树的组织只能在标准树上操作");
                return ret;
            }
        }else{

            Wrapper orgWrapper = Condition.create()
                    .eq("STATUS_CD","1000")
                    .eq("ORG_ID",orgId);
            Org org1 = new Org();
            org1 = orgService.selectOne(orgWrapper);
            if(org1==null){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织不存在");
                return ret;
            }
            BeanUtils.copyProperties(org1, org);
        }

        if(!StrUtil.isNullOrEmpty(orgTreeId)){
            Wrapper orgOrgTreeRelConfWrapper = Condition.create()
                    .eq("ORG_TREE_ID",orgTreeId)
                    .eq("STATUS_CD","1000")
                    .eq("ORG_ID",orgId);
            OrgOrgtreeRel orgOrgTreeRel = orgOrgtreeRelService.selectOne(orgOrgTreeRelConfWrapper);
            if(orgOrgTreeRel!=null){
                if(!StrUtil.isNullOrEmpty(orgOrgTreeRel.getSort())){
                    org.setSort(orgOrgTreeRel.getSort().toString());
                }
            }
        }



        //组织类别
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
                                                     Integer pageNo,
                                                     Long userId,
                                                     String accout){
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
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }


        OrgVo orgVo = new OrgVo();
        //获取权限
        if(!StrUtil.isNullOrEmpty(accout)) {
            List<String> tabNames = new ArrayList<String>();
            tabNames.add("TB_ORG_TREE");
            tabNames.add("TB_ORG");
            tabNames.add("TB_ORG_REL");
            tabNames.add("TB_ORG_ORGTYPE_REL");
            List<SysDataRule> sdrList = commonSystemService.getSysDataRuleList(tabNames, accout);
            if(sdrList!=null && sdrList.size()>0){
                if(!commonSystemService.isOrgTreeAutho(orgTreeId,sdrList)){
                    ret.setState(ResponseResult.PARAMETER_ERROR);
                    ret.setMessage("无权限");
                    return ret;
                }
                String orgParams = commonSystemService.getSysDataRuleSql("TB_ORG",sdrList);
                String orgOrgTypeParams = commonSystemService.getSysDataRuleSql("TB_ORG_ORGTYPE_REL",sdrList);
                orgVo.setTabOrgParams(orgParams);
                orgVo.setTabOrgOrgTypeParams(orgOrgTypeParams);
            }
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
                                                  String orgTreeId,
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
        if(!StrUtil.isNullOrEmpty(orgTreeId)){
            orgVo.setOrgTreeId(new Long(orgTreeId));
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


    @ApiOperation(value = "新增标准树组织", notes = "新增标准树组织")
    @UooLog(value = "新增标准树组织", key = "addBaseOrg")
    @RequestMapping(value = "/addBaseOrg", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> addBaseOrg(@RequestBody List<TreeNodeVo> treeNodeVos) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
        String errorStr = new String();
        for(TreeNodeVo vo : treeNodeVos){
            ResponseResult<String> delret = new ResponseResult<String>();
            ResponseResult<TreeNodeVo> addret = new ResponseResult<TreeNodeVo>();
            if("delete".equals(vo.getOper())){
                delret = deleteOrg(vo.getId(),"1",null,null);
                if(delret.getState()==ResponseResult.PARAMETER_ERROR){
                    errorStr = "组织:"+vo.getName()+",删除失败["+delret.getMessage()+"]"+"\n";
                }
            }else if("add".equals(vo.getOper())){
                Org org = new Org();
                org.setOrgId(new Long(vo.getId()));
                org.setSupOrgId(new Long(vo.getPid()));
                org.setOrgTreeId(1L);
                addret = orgRelController.addOrgRel(org);
                if(addret.getState()==ResponseResult.PARAMETER_ERROR){
                    if(addret.getState()==ResponseResult.PARAMETER_ERROR){
                        errorStr = "组织:"+vo.getName()+",新增失败["+addret.getMessage()+"]"+"\n";
                    }
                }
            }

        }
        if(!StrUtil.isNullOrEmpty(errorStr)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage(errorStr);
        }else{
            ret.setState(ResponseResult.STATE_OK);
            ret.setMessage(errorStr);
        }
        return ret;
    }

//    @ApiOperation(value = "获取BSS组织", notes = "获取BSS组织")
////    @UooLog(value = "获取BSS组织", key = "getBssOrg")
////    @RequestMapping(value = "/getBssOrg", method = RequestMethod.GET)
////    @Transactional(rollbackFor = Exception.class)
////    public ResponseResult<Page<TreeNodeVo>> getBssOrg() throws IOException {
////        ResponseResult<Page<TreeNodeVo>> ret = new ResponseResult<List<TreeNodeVo>>();
////        orgService.getBssOrg();
////        return ret;
////    }


}

