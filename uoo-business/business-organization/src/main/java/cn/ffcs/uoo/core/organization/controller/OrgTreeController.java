package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.entity.*;
import cn.ffcs.uoo.core.organization.service.*;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.OrgRefTypeVo;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  组织树
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@RestController
@RequestMapping("/orgTree")
@Api(value = "/orgTree", description = "组织树相关操作")
public class OrgTreeController extends BaseController {


    @Autowired
    private OrgTreeService orgTreeService;

    @Autowired
    private OgtOrgtypeConfService ogtOrgtypeConfService;

    @Autowired
    private OgtOrgReltypeConfService ogtOrgReltypeConfService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private TreeStaffTypeRelService treeStaffTypeRelService;

    @Autowired
    private OrgRelService orgRelService;

    @Autowired
    private OrgLevelService orgLevelService;

    @Autowired
    private OrgOrgtreeRelService orgOrgtreeRelService;


    @Autowired
    private OrgRelTypeService orgRelTypeService;

    @Autowired
    private OrgTypeService orgTypeService;

    @Autowired
    private AmqpTemplate template;


    @ApiOperation(value = "新增组织树信息-web", notes = "新增组织树信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "新增组织树信息",key = "addOrgTree")
    @RequestMapping(value = "/addOrgTree",method = RequestMethod.POST)
    public ResponseResult<String> addOrgTree(@RequestBody OrgTree orgTree){
        ResponseResult<String> ret = new ResponseResult<>();
        String msg = orgTreeService.judgeOrgTreeParams(orgTree);
        if(!StrUtil.isNullOrEmpty(msg)){
            ret.setMessage(msg);
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }

        List<OrgRelType> orgRelTypeList = orgTree.getOrgRelTypeList();
        List<OrgType> orgTypeList = orgTree.getOrgTypeList();
        List<String> userTtypeList = orgTree.getUserTypeList();

        //组织节点
        List<TreeNodeVo> treeNodeList = orgTree.getTreeNodeList();

        Wrapper orgReltypeConfWrapper = Condition.create()
                .eq("REF_CODE",orgRelTypeList.get(0).getRefCode())
                .eq("STATUS_CD","1000");
        OrgRelType ort = orgRelTypeService.selectOne(orgReltypeConfWrapper);
        if(ort==null){
            ret.setMessage("组织关系类型不存在");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        //判断组织树组织关系是否存在
        if(orgTreeService.isExistsOrgTreeRel(orgRelTypeList.get(0).getRefCode())){
            ret.setMessage("组织关系类型已创建组织树，不能重复创建");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }

        Long orgId = orgService.getId();
        Org org = new Org();
        org.setOrgId(orgId);
        org.setOrgName(orgTree.getOrgTreeName());
        org.setFullName(orgTree.getOrgTreeName());
        org.setUuid(StrUtil.getUUID());
        org.setOrgCode(orgService.getGenerateOrgCode());
        orgService.add(org);

        OrgRel orgRelRoot = new OrgRel();
        Long orgRelIdRoot = orgRelService.getId();
        orgRelRoot.setOrgRelId(orgRelIdRoot);
        orgRelRoot.setOrgId(orgId);
        orgRelRoot.setRefCode(ort.getRefCode());
        orgRelRoot.setStatusCd("1000");
        orgRelService.add(orgRelRoot);

        Long orgTreeId = orgTreeService.getId();
        orgTree.setOrgTreeName(orgTree.getOrgTreeName());
        orgTree.setOrgTreeId(orgTreeId);
        orgTree.setOrgId(String.valueOf(orgId));
        orgTree.setOrgTreeType(orgTree.getOrgTreeType());
        orgTree.setSort(orgTree.getSort());
        orgTreeService.add(orgTree);


//        OgtOrgReltypeConf ogtOrgReftypeConf = new OgtOrgReltypeConf();
//        for(OrgRelType orgRelType : orgRelTypeList){
//            Long ogtOrgReftypeConfId = ogtOrgReltypeConfService.getId();
//            ogtOrgReftypeConf.setOrgTreeId(orgTreeId);
//            ogtOrgReftypeConf.setOrgRelTypeId(orgRelType.getOrgRelTypeId());
//            ogtOrgReftypeConf.setOgtOrgReltypeConfId(ogtOrgReftypeConfId);
//            ogtOrgReltypeConfService.add(ogtOrgReftypeConf);
//
//        }
        //        Wrapper orgRelTypeWrapper = Condition.create().eq("STATUS_CD","1000")
//                .eq("REF_CODE",orgRelTypeList.get(0).getRefCode());
//        OrgRelType orgtype = orgRelTypeService.selectOne(orgRelTypeWrapper);
//        if(orgtype==null){
//            ret.setMessage("组织关系类型不存在");
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            return ret;
//        }


        OgtOrgReltypeConf ogtOrgReftypeConf = new OgtOrgReltypeConf();
        Long ogtOrgReftypeConfId = ogtOrgReltypeConfService.getId();
        ogtOrgReftypeConf.setOrgTreeId(orgTreeId);
        ogtOrgReftypeConf.setOrgRelTypeId(ort.getOrgRelTypeId());
        ogtOrgReftypeConf.setOgtOrgReltypeConfId(ogtOrgReftypeConfId);
        ogtOrgReltypeConfService.add(ogtOrgReftypeConf);



        for(OrgType ot : orgTypeList){
            Long ogtOrgtypeConfId = ogtOrgtypeConfService.getId();
            OgtOrgtypeConf ogtOrgtypeConf = new OgtOrgtypeConf();
            ogtOrgtypeConf.setOrgTreeId(orgTreeId);
            ogtOrgtypeConf.setOrgTypeId(ot.getOrgTypeId());
            ogtOrgtypeConf.setOgtOrgtypeConfId(ogtOrgtypeConfId);
            ogtOrgtypeConfService.add(ogtOrgtypeConf);
        }

        //增加用工性质

//        Long treeStaffTypeRelId = treeStaffTypeRelService.getId();
//        TreeStaffTypeRel treeStaffTypeRel = new TreeStaffTypeRel();
//        treeStaffTypeRel.setTreeStaffTypeId(treeStaffTypeRelId);
//        treeStaffTypeRel.setOrgTreeId(orgTreeId);
//        treeStaffTypeRel.setUserTypeId(Long.valueOf(orgTree.getUserTypeId()));
//        treeStaffTypeRelService.add(treeStaffTypeRel);


        //新增编辑组织树组织关系
        if(treeNodeList!=null && treeNodeList.size()>0){
//            String tarOrgTreeId = orgTree.getTarOrgTreeId();
//            OrgTree tarOrgTree = orgTreeService.selectById(tarOrgTreeId);
//
            for(TreeNodeVo vo : treeNodeList){
                OrgRel orgRel = new OrgRel();
                Long orgRefId = orgRelService.getId();
                orgRel.setOrgRelId(orgRefId);
                orgRel.setOrgId(new Long(vo.getId()));
                if(!StrUtil.isNullOrEmpty(vo.getPid())){
                    orgRel.setParentOrgId(new Long(vo.getPid()));
                }else{
                    orgRel.setParentOrgId(orgId);
                }
                orgRel.setRefCode(ort.getRefCode());
                orgRel.setStatusCd("1000");
                orgRelService.add(orgRel);

                //新增组织层级
                Long  orgLevelId = orgLevelService.getId();
                OrgLevel orgLevel = new OrgLevel();
                orgLevel.setOrgLevelId(orgLevelId);
                orgLevel.setOrgId(new Long(vo.getId()));
                orgLevel.setOrgLevel(Integer.valueOf(vo.getLevel()));
                orgLevel.setOrgTreeId(orgTreeId);
                orgLevel.setStatusCd("1000");
                orgLevelService.add(orgLevel);

                //组织组织树关系
                Long orgOrgtreeRefId = orgOrgtreeRelService.getId();
                OrgOrgtreeRel orgOrgtreeRef = new OrgOrgtreeRel();
                orgOrgtreeRef.setOrgOrgtreeId(orgOrgtreeRefId);
                orgOrgtreeRef.setOrgId(new Long(vo.getId()));
                orgOrgtreeRef.setOrgTreeId(orgTreeId);
                orgOrgtreeRef.setStatusCd("1000");
                orgOrgtreeRelService.add(orgOrgtreeRef);

                String mqmsg = "{\"type\":\"org\",\"handle\":\"update\",\"context\":{\"column\":\"orgId\",\"value\":"+vo.getId()+"}}" ;
                template.convertAndSend("message_sharing_center_queue",mqmsg);
            }
        }


        ret.setMessage("成功");
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }


    @ApiOperation(value = "修改组织树组织树信息-web", notes = "修改组织树组织树信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgTree", value = "组织树信息", required = true, dataType = "OrgTree")
    })
    @UooLog(value = "修改组织树组织树信息",key = "updateOrgTree")
    @RequestMapping(value = "/updateOrgTree",method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> updateOrgTree(@RequestBody OrgTree orgTree){
        ResponseResult<String> ret = new ResponseResult<>();
        String msg = orgTreeService.judgeOrgTreeParams(orgTree);
        if(!StrUtil.isNullOrEmpty(msg)){
            ret.setMessage(msg);
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }

        List<OrgRelType> orgRelTypeList = orgTree.getOrgRelTypeList();
        List<OrgType> orgTypeList = orgTree.getOrgTypeList();
        List<String> userTypeList = orgTree.getUserTypeList();

        if(StrUtil.isNullOrEmpty(orgTree.getOrgTreeId())){
            ret.setMessage("组织树标识不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        OrgTree otree = orgTreeService.selectById(orgTree.getOrgTreeId());
        if(StrUtil.isNullOrEmpty(otree)){
            ret.setMessage("组织树不存在");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        boolean isExists = false;
        //组织树组织关系类型
//        Wrapper ogtOrgReftypeConfWrapper = Condition.create().eq("STATUS_CD","1000")
//                .eq("ORG_TREE_ID",orgTree.getOrgTreeId());
//        List<OgtOrgReltypeConf> ogtOrgReftypeConfCurList
//                =  ogtOrgReltypeConfService.selectList(ogtOrgReftypeConfWrapper);


        List<OrgRelType> orgRelTypeListCur = new ArrayList<OrgRelType>();
        orgRelTypeListCur = orgRelTypeService.getOrgRelType(orgTree.getOrgTreeId().toString());
        //组织树组织关系类型 不更新
        if(orgRelTypeListCur!=null && orgRelTypeListCur.size()>0){
            OrgRelType ort = orgRelTypeList.get(0);
            if(!ort.getRefCode().equals(orgRelTypeListCur.get(0).getRefCode())){
                ret.setMessage("组织树关系类型不能修改");
                ret.setState(ResponseResult.PARAMETER_ERROR);
                return ret;
            }
        }

//        for(OrgRelType orgRelType : orgRelTypeList){
//            isExists = false;
//            for(OrgRelType orgRt : orgRelTypeListCur){
//                if(orgRt.getRefCode().equals(orgRelType.getRefCode())){
//                    isExists = true;
//                    break;
//                }
//            }
//            if(!isExists){
//                Long ogtOrgReftypeConfId = ogtOrgReltypeConfService.getId();
//                OgtOrgReltypeConf ogtOrgReftypeConf = new OgtOrgReltypeConf();
//                ogtOrgReftypeConf.setOrgTreeId(orgTree.getOrgTreeId());
//                ogtOrgReftypeConf.setOrgRelTypeId(orgRelType.getOrgRelTypeId());
//                ogtOrgReftypeConf.setOgtOrgReltypeConfId(ogtOrgReftypeConfId);
//                ogtOrgReltypeConfService.add(ogtOrgReftypeConf);
//            }
//        }
//        for(OrgRelType orgRelT : orgRelTypeListCur){
//            isExists = false;
//            for(OrgRelType orgRelType : orgRelTypeList){
//                if(orgRelT.getRefCode().equals(orgRelType.getRefCode())){
//                    isExists = true;
//                    break;
//                }
//            }
//            if(!isExists){
//                Wrapper ogtOrgReftypeConfWrapper = Condition.create()
//                        .eq("STATUS_CD","1000")
//                        .eq("ORG_TREE_ID",orgTree.getOrgTreeId())
//                        .eq("ORG_REL_TYPE_ID",orgRelT.getOrgRelTypeId());
//                ogtOrgReltypeConfService.delete(
//                        ogtOrgReltypeConfService.selectOne(ogtOrgReftypeConfWrapper));
//            }
//        }

        //组织树组织分类
        Wrapper ogtOrgtypeConfWrapper = Condition.create().eq("STATUS_CD","1000")
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId());
        List<OgtOrgtypeConf> OgtOrgtypeConfCurList = ogtOrgtypeConfService.selectList(ogtOrgtypeConfWrapper);

        for(OrgType ot : orgTypeList){
            isExists = false;
            for(OgtOrgtypeConf ogtOrgtypeConf : OgtOrgtypeConfCurList){
                if(ot.getOrgTypeId().longValue() == ogtOrgtypeConf.getOrgTypeId().longValue()){
                    isExists = true;
                    break;
                }
            }
            if(!isExists){
                Long ogtOrgtypeConfId = ogtOrgtypeConfService.getId();
                OgtOrgtypeConf ogtOrgtypeConf = new OgtOrgtypeConf();
                ogtOrgtypeConf.setOrgTreeId(orgTree.getOrgTreeId());
                ogtOrgtypeConf.setOrgTypeId(ot.getOrgTypeId());
                ogtOrgtypeConf.setOgtOrgtypeConfId(ogtOrgtypeConfId);
                ogtOrgtypeConfService.add(ogtOrgtypeConf);
            }
        }
        for(OgtOrgtypeConf ogtOrgtypeConf : OgtOrgtypeConfCurList){
            isExists = false;
            for(OrgType ot : orgTypeList){
                if(ot.getOrgTypeId().longValue() == ogtOrgtypeConf.getOrgTypeId().longValue()){
                    isExists = true;
                    break;
                }
            }
            if(!isExists){
                ogtOrgtypeConfService.delete(ogtOrgtypeConf);
            }
        }
        //用工性质
        Wrapper treeStaffRelWrapper = Condition.create().eq("STATUS_CD","1000")
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId());
        TreeStaffTypeRel treeStaffTypeRelCur = treeStaffTypeRelService.selectOne(treeStaffRelWrapper);
        if(treeStaffTypeRelCur!=null){
            treeStaffTypeRelCur.setUserTypeId(new Long(orgTree.getUserTypeId()));
            treeStaffTypeRelService.update(treeStaffTypeRelCur);
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("更新成功");
        return ret;
    }


    @ApiOperation(value = "查询组织树信息", notes = "查询组织树信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织树信息",key = "getOrgTree")
    @RequestMapping(value = "/getOrgTree",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<OrgTree>  getOrgTree(String orgTreeId){
        ResponseResult<OrgTree> ret = new ResponseResult<OrgTree>();
        if(StrUtil.isNullOrEmpty(orgTreeId)){
            ret.setMessage("组织树标识不存在");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        Wrapper orgTreeWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgTreeId)
                .eq("STATUS_CD","1000");
        OrgTree orgTree = orgTreeService.selectOne(orgTreeWrapper);
        if(StrUtil.isNullOrEmpty(orgTree)){
            ret.setMessage("组织树不存在");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        Wrapper userTypeWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgTreeId)
                .eq("STATUS_CD","1000");
        TreeStaffTypeRel userType =  treeStaffTypeRelService.selectOne(userTypeWrapper);
        if(userType!=null){
            orgTree.setUserTypeId(userType.getUserTypeId().toString());
        }

        List<OrgRelType> orgRelTypeList = new ArrayList<OrgRelType>();
        orgRelTypeList = orgRelTypeService.getOrgRelType(orgTree.getOrgTreeId().toString());
        orgTree.setOrgRelTypeList(orgRelTypeList);
        if(orgRelTypeList!=null && orgRelTypeList.size()>0){
            orgTree.setRefCode(orgRelTypeList.get(0).getRefCode());
        }
        List<OrgType> orgTypeList = orgTypeService.getOrgTypeByOrgTreeId(orgTree.getOrgTreeId());
        orgTree.setOrgTypeList(orgTypeList);


        ret.setData(orgTree);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        return ret;
    }


    @ApiOperation(value = "查询组织树列表-web", notes = "查询组织树列表")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织树列表",key = "getOrgTreeList")
    @RequestMapping(value = "/getOrgTreeList",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<OrgTree>>  getOrgTreeList(String orgTreeId,String orgRootId){
        ResponseResult<List<OrgTree>> ret = new ResponseResult<List<OrgTree>>();
        Wrapper orgTreeWrapper = Condition.create().eq("STATUS_CD","1000").orderBy("SORT");
        if(!StrUtil.isNullOrEmpty(orgTreeId)){
            orgTreeWrapper.eq("ORG_TREE_ID",orgTreeId);
        }
        List<OrgTree> orgTreeList = orgTreeService.selectList(orgTreeWrapper);
        ret.setData(orgTreeList);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }


}

