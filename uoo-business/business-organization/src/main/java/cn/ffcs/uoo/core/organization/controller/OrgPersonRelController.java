package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.entity.*;
import cn.ffcs.uoo.core.organization.service.*;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.OrgRefTypeVo;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import cn.ffcs.uoo.core.organization.vo.SysDataRule;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@RestController
@RequestMapping("/orgPersonRel")
public class OrgPersonRelController extends BaseController {

    @Autowired
    private OrgPersonRelService orgPersonRelService;

    @Autowired
    private OrgtreeOrgpersonRelService orgtreeOrgpersonRelService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgRelService orgRelService;

    @Autowired
    private OrgTreeService orgTreeService;

    @Autowired
    private OrgLevelService orgLevelService;

    @Autowired
    private OrgContactRelService orgContactRelService;

    @Autowired
    private AmqpTemplate template;


    @Autowired
    private CommonSystemService commonSystemService;

    @Autowired
    private ModifyHistoryService modifyHistoryService;

    @ApiOperation(value = "新增组织人员关系-web" , notes = "新增组织人员")
    @ApiImplicitParams({
    })
    @UooLog(value = "新增组织人员关系", key = "addOrgPsn")
    @RequestMapping(value = "/addOrgPsn", method = RequestMethod.POST)
    public ResponseResult<String> addOrgPsn(@RequestBody List<PsonOrgVo> psonOrgList){
        System.out.println(new Date());
        ResponseResult<String> ret = new ResponseResult<String>();

        if(psonOrgList!=null){
            String batchNumber = modifyHistoryService.getBatchNumber();
            for(PsonOrgVo psonOrgVo : psonOrgList){
                Wrapper orgTreeConfWrapper = Condition.create()
                        .eq("ORG_TREE_ID",psonOrgVo.getOrgTreeId())
                        .eq("STATUS_CD","1000");
                OrgTree orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
                if(orgtree==null){
                    ret.setState(ResponseResult.PARAMETER_ERROR);
                    ret.setMessage("组织树不存在");
                    return ret;
                }

                OrgPersonRel orgPersonRel = orgPersonRelService.convertObj(psonOrgVo);
                Long orgPsndocRefId = orgPersonRelService.getId();
                orgPersonRel.setOrgPersonId(orgPsndocRefId);
                orgPersonRel.setOrgTreeId(psonOrgVo.getOrgTreeId().toString());
                orgPersonRel.setCreateUser(psonOrgVo.getSysUserId());
                orgPersonRelService.add(orgPersonRel);
                modifyHistoryService.addModifyHistory(null,orgPersonRel,psonOrgVo.getSysUserId(),batchNumber);


                Long orgTreePerId = orgtreeOrgpersonRelService.getId();
                OrgtreeOrgpersonRel orgtreeOrgpersonRel = new OrgtreeOrgpersonRel();
                orgtreeOrgpersonRel.setOrgTreeId(orgtree.getOrgTreeId());
                orgtreeOrgpersonRel.setOrgPersonId(orgPsndocRefId);
                orgtreeOrgpersonRel.setOrgtreeOrgpersonId(orgTreePerId);
                orgtreeOrgpersonRel.setStatusCd("1000");
                orgtreeOrgpersonRel.setCreateUser(psonOrgVo.getSysUserId());
                orgtreeOrgpersonRelService.add(orgtreeOrgpersonRel);
                modifyHistoryService.addModifyHistory(null,orgtreeOrgpersonRel,psonOrgVo.getSysUserId(),batchNumber);

            }
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }

    @ApiOperation(value = "新增组织人员关系2-web" , notes = "新增组织人员2")
    @ApiImplicitParams({
    })
    @UooLog(value = "新增组织人员关系2", key = "addOrgPsn2")
    @RequestMapping(value = "/addOrgPsn2", method = RequestMethod.POST)
    public ResponseResult<String> addOrgPsn2(@RequestBody List<PsonOrgVo> psonOrgList){
        System.out.println(new Date());
        ResponseResult<String> ret = new ResponseResult<String>();
        if(psonOrgList!=null){
            String batchNumber = modifyHistoryService.getBatchNumber();
            for(PsonOrgVo psonOrgVo : psonOrgList){
                Wrapper orgTreeConfWrapper = Condition.create()
                        .eq("ORG_TREE_ID",psonOrgVo.getOrgTreeId())
                        .eq("STATUS_CD","1000");
                OrgTree orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
                if(orgtree==null){
                    ret.setState(ResponseResult.PARAMETER_ERROR);
                    ret.setMessage("组织树不存在");
                    return ret;
                }

                OrgPersonRel orgPersonRel = orgPersonRelService.convertObj(psonOrgVo);
                Long orgPsndocRefId = orgPersonRelService.getId();
                orgPersonRel.setOrgPersonId(orgPsndocRefId);
                orgPersonRel.setOrgTreeId(psonOrgVo.getOrgTreeId().toString());
                orgPersonRel.setCreateUser(psonOrgVo.getSysUserId());
                orgPersonRelService.add(orgPersonRel);
                modifyHistoryService.addModifyHistory(null,orgPersonRel,psonOrgVo.getSysUserId(),batchNumber);


                Long orgTreePerId = orgtreeOrgpersonRelService.getId();
                OrgtreeOrgpersonRel orgtreeOrgpersonRel = new OrgtreeOrgpersonRel();
                orgtreeOrgpersonRel.setOrgTreeId(orgtree.getOrgTreeId());
                orgtreeOrgpersonRel.setOrgPersonId(orgPsndocRefId);
                orgtreeOrgpersonRel.setOrgtreeOrgpersonId(orgTreePerId);
                orgtreeOrgpersonRel.setStatusCd("1000");
                orgtreeOrgpersonRel.setCreateUser(psonOrgVo.getSysUserId());
                orgtreeOrgpersonRelService.add(orgtreeOrgpersonRel);
                modifyHistoryService.addModifyHistory(null,orgtreeOrgpersonRel,psonOrgVo.getSysUserId(),batchNumber);
            }
            String mqmsg = "{\"type\":\"person\",\"handle\":\"update\",\"context\":{\"column\":\"personnelId\",\"value\":"+psonOrgList.get(0).getPersonnelId()+"}}" ;
            template.convertAndSend("message_sharing_center_queue",mqmsg);
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }


    @ApiOperation(value = "修改组织人员-web", notes = "修改组织人员")
    @ApiImplicitParams({
             })
    @UooLog(value = "修改组织人员", key = "updateOrgPsn")
    @RequestMapping(value = "/updateOrgPsn", method = RequestMethod.POST)
    public ResponseResult<String> updateOrgPsn(@RequestBody PsonOrgVo psonOrgVo){
        System.out.println(new Date());
        ResponseResult<String> ret = new ResponseResult<String>();
        Long personnelId = psonOrgVo.getPersonnelId();
        if(StrUtil.isNullOrEmpty(personnelId)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("组织人员标识不能为空");
            return ret;
        }
        Wrapper orgTreeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",psonOrgVo.getOrgTreeId())
                .eq("STATUS_CD","1000");
        OrgTree orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgtree==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }

        List<OrgPersonRel> orgPersonRelList = orgPersonRelService.getOrgPsnByOrgAndPsnId(
                                                    psonOrgVo.getOrgTreeId().toString(),
                                                    personnelId.toString(),
                                                    psonOrgVo.getOrgId().toString());
        if(orgPersonRelList!=null && orgPersonRelList.size()>0){
            String batchNumber = modifyHistoryService.getBatchNumber();
            OrgPersonRel orgPersonRel = orgPersonRelList.get(0);
            OrgPersonRel orgPersonRelOLd = new OrgPersonRel();
            BeanUtils.copyProperties(orgPersonRel,orgPersonRelOLd);
            orgPersonRel.setDoubleName(StrUtil.strnull(psonOrgVo.getDoubleName()));
            orgPersonRel.setProperty(StrUtil.strnull(psonOrgVo.getProperty()));
            if(!StrUtil.isNullOrEmpty(psonOrgVo.getPostId())){
                orgPersonRel.setPostId(new Long(psonOrgVo.getPostId()));
            }
            if(!StrUtil.isNullOrEmpty(psonOrgVo.getSort())){
                orgPersonRel.setSort(new Double(psonOrgVo.getSort()));
            }
            orgPersonRel.setUpdateUser(psonOrgVo.getSysUserId());
            orgPersonRelService.update(orgPersonRel);
            modifyHistoryService.addModifyHistory(orgPersonRelOLd,orgPersonRel,psonOrgVo.getSysUserId(),batchNumber);
        }
        String mqmsg = "{\"type\":\"person\",\"handle\":\"update\",\"context\":{\"column\":\"personnelId\",\"value\":"+personnelId+"}}" ;
        template.convertAndSend("message_sharing_center_queue",mqmsg);

        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("更新成功");
        return ret;
    }

    @ApiOperation(value = "删除组织人员关系", notes = "删除组织人员关系")
    @ApiImplicitParams({
              })
    @UooLog(value = "删除组织人员关系", key = "deleteOrgPsn")
    @RequestMapping(value = "/deleteOrgPsn", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> deleteOrgPsn(@RequestBody PsonOrgVo psonOrgVo){
        System.out.println(new Date());
        ResponseResult<String> ret = new ResponseResult<String>();

        Long orgPsndocRefId = psonOrgVo.getPersonnelId();
        if(StrUtil.isNullOrEmpty(orgPsndocRefId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织人员标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(psonOrgVo.getOrgTreeId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(psonOrgVo.getOrgId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        Wrapper orgTreeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",psonOrgVo.getOrgTreeId())
                .eq("STATUS_CD","1000");
        OrgTree orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
        if(orgtree==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树不存在");
            return ret;
        }
        Wrapper orgPerConfWrapper = Condition.create()
                .eq("PERSONNEL_ID",orgPsndocRefId)
                .eq("ORG_ID",psonOrgVo.getOrgId())
                .eq("STATUS_CD","1000");
        List<OrgPersonRel> orgPersonRelList = orgPersonRelService.selectList(orgPerConfWrapper);
        if(orgPersonRelList != null && orgPersonRelList.size()>0){
            String batchNumber = modifyHistoryService.getBatchNumber();
            for(OrgPersonRel opr : orgPersonRelList){
                Wrapper orgTreePerConfWrapper = Condition.create()
                        .eq("ORG_PERSON_ID",opr.getOrgPersonId())
                        .eq("STATUS_CD","1000")
                        .eq("ORG_TREE_ID",orgtree.getOrgTreeId());
                OrgtreeOrgpersonRel orgtreeOrgpersonRel = orgtreeOrgpersonRelService.selectOne(orgTreePerConfWrapper);
                if(orgtreeOrgpersonRel!=null){
                    orgtreeOrgpersonRel.setUpdateUser(psonOrgVo.getSysUserId());
                    orgtreeOrgpersonRelService.delete(orgtreeOrgpersonRel);
                    modifyHistoryService.addModifyHistory(orgtreeOrgpersonRel,null,psonOrgVo.getSysUserId(),batchNumber);
                    opr.setUpdateUser(psonOrgVo.getSysUserId());
                    orgPersonRelService.delete(opr);
                    modifyHistoryService.addModifyHistory(opr,null,psonOrgVo.getSysUserId(),batchNumber);
                    break;
                }
            }
        }
        String mqmsg = "{\"type\":\"person\",\"handle\":\"update\",\"context\":{\"column\":\"personnelId\",\"value\":"+orgPsndocRefId+"}}" ;
        template.convertAndSend("message_sharing_center_queue",mqmsg);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }



    @ApiOperation(value = "删除人员所有相关信息", notes = "删除人员所有相关信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "删除人员所有相关信息", key = "deletePsnRel")
    @RequestMapping(value = "/deletePsnRel", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> deletePsnRel(@RequestBody PsonOrgVo psonOrgVo){
        ResponseResult<String> ret = new ResponseResult<String>();
        Long orgPsndocRefId = psonOrgVo.getPersonnelId();
        if(StrUtil.isNullOrEmpty(orgPsndocRefId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织人员标识不能为空");
            return ret;
        }
        String batchNumber = modifyHistoryService.getBatchNumber();
        Wrapper orgPerConfWrapper = Condition.create()
                .eq("PERSONNEL_ID",orgPsndocRefId)
                .eq("STATUS_CD","1000");
        List<OrgPersonRel> orgPersonRelList = orgPersonRelService.selectList(orgPerConfWrapper);
        if(orgPersonRelList != null && orgPersonRelList.size()>0){
            for(OrgPersonRel opr : orgPersonRelList){
                Wrapper orgTreePerConfWrapper = Condition.create()
                        .eq("ORG_PERSON_ID",opr.getOrgPersonId())
                        .eq("STATUS_CD","1000");
                OrgtreeOrgpersonRel orgtreeOrgpersonRel = orgtreeOrgpersonRelService.selectOne(orgTreePerConfWrapper);
                if(orgtreeOrgpersonRel!=null){
                    orgtreeOrgpersonRel.setUpdateUser(psonOrgVo.getSysUserId());
                    orgtreeOrgpersonRelService.delete(orgtreeOrgpersonRel);
                    modifyHistoryService.addModifyHistory(orgtreeOrgpersonRel,null,psonOrgVo.getSysUserId(),batchNumber);
                }
                opr.setUpdateUser(psonOrgVo.getSysUserId());
                orgPersonRelService.delete(opr);
                modifyHistoryService.addModifyHistory(opr,null,psonOrgVo.getSysUserId(),batchNumber);
            }
        }

        Wrapper orgContRelWrapper = Condition.create()
                .eq("PERSONNEL_ID",orgPsndocRefId)
                .eq("STATUS_CD","1000");
        List<OrgContactRel> orgContactRels = orgContactRelService.selectList(orgContRelWrapper);
        for(OrgContactRel ocr : orgContactRels){
            orgContactRelService.delete(ocr);
            modifyHistoryService.addModifyHistory(ocr,null,psonOrgVo.getSysUserId(),batchNumber);
        }
        String mqmsg = "{\"type\":\"person\",\"handle\":\"update\",\"context\":{\"column\":\"personnelId\",\"value\":"+orgPsndocRefId+"}}" ;
        template.convertAndSend("message_sharing_center_queue",mqmsg);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }


    @ApiOperation(value = "查询组织人员信息", notes = "查询组织人员信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织人员信息", key = "getOrgPsn")
    @RequestMapping(value = "/getOrgPsn", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<PsonOrgVo>> getFuzzyOrgPsnPage(PsonOrgVo psonOrgVo){
        ResponseResult<List<PsonOrgVo>> ret = new ResponseResult<List<PsonOrgVo>>();
        Page<PsonOrgVo> page = orgPersonRelService.selectFuzzyOrgPsnPage(psonOrgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }





    @ApiOperation(value = "查询人员组织信息列表", notes = "查询人员组织信息列表")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询人员组织信息列表",key = "getPerOrgRelList")
    @RequestMapping(value = "/getPerOrgRelList",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<PsonOrgVo>> getPerOrgRelList(String orgTreeId,String personnelId,
                                                            Long userId,String accout){
        ResponseResult<List<PsonOrgVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(personnelId)){
            ret.setMessage("人员标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        PsonOrgVo psonOrgVo = new PsonOrgVo();
        psonOrgVo.setPersonnelId(new Long(personnelId));
        if(!StrUtil.isNullOrEmpty(orgTreeId)){
            psonOrgVo.setOrgTreeId(new Long(orgTreeId));
        }
        List<PsonOrgVo> psonList = orgPersonRelService.getPerOrgRelList(psonOrgVo);
        if(psonList==null || psonList.size()<0){
            ret.setMessage("人员组织关系不存在");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(psonList);
        return ret;
    }


    @ApiOperation(value = "查询人员组织信息翻页-web", notes = "查询人员组织信息翻页")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询人员组织信息翻页",key = "getPerOrgRelPage")
    @RequestMapping(value = "/getPerOrgRelPage",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<PsonOrgVo>> getPerOrgRelPage(String orgId,
                                                            String orgTreeId,
                                                            String refCode,
                                                            String orgRootId,
                                                            String personnelId,
                                                            String isSearchlower,
                                                            String search,
                                                            String sortField,
                                                            String sortOrder,
                                                            Integer pageSize,
                                                            Integer pageNo,
                                                            Long userId,
                                                            String accout
                                                            ){
        ResponseResult<Page<PsonOrgVo>> ret = new ResponseResult<>();

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
            ret.setMessage("组织标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }

        if(StrUtil.isNullOrEmpty(orgTreeId) && StrUtil.isNullOrEmpty(refCode)){
            ret.setMessage("组织树标识和组织树关系编码不能同时为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }

        OrgTree orgtree = null;
        if(!StrUtil.isNullOrEmpty(refCode)){
            orgtree = orgTreeService.getOrgTreeByRefCode(refCode);
            if (orgtree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }else {
            //获取组织树
            Wrapper orgTreeConfWrapper = Condition.create()
                    .eq("ORG_TREE_ID", orgTreeId)
                    .eq("STATUS_CD", "1000");
            orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
            if (orgtree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }


        PsonOrgVo psonOrgVo = new PsonOrgVo();
        //获取权限
        if(!StrUtil.isNullOrEmpty(accout)) {
            List<String> tabNames = new ArrayList<String>();
            tabNames.add("TB_ORG_TREE");
            tabNames.add("TB_ORG");
            tabNames.add("TB_ACCOUNT_ORG_REL");
            tabNames.add("TB_ORG_ORGTYPE_REL");
            tabNames.add("TB_ORG_PERSON_REL");
            List<SysDataRule> sdrList = commonSystemService.getSysDataRuleList(tabNames, accout);
            if(sdrList!=null && sdrList.size()>0){
                if(!commonSystemService.isOrgTreeAutho(orgTreeId,sdrList)){
                    ret.setState(ResponseResult.PARAMETER_ERROR);
                    ret.setMessage("树无权限");
                    return ret;
                }
                String orgParams = commonSystemService.getSysDataRuleSql("TB_ORG",sdrList);
                psonOrgVo.setTabOrgParams(orgParams);
                String orgPerParams =  commonSystemService.getSysDataRuleSql("tbOrgPersonRel","TB_ORG_PERSON_REL",sdrList);
                psonOrgVo.setTabOrgPerRelParams(orgPerParams);
                String orgOrgTypeParams = commonSystemService.getSysDataRuleSql("TB_ORG_ORGTYPE_REL",sdrList);
                psonOrgVo.setTabOrgOrgTypeParams(orgOrgTypeParams);
            }
        }


        psonOrgVo.setIsSearchlower(StrUtil.isNullOrEmpty(isSearchlower)?"0":isSearchlower);
        psonOrgVo.setOrgId(new Long(orgId));
        //psonOrgVo.setOrgRootId(new Long(orgRootId));
        psonOrgVo.setOrgTreeId(orgtree.getOrgTreeId());
        psonOrgVo.setSortField(StrUtil.strnull(sortField));
        psonOrgVo.setSortOrder(StrUtil.strnull(sortOrder));
        if(!StrUtil.isNullOrEmpty(personnelId)){
            psonOrgVo.setPersonnelId(new Long(personnelId));
        }

        if(!StrUtil.isNullOrEmpty(search)){
            psonOrgVo.setSearch(search);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            psonOrgVo.setPageSize(pageSize);
        }
        if(!StrUtil.isNullOrEmpty(pageNo)){
            psonOrgVo.setPageNo(pageNo);
        }

        Wrapper orgLevelConfWrapper = Condition.create()
                .eq("ORG_TREE_ID", psonOrgVo.getOrgTreeId())
                .eq("ORG_ID",psonOrgVo.getOrgId())
                .eq("STATUS_CD", "1000");
        OrgLevel orgLev = orgLevelService.selectOne(orgLevelConfWrapper);
        if(orgLev==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树层级不存在");
            return ret;
        }
        Page<PsonOrgVo> page = null;
        if(orgLev.getOrgLevel()<3 && "1".equals(psonOrgVo.getIsSearchlower())){
            //查全部
            page = orgPersonRelService.selectAllPerOrgRelPage(psonOrgVo);
        }else{
            //查部分
            page = orgPersonRelService.selectPerOrgRelPage(psonOrgVo);
        }

        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(page);
        return ret;
    }


    @ApiOperation(value = "查询人员-web", notes = "查询人员")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询人员",key = "getPerOrOrgRelPage")
    @RequestMapping(value = "/getPerOrOrgRelPage",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<PsonOrgVo>> getPerOrOrgRelPage(PsonOrgVo psonOrgVo){
        ResponseResult<Page<PsonOrgVo>> ret = new ResponseResult<>();
        Page<PsonOrgVo> page = orgPersonRelService.selectPerOrgRelPage(psonOrgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(page);
        return ret;
    }





    @ApiOperation(value = "查询用户-web", notes = "查询用户")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询用户",key = "getUserOrgRelPage")
    @RequestMapping(value = "/getUserOrgRelPage",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<PsonOrgVo>> getUserOrgRelPage(String orgId,
                                                             String orgTreeId,
                                                             String refCode,
                                                             String isSearchlower,
                                                             String search,
                                                             Integer pageSize,
                                                             Integer pageNo,
                                                             Long userId,
                                                             String accout
                                                             ){
        ResponseResult<Page<PsonOrgVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgTreeId) && StrUtil.isNullOrEmpty(refCode)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识和组织树关系类型不能同时为空");
            return ret;
        }
        OrgTree orgtree = null;
        if(!StrUtil.isNullOrEmpty(refCode)){
            orgtree = orgTreeService.getOrgTreeByRefCode(refCode);
            if (orgtree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }else {
            //获取组织树
            Wrapper orgTreeConfWrapper = Condition.create()
                    .eq("ORG_TREE_ID", orgTreeId)
                    .eq("STATUS_CD", "1000");
            orgtree = orgTreeService.selectOne(orgTreeConfWrapper);
            if (orgtree == null) {
                ret.setState(ResponseResult.PARAMETER_ERROR);
                ret.setMessage("组织树不存在");
                return ret;
            }
        }


        PsonOrgVo psonOrgVo = new PsonOrgVo();

        //获取权限
        if(!StrUtil.isNullOrEmpty(accout)) {
            List<String> tabNames = new ArrayList<String>();
            tabNames.add("TB_ORG_TREE");
            tabNames.add("TB_ORG");
            tabNames.add("TB_ORG_PERSON_REL");
            tabNames.add("TB_ACCOUNT_ORG_REL");
            tabNames.add("TB_ORG_ORGTYPE_REL");
            List<SysDataRule> sdrList = commonSystemService.getSysDataRuleList(tabNames, accout);
            if(sdrList!=null && sdrList.size()>0){
                if(!commonSystemService.isOrgTreeAutho(orgTreeId,sdrList)){
                    ret.setState(ResponseResult.PARAMETER_ERROR);
                    ret.setMessage("树无权限");
                    return ret;
                }
                String orgParams = commonSystemService.getSysDataRuleSql("TB_ORG",sdrList);
                psonOrgVo.setTabOrgParams(orgParams);
                String orgPerParams =  commonSystemService.getSysDataRuleSql("TB_ORG_PERSON_REL",sdrList);
                psonOrgVo.setTabOrgPerRelParams(orgPerParams);
                String orgOrgTypeParams = commonSystemService.getSysDataRuleSql("TB_ORG_ORGTYPE_REL",sdrList);
                psonOrgVo.setTabOrgOrgTypeParams(orgOrgTypeParams);
                String orgAccountRelParams = commonSystemService.getSysDataRuleSql("tbAccountOrgRel","TB_ACCOUNT_ORG_REL",sdrList);
                psonOrgVo.setTabAccountOrgRelParams(orgAccountRelParams);
            }
        }


        psonOrgVo.setIsSearchlower(StrUtil.isNullOrEmpty(isSearchlower)?"0":isSearchlower);
        psonOrgVo.setOrgId(new Long(orgId));
        psonOrgVo.setOrgTreeId(orgtree.getOrgTreeId());
        if(!StrUtil.isNullOrEmpty(search)){
            psonOrgVo.setSearch(search);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            psonOrgVo.setPageSize(pageSize);
        }
        if(!StrUtil.isNullOrEmpty(pageNo)){
            psonOrgVo.setPageNo(pageNo);
        }


        Wrapper orgLevelConfWrapper = Condition.create()
                .eq("ORG_TREE_ID", psonOrgVo.getOrgTreeId())
                .eq("ORG_ID",psonOrgVo.getOrgId())
                .eq("STATUS_CD", "1000");
        OrgLevel orgLev = orgLevelService.selectOne(orgLevelConfWrapper);
        if(orgLev==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树层级不存在");
            return ret;
        }

        Wrapper orgLevel2ConfWrapper = Condition.create()
                .eq("ORG_TREE_ID", psonOrgVo.getOrgTreeId())
                .eq("ORG_LEVEL",orgLev.getOrgLevel())
                .eq("STATUS_CD", "1000");
        List<OrgLevel> orgLevList = orgLevelService.selectList(orgLevel2ConfWrapper);

        Page<PsonOrgVo> page = null;
        if(orgLev.getOrgLevel()<3 && orgLevList.size()==1 && "1".equals(psonOrgVo.getIsSearchlower())){
            page = orgPersonRelService.selectAllUserOrgRelPage(psonOrgVo);
        }else{
            page = orgPersonRelService.selectUserOrgRelPage(psonOrgVo);
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(page);
        return ret;
    }

    @ApiOperation(value = "查询用户归属信息-web", notes = "查询用户归属信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询用户归属信息",key = "getPerOrgAttribution")
    @RequestMapping(value = "/getPerOrgAttribution",method = RequestMethod.GET)
    public ResponseResult<Page<PsonOrgVo>> getPerOrgAttribution(String orgId,
                                                                String orgTreeId,
                                                                String personnelId,
                                                                Integer pageSize,
                                                                Integer pageNo){
        ResponseResult<Page<PsonOrgVo>> ret = new ResponseResult<Page<PsonOrgVo>>();
        if(!StrUtil.isNullOrEmpty(orgId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if(!StrUtil.isNullOrEmpty(personnelId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("人员标识不能为空");
            return ret;
        }
        if(!StrUtil.isNullOrEmpty(personnelId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("人员标识不能为空");
            return ret;
        }
        return ret;
    }

}

