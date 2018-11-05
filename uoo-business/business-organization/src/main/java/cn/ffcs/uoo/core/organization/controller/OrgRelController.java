package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
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
import io.swagger.models.auth.In;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
@Api(value = "/org", description = "组织关系相关操作")
public class OrgRelController {


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
    private SolrService solrService;

    @ApiOperation(value = "查询组织树信息-web", notes = "查询组织树信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "relCode", value = "组织关系类型", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orgRootId", value = "根节点标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "isOpen", value = "是否展开", required = true, dataType = "boolean"),
            @ApiImplicitParam(name = "isAsync", value = "是否异步", required = true, dataType = "boolean"),
            @ApiImplicitParam(name = "isRoot", value = "是否是根节点", required = true, dataType = "boolean"),
            @ApiImplicitParam(name = "id", value = "父节点", required = true, dataType = "String"),

    })
    @UooLog(value = "查询组织树", key = "getOrgRelTree")
    @RequestMapping(value = "/getOrgRelTree", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getOrgRelTree(String id, String orgRootId,String relCode, boolean isOpen,
                                                boolean isAsync, boolean isRoot) throws IOException {
        //System.out.println(new Date());
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgRootId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("根节点标识不能为空");
            return ret;
        }
        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        treeNodeVos = orgRelService.queryOrgTree(orgRootId,relCode,id,isRoot);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("组织树查询成功");
        ret.setData(treeNodeVos);
        return ret;
    }

    @ApiOperation(value = "重构组织树获取-web", notes = "重构组织树获取")
    @ApiImplicitParams({
    })
    @UooLog(value = "重构组织树获取", key = "getOrgRelTree")
    @RequestMapping(value = "/getRestructOrgRelTree", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(String id,String orgRootId,boolean isFull) throws IOException {
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(id)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgRootId)){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树根节点不能为空");
            return ret;
        }
        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        treeNodeVos = orgRelService.selectFuzzyOrgRelTree(id,orgRootId,isFull);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("组织树查询成功");
        ret.setData(treeNodeVos);
        return ret;
    }



    @ApiOperation(value = "新增组织关系-web", notes = "新增组织关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "org", value = "组织", required = true, dataType = "Org"),
    })
    @UooLog(value = "新增组织关系", key = "addOrgRel")
    @RequestMapping(value = "/addOrgRel", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> addOrgRel(Org org) throws IOException {
        ResponseResult<String> ret = new ResponseResult<String>();
        if(StrUtil.isNullOrEmpty(org.getOrgId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        //查询组织树
        Wrapper orgtreeWrapper = Condition.create().eq("ORG_ID",org.getOrgRootId())
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

//        List<OrgRel> orgRefList = orgRelService.selectOrgRel(org);
//        if(orgRefList != null || orgRefList.size() > 0){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织已经下挂该组织树");
//            return ret;
//        }

        Wrapper ogtOrgReftypeConfWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
                .eq("STATUS_CD","1000");

        List<OgtOrgReltypeConf> ogtOrgReftypeConfList =  ogtOrgReftypeConfService.selectList(ogtOrgReftypeConfWrapper);
        if(ogtOrgReftypeConfList == null || ogtOrgReftypeConfList.size() < 0){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织关系类型不存在");
            return ret;
        }
        OgtOrgReltypeConf ogtOrgReftypeConf = ogtOrgReftypeConfList.get(0);
        //新增组织关系
        OrgRel orgRel = new OrgRel();
        Long orgRefId = orgRelService.getId();
        orgRel.setOrgRelId(orgRefId);
        orgRel.setOrgId(org.getOrgId());
        orgRel.setSupOrgId(org.getSupOrgId());
        orgRel.setOrgRelTypeId(ogtOrgReftypeConf.getOrgRelTypeId());
        orgRel.setStatusCd("1000");
        orgRel.insert();

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
            orgLevel.setOrgTreeId(org.getOrgTreeId());
            orgLevel.setStatusCd("1000");
            orgLevel.insert();
        }

        //组织组织树关系
        Long orgOrgtreeRefId = orgOrgtreeRelService.getId();
        OrgOrgtreeRel orgOrgtreeRef = new OrgOrgtreeRel();
        orgOrgtreeRef.setOrgOrgtreeId(orgOrgtreeRefId);
        orgOrgtreeRef.setOrgId(org.getOrgId());
        orgOrgtreeRef.setOrgTreeId(org.getOrgTreeId());
        orgOrgtreeRef.setStatusCd("1000");
        orgOrgtreeRef.insert();

        SolrInputDocument input = new SolrInputDocument();
        input.addField("id", orgRefId);
        input.addField("orgId", org.getOrgId());
        input.addField("orgCode", org.getOrgCode());
        input.addField("orgRelTypeId", ogtOrgReftypeConf.getOrgRelTypeId());
        input.addField("orgName", org.getOrgName());
        //获取系统路径
        String sysfullName = orgService.getSysFullName(org.getOrgRootId().toString(),org.getSupOrgId().toString());
        sysfullName = sysfullName+"/"+org.getOrgName();
        input.addField("fullName",sysfullName);
        solrService.addDataIntoSolr("org",input);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("新增成功");
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
    @UooLog(value = "检索组织信息", key = "getFuzzyOrgRel")
    @RequestMapping(value = "/getFuzzyOrgRelPage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<OrgVo>> getFuzzyOrgRelPage(OrgVo orgVo) throws IOException {
        ResponseResult<Page<OrgVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgVo.getSearch())){
            ret.setMessage("检索信息不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgVo.getOrgRootId())){
            ret.setMessage("根节点组织标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        Page<OrgVo> page = orgRelService.selectFuzzyOrgRelPage(orgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(page);
        return ret;
    }





//
//    @ApiOperation(value = "查询组织关系类型", notes = "查询组织关系类型")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "orgId", value = "组织Id", required = true, dataType = "String")
//    })
//    @UooLog(value = "查询组织关系类型", key = "getOrgRelType")
//    @RequestMapping(value = "/getOrgRelType", method = RequestMethod.POST)
//    @Transactional(rollbackFor = Exception.class)
//    public ResponseResult<List<OrgRefTypeVo>> getOrgRelType(Long orgId) throws IOException {
//        ResponseResult<List<OrgRefTypeVo>> ret = new ResponseResult<>();
//        Org org = new Org();
//        org.setOrgId(orgId);
//        List<OrgRefTypeVo> orfList = orgRelService.getOrgRelType(org);
//        ret.setData(orfList);
//        ret.setState(ResponseResult.STATE_OK);
//        ret.setMessage("成功");
//        return ret;
//    }



    @ApiOperation(value = "查询组织关系类型翻页-web", notes = "查询组织关系类型翻页")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织关系类型翻页", key = "getOrgRelTypePage")
    @RequestMapping(value = "/getOrgRelTypePage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<OrgRefTypeVo>> getOrgRelTypePage(OrgRefTypeVo orgRefTypeVo) throws IOException {
        ResponseResult<Page<OrgRefTypeVo>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgRefTypeVo)){
            ret.setMessage("参数不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgRefTypeVo.getOrgId())){
            ret.setMessage("组织标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        Page<OrgRefTypeVo> page = orgRelService.selectOrgRelTypePage(orgRefTypeVo);
        ret.setMessage("成功");
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(page);
        return ret;
    }


}

