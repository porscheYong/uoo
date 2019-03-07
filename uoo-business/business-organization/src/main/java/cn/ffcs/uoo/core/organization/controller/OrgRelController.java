package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.Api.service.ExpandovalueService;
import cn.ffcs.uoo.core.organization.entity.*;
import cn.ffcs.uoo.core.organization.service.*;
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
import io.swagger.models.Xml;
import io.swagger.models.auth.In;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@RestController
@RequestMapping("/orgRel")
@Api(value = "/orgRel", description = "组织关系相关操作")
public class OrgRelController extends BaseController {


    @Autowired
    private OrgRelService orgRelService;

    @Autowired
    private OgtOrgReltypeConfService ogtOrgReftypeConfService;

    @Autowired
    private OrgOrgtreeRelService orgOrgtreeRelService;

    @Autowired
    private OrgLevelService orgLevelService;

    @Autowired
    private OrgRelTypeService orgRelTypeService;

    @Autowired
    private OrgTreeService orgTreeService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private CommonSystemService commonSystemService;

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private ModifyHistoryService modifyHistoryService;


    @Autowired
    private ExpandovalueService expandovalueService;


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private IOrgTreeSynchRuleService iOrgTreeSynchRuleService;


    @ApiOperation(value = "查询组织树信息-web", notes = "查询组织树信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织树", key = "getOrgRelTree")
    @RequestMapping(value = "/getOrgRelTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getOrgRelTree(String id,String orgTreeId,String orgRootId,String refCode,
                                                          boolean isOpen,boolean isAsync,boolean isRoot,Long userId,String accout) throws IOException {
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgTreeId) && StrUtil.isNullOrEmpty(refCode)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识和关系类型不能同时为空");
            return ret;
        }
        OrgTree orgTree = null;
        if(!StrUtil.isNullOrEmpty(refCode)){
            orgTree = orgTreeService.getOrgTreeByRefCode(refCode);
            if (orgTree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }else {
            //查询组织树
            Wrapper orgTreeConfWrapper = Condition.create().eq("ORG_TREE_ID", orgTreeId).eq("STATUS_CD", "1000");
            orgTree = orgTreeService.selectOne(orgTreeConfWrapper);
            if (orgTree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }
        OrgRelType ortCur = null;
        if(StrUtil.isNullOrEmpty(refCode)){
            List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
            orgRelTypeListCur = orgRelTypeService.getOrgRelType(orgTreeId);
            if(orgRelTypeListCur==null && orgRelTypeListCur.size()==0){
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织类型不存在");
                return ret;
            }
            ortCur = orgRelTypeListCur.get(0);
        }
        System.out.printf("getOrgRelTree-权限获取时间开始: %tF %<tT%n", System.currentTimeMillis());
        //获取权限
        String orgParams = "";
        String orgOrgTypeParams = "";
        String orgOrgTreeRelParams = "";
        if(!StrUtil.isNullOrEmpty(accout)) {
            List<String> tabNames = new ArrayList<String>();
            tabNames.add("TB_ORG_TREE");
            tabNames.add("TB_ORG");
            tabNames.add("TB_ORG_REL");
            tabNames.add("TB_ORG_ORGTYPE_REL");
            tabNames.add("TB_ORG_ORGTREE_REL");
            List<SysDataRule> sdrList = commonSystemService.getSysDataRuleList(tabNames, accout);
            // TODO: 2019/2/20
            List<String> tabNamenews = new ArrayList<String>();
            tabNamenews.add("TB_ORG_ORGTREE_REL");
            List<SysDataRule> sdrList1 = commonSystemService.getSysDataRuleList(tabNamenews,accout,orgTreeId);
            if(sdrList1!=null && sdrList1.size()>0){
                orgOrgTreeRelParams = commonSystemService.getSysDataRuleParams("TB_ORG_ORGTREE_REL","ORG_BIZ_FULL_ID",sdrList);
                if(!StrUtil.isNullOrEmpty(orgOrgTreeRelParams)) {
                    orgOrgTreeRelParams = commonSystemService.getOrgOrgTreeRelSql(orgOrgTreeRelParams,orgTree.getOrgTreeId().toString());
                }
            }

            if(sdrList!=null && sdrList.size()>0){
                if(!commonSystemService.isOrgTreeAutho(orgTreeId,sdrList)){
                    ret.setState(ResponseResult.PARAMETER_ERROR);
                    ret.setMessage("无权限");
                    return ret;
                }
                orgParams = commonSystemService.getSysDataRuleSql("TB_ORG",sdrList);
                orgOrgTypeParams = commonSystemService.getSysDataRuleSql("TB_ORG_ORGTYPE_REL",sdrList);
//                orgOrgTreeRelParams = commonSystemService.getSysDataRuleParams("TB_ORG_ORGTREE_REL","ORG_BIZ_FULL_ID",sdrList);
//                if(!StrUtil.isNullOrEmpty(orgOrgTreeRelParams)) {
//                    orgOrgTreeRelParams = commonSystemService.getOrgOrgTreeRelSql(orgOrgTreeRelParams,orgTree.getOrgTreeId().toString());
//                }
            }
        }
        System.out.printf("getOrgRelTree-权限获取时间结束: %tF %<tT%n", System.currentTimeMillis());
        System.out.printf("getOrgRelTree-查询树开始: %tF %<tT%n", System.currentTimeMillis());
        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        treeNodeVos = orgRelService.queryOrgTree(orgTree.getOrgTreeId().toString(),orgTree.getOrgId(),ortCur.getRefCode(),
                id,isRoot,orgParams,orgOrgTypeParams,orgOrgTreeRelParams);
        System.out.printf("getOrgRelTree-查询树结束: %tF %<tT%n", System.currentTimeMillis());
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("组织树查询成功");
        ret.setData(treeNodeVos);
        System.out.println(new Date());
        return ret;
    }
    @ApiOperation(value = "重构组织树获取-web", notes = "重构组织树获取")
    @ApiImplicitParams({
    })
    @UooLog(value = "重构组织树获取", key = "getOrgRelTree")
    @RequestMapping(value = "/getRestructOrgRelTree", method = RequestMethod.GET)
    //@Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(String orgId,String orgTreeId,String orgRootId,boolean isFull) throws IOException {
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
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
        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        treeNodeVos = orgRelService.selectFuzzyOrgRelTree(orgId,orgTreeId,isFull);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("组织树查询成功");
        ret.setData(treeNodeVos);
        return ret;
    }


    @ApiOperation(value = "组织树以及层级获取-web", notes = "组织树以及层级获取")
    @ApiImplicitParams({
    })
    @UooLog(value = "组织树以及层级获取", key = "getTarOrgRelTreeAndLv")
    @RequestMapping(value = "/getTarOrgRelTreeAndLv", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getTarOrgRelTreeAndLv(String orgRootId,String orgTreeId,String lv,String curOrgid,boolean isFull) throws IOException {
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgTreeId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(lv)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("层级不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(isFull)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("是否全量不能为空");
            return ret;
        }
        Wrapper orgTreeConfWrapper = Condition.create().eq("ORG_TREE_ID",orgTreeId).eq("STATUS_CD","1000");
        OrgTree orgTree  = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgTree == null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }
        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        treeNodeVos = orgRelService.selectTarOrgRelTreeAndLv(orgTree.getOrgId(),orgTreeId,lv,curOrgid,isFull);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("组织树查询成功");
        ret.setData(treeNodeVos);
        return ret;
    }




    @ApiOperation(value = "新增组织关系-web", notes = "新增组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "org", value = "组织", required = true, dataType = "OrgVo"),
    })
    @UooLog(value = "新增组织关系", key = "addOrgRel")
    @RequestMapping(value = "/addOrgRel", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<TreeNodeVo> addOrgRel(@RequestBody OrgVo org) throws IOException {
        ResponseResult<TreeNodeVo> ret = new ResponseResult<TreeNodeVo>();
        if(StrUtil.isNullOrEmpty(org.getOrgId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(org.getOrgTreeId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识不能为空");
            return ret;
        }

        if(StrUtil.isNullOrEmpty(org.getSupOrgId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织父节点不能为空");
            return ret;
        }
        //查询组织树
        Wrapper orgtreeWrapper = Condition.create().eq("ORG_TREE_ID",org.getOrgTreeId())
                .eq("STATUS_CD","1000");
        OrgTree orgTree = orgTreeService.selectOne(orgtreeWrapper);
        if(orgTree==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不能为空");
            return ret;
        }
        //判断组织是否需已经下挂在该组织树上
        Wrapper orgOrgtreeRefWrapper = Condition.create().eq("ORG_ID",org.getOrgId())
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                .eq("STATUS_CD","1000");
        List<OrgOrgtreeRel> orgOrgtreeRefList = orgOrgtreeRelService.selectList(orgOrgtreeRefWrapper);
        if(orgOrgtreeRefList != null && orgOrgtreeRefList.size()>0){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织已经下挂该组织树");
            return ret;
        }

        //查询组织树
        Wrapper orgWrapper = Condition.create().eq("ORG_ID",org.getOrgId())
                .eq("STATUS_CD","1000");
        Org o = orgService.selectOne(orgWrapper);
        if(o==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织不存在");
            return ret;
        }


        Wrapper ogtOrgReftypeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                .eq("STATUS_CD","1000");

        List<OgtOrgReltypeConf> ogtOrgReftypeConfList =  ogtOrgReftypeConfService.selectList(ogtOrgReftypeConfWrapper);
        if(ogtOrgReftypeConfList == null || ogtOrgReftypeConfList.size() < 0){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织关系类型关联不存在");
            return ret;
        }
        OgtOrgReltypeConf ogtOrgReftypeConf = ogtOrgReftypeConfList.get(0);
        Wrapper orgReltypeConfWrapper = Condition.create()
                .eq("ORG_REL_TYPE_ID",ogtOrgReftypeConf.getOrgRelTypeId())
                .eq("STATUS_CD","1000");
        OrgRelType ort = orgRelTypeService.selectOne(orgReltypeConfWrapper);
        if(ort==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织关系类型不存在");
            return ret;
        }
        OrgUpdateCheckResult syncRet = new OrgUpdateCheckResult();
        syncRet = iOrgTreeSynchRuleService.check(OrgUpdateCheckResult.OrgOperateType.DELETE,orgTree.getOrgTreeId(),org.getOrgId());
        if(!syncRet.isVaild()){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("同步规则不允许删除操作");
            return ret;
        }


        String batchNumber = modifyHistoryService.getBatchNumber();


        if("1".equals(orgTree.getOrgTreeId().toString())){
            Org oldOrg = new Org();
            BeanUtils.copyProperties(o,oldOrg);
            o.setStandardFlag(1L);
            orgService.update(o);
            modifyHistoryService.addModifyHistory(oldOrg,0,org.getUpdateUser(),batchNumber);
        }

        //新增组织关系
        OrgRel orgRel = new OrgRel();
        Long orgRefId = orgRelService.getId();
        orgRel.setOrgRelId(orgRefId);
        orgRel.setOrgId(org.getOrgId());
        orgRel.setParentOrgId(org.getSupOrgId());
        orgRel.setRefCode(ort.getRefCode());
        orgRel.setStatusCd("1000");
        orgRel.setCreateUser(org.getUpdateUser());
        orgRelService.add(orgRel);
        modifyHistoryService.addModifyHistory(null,orgRel,org.getUpdateUser(),batchNumber);



        //新增组织层级
        Wrapper orgLevelWrapper = Condition.create().eq("ORG_TREE_ID",org.getOrgTreeId())
                .eq("STATUS_CD","1000")
                .eq("ORG_ID",org.getSupOrgId());
        List<OrgLevel> orgLevelList = orgLevelService.selectList(orgLevelWrapper);
        if(orgLevelList != null || orgLevelList.size() > 0){
            OrgLevel orgL = orgLevelList.get(0);
            int lv = orgL.getOrgLevel()+1;
            Long  orgLevelId = orgLevelService.getId();
            OrgLevel orgLevel = new OrgLevel();
            orgLevel.setOrgLevelId(orgLevelId);
            orgLevel.setOrgId(org.getOrgId());
            orgLevel.setOrgLevel(lv);
            orgLevel.setOrgTreeId(orgTree.getOrgTreeId());
            orgLevel.setStatusCd("1000");
            orgLevel.setCreateUser(org.getUpdateUser());
            orgLevelService.add(orgLevel);
            modifyHistoryService.addModifyHistory(null,orgLevel,org.getUpdateUser(),batchNumber);
        }

        //组织组织树关系
        // TODO: 2019/1/23
        String fullBizName = "";
        fullBizName = orgOrgtreeRelService.getFullBizOrgNameList(orgTree.getOrgTreeId().toString(),org.getSupOrgId().toString(),"");
        fullBizName+=StrUtil.strnull(StrUtil.isNullOrEmpty(org.getOrgBizName())?o.getOrgName():org.getOrgBizName());
        String fullBizNameId = "";
        fullBizNameId = orgOrgtreeRelService.getFullBizOrgIdList(orgTree.getOrgTreeId().toString(),org.getSupOrgId().toString(),",");
        fullBizNameId=","+fullBizNameId+","+o.getOrgId()+",";

        Long orgOrgtreeRefId = orgOrgtreeRelService.getId();
        OrgOrgtreeRel orgOrgtreeRef = new OrgOrgtreeRel();
        orgOrgtreeRef.setOrgOrgtreeId(orgOrgtreeRefId);
        orgOrgtreeRef.setOrgId(org.getOrgId());
        orgOrgtreeRef.setOrgTreeId(orgTree.getOrgTreeId());
        orgOrgtreeRef.setStatusCd("1000");
        orgOrgtreeRef.setCreateUser(org.getUpdateUser());
        // TODO: 2019/1/23
        orgOrgtreeRef.setOrgBizName(StrUtil.isNullOrEmpty(org.getOrgBizName())?o.getOrgName():org.getOrgBizName());
        orgOrgtreeRef.setOrgBizFullName(fullBizName);
        orgOrgtreeRef.setOrgBizFullId(fullBizNameId);

        orgOrgtreeRelService.add(orgOrgtreeRef);
        modifyHistoryService.addModifyHistory(null,orgOrgtreeRef,org.getUpdateUser(),batchNumber);

        /*新增化小编码*/
        //新增营销组织扩展属性
        List<ExpandovalueVo> extValueList = org.getExpandovalueVoList();
        if(extValueList !=null && extValueList.size()>0){
            if(extValueList!=null && extValueList.size()>0){
                ResponseResult<ExpandovalueVo> publicRet = new ResponseResult<ExpandovalueVo>();
                for(ExpandovalueVo extVo : extValueList){
                    extVo.setTableName("TB_ORG");
                    extVo.setRecordId(org.getOrgId().toString());
                    publicRet = expandovalueService.addExpandoInfo(extVo);
                }
            }



            String orgMarkCodeRet = jdbcTemplate.execute(new ConnectionCallback<String>() {
                @Override
                public String doInConnection(Connection conn) throws SQLException, DataAccessException {
                    CallableStatement cstmt = null;
                    String result = "";
                    try {
                        cstmt = conn.prepareCall("{CALL P_ORG_CNTRT_MGMT_GENERATOR (?,?,?)}");
                        cstmt.setObject(1, org.getOrgCode());
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

        /* 新增组织类型**/
        TreeNodeVo vo = new TreeNodeVo();
        vo.setId(org.getOrgId().toString());
        vo.setPid(orgRefId.toString());
        vo.setName(o.getOrgName());

        // TODO: 2019/3/7 是否同步
        if(syncRet.isSync() &&
                syncRet.getSyncOrgTreeIds()!=null &&
                syncRet.getSyncOrgTreeIds().size()>0){
            orgService.freeOrgAddSync(syncRet.getSyncOrgTreeIds(),org,org.getUpdateUser(),batchNumber);
        }


        String mqmsg = "{\"type\":\"org\",\"handle\":\"update\",\"context\":{\"column\":\"orgId\",\"value\":"+org.getOrgId()+"}}" ;
        template.convertAndSend("message_sharing_center_queue",mqmsg);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("新增成功");
        ret.setData(vo);
        return ret;

    }


//    @ApiOperation(value = "删除组织关系信息", notes = "删除组织关系信息")
//    @ApiImplicitParams({
//    })
//    @UooLog(value = "删除组织关系信息", key = "deleteOrg")
//    @RequestMapping(value = "/deleteOrgRel", method = RequestMethod.POST)
//    @Transactional(rollbackFor = Exception.class)
//    public ResponseResult<String> deleteOrgRel(Org org){
//        ResponseResult<String> ret = new ResponseResult<String>();
//        if(StrUtil.isNullOrEmpty(org.getOrgTreeId())){
//            ret.setState(ResponseResult.STATE_ERROR);
//            ret.setMessage("组织树标识不能为空");
//            return ret;
//        }
//        if(StrUtil.isNullOrEmpty(org.getOrgRootId())){
//            ret.setState(ResponseResult.STATE_ERROR);
//            ret.setMessage("组织树根节点不能为空");
//            return ret;
//        }
//        //查询树下的组织关系
//       // List<OrgRelType> orgRelTypeList = orgRelService.getOrgRel(org.getOrgTreeId());
//        Wrapper orgWrapper = Condition.create().eq("ORG_ID",org.getOrgId()).eq("STATUS_CD","1000");
//        int count = orgRelService.selectCount(orgWrapper);
//        if(count > 0){
//            ret.setState(ResponseResult.STATE_ERROR);
//            ret.setMessage("组织关系存在");
//            return ret;
//        }
//        //组织组织类别
//        Wrapper orgTypeWrapper = Condition.create().eq("ORG_ID",org.getOrgId()).eq("STATUS_CD","1000");
//        List<OrgOrgtypeRel> orgTypeRefCurList = orgTypeRefService.selectList(orgTypeWrapper);
//        for(OrgOrgtypeRel orgTypeRef : orgTypeRefCurList){
//            orgTypeRefService.delete(orgTypeRef);
//        }
//
//        Wrapper positionWrapper = Condition.create().eq("ORG_ID",org.getOrgId()).eq("STATUS_CD","1000");
//        List<OrgPositionRel> orgPositionCurList = orgPositionRelService.selectList(positionWrapper);
//        for(OrgPositionRel orgPosition : orgPositionCurList){
//            orgPositionRelService.delete(orgPosition);
//        }
//        orgService.delete(org);
//        ret.setState(ResponseResult.STATE_OK);
//        ret.setMessage("删除成功");
//        return ret;
//    }

    @ApiOperation(value = "检索组织信息-web", notes = "检索组织信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "检索组织信息", key = "getFuzzyOrgRelPage")
    @RequestMapping(value = "/getFuzzyOrgRelPage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<OrgVo>> getFuzzyOrgRelPage(String search,
                                                          String orgRootId,
                                                          String orgTreeId,
                                                          Integer pageSize,
                                                          Integer pageNo,
                                                          Long userId,String accout) throws IOException {
        ResponseResult<Page<OrgVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgTreeId)){
            ret.setMessage("组织树标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        Wrapper orgtreeWrapper = Condition.create().eq("ORG_TREE_ID",orgTreeId)
                .eq("STATUS_CD","1000");
        OrgTree orgTree = orgTreeService.selectOne(orgtreeWrapper);
        if(orgTree==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不能为空");
            return ret;
        }
        OrgVo orgVo = new OrgVo();


        String orgParams = "";
        String orgOrgTypeParams = "";
        String orgOrgTreeRelParams = "";
        if(!StrUtil.isNullOrEmpty(accout)) {
            List<String> tabNames = new ArrayList<String>();
            tabNames.add("TB_ORG_TREE");
            tabNames.add("TB_ORG");
            tabNames.add("TB_ORG_REL");
            tabNames.add("TB_ORG_ORGTYPE_REL");
            tabNames.add("TB_ORG_ORGTREE_REL");
            List<SysDataRule> sdrList = commonSystemService.getSysDataRuleList(tabNames, accout);
            // TODO: 2019/2/20
            List<String> tabNamenews = new ArrayList<String>();
            tabNamenews.add("TB_ORG_ORGTREE_REL");
            List<SysDataRule> sdrList1 = commonSystemService.getSysDataRuleList(tabNamenews,accout,orgTreeId);
            if(sdrList1!=null && sdrList1.size()>0){
                orgOrgTreeRelParams = commonSystemService.getSysDataRuleParams("TB_ORG_ORGTREE_REL","ORG_BIZ_FULL_ID",sdrList);
                if(!StrUtil.isNullOrEmpty(orgOrgTreeRelParams)){
                    orgOrgTreeRelParams = commonSystemService.getOrgOrgTreeRelSql(orgOrgTreeRelParams,orgTreeId);
                }
                orgVo.setTabOrgOrgTreeRelParams(orgOrgTreeRelParams);
            }

            if(sdrList!=null && sdrList.size()>0){
                if(!commonSystemService.isOrgTreeAutho(orgTreeId,sdrList)){
                    ret.setState(ResponseResult.PARAMETER_ERROR);
                    ret.setMessage("无权限");
                    return ret;
                }
                orgParams = commonSystemService.getSysDataRuleSql("TB_ORG",sdrList);
                orgOrgTypeParams = commonSystemService.getSysDataRuleSql("TB_ORG_ORGTYPE_REL",sdrList);
//                orgOrgTreeRelParams = commonSystemService.getSysDataRuleParams("TB_ORG_ORGTREE_REL","ORG_BIZ_FULL_ID",sdrList);
//                if(!StrUtil.isNullOrEmpty(orgOrgTreeRelParams)){
//                    orgOrgTreeRelParams = commonSystemService.getOrgOrgTreeRelSql(orgOrgTreeRelParams,orgTreeId);
//                }
//                orgVo.setTabOrgOrgTreeRelParams(orgOrgTreeRelParams);

                orgVo.setTabOrgParams(orgParams);
                orgVo.setTabOrgOrgTypeParams(orgOrgTypeParams);
            }
        }
        orgVo.setOrgTreeId(new Long(orgTreeId));
        if(!StrUtil.isNullOrEmpty(search)){
            orgVo.setSearch(search);
            if(StrUtil.isNumeric(search)){
                orgVo.setIsSearchNum("1");
            }
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            orgVo.setPageSize(pageSize);
        }
        if(!StrUtil.isNullOrEmpty(pageNo)){
            orgVo.setPageNo(pageNo);
        }

        Page<OrgVo> page = orgRelService.selectFuzzyOrgRelPage(orgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(page);
        return ret;
    }


    @ApiOperation(value = "查询组织关系类型翻页-web", notes = "查询组织关系类型翻页")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织关系类型翻页", key = "getOrgRelTypePage")
    @RequestMapping(value = "/getOrgRelTypePage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<OrgRefTypeVo>> getOrgRelTypePage(String orgId,
                                                                Integer pageSize,
                                                                Integer pageNo,
                                                                Long userId,String accout
                                                                ){
        ResponseResult<Page<OrgRefTypeVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setMessage("组织标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        OrgRefTypeVo orgRefTypeVo = new OrgRefTypeVo();
        orgRefTypeVo.setOrgId(orgId);
        if(!StrUtil.isNullOrEmpty(pageNo)){
            orgRefTypeVo.setPageNo(pageNo);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            orgRefTypeVo.setPageSize(pageSize);
        }
        //获取权限
        String orgParams = "";
        String orgOrgTypeParams = "";
        String orgRelParams = "";
        if(!StrUtil.isNullOrEmpty(accout)) {
            List<String> tabNames = new ArrayList<String>();
            tabNames.add("TB_ORG_TREE");
            tabNames.add("TB_ORG");
            tabNames.add("TB_ORG_REL");
            tabNames.add("TB_ORG_ORGTYPE_REL");
            List<SysDataRule> sdrList = commonSystemService.getSysDataRuleList(tabNames, accout);
            if(sdrList!=null && sdrList.size()>0){
                orgParams = commonSystemService.getSysDataRuleSql("TB_ORG",sdrList);
                orgOrgTypeParams = commonSystemService.getSysDataRuleSql("TB_ORG_ORGTYPE_REL",sdrList);
                orgRelParams = commonSystemService.getSysDataRuleSql("TB_ORG_REL",sdrList);
                orgRefTypeVo.setTabOrgParams(orgParams);
                orgRefTypeVo.setTabOrgOrgTypeParams(orgOrgTypeParams);
                orgRefTypeVo.setTabOrgRelParams(orgRelParams);
            }
        }
        Page<OrgRefTypeVo> page = orgRelService.selectOrgRelTypePage(orgRefTypeVo);
        ret.setMessage("成功");
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(page);
        return ret;
    }


}

