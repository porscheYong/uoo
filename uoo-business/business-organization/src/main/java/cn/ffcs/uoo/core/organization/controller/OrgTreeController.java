package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.core.organization.entity.*;
import cn.ffcs.uoo.core.organization.service.*;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.OrgRefTypeVo;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@Api(value = "/org", description = "组织树相关操作")
public class OrgTreeController {


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

    @ApiOperation(value = "新增组织树信息-web", notes = "新增组织树信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "orgTree", value = "组织树信息", required = true, dataType = "OrgTree")
//    })
    @UooLog(value = "新增组织树信息",key = "addOrgTree")
    @RequestMapping(value = "/addOrgTree",method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<String> addOrgTree(OrgTree orgTree){
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
        Long orgId = orgService.getId();
        Org org = new Org();
        org.setOrgId(orgId);
        org.setOrgName(orgTree.getOrgTreeName());
        org.setFullName(orgTree.getOrgTreeName());
        org.setUuid(StrUtil.getUUID());
        org.setOrgCode(orgService.getGenerateOrgCode());
        orgService.add(org);

        Long orgTreeId = orgTreeService.getId();
        orgTree.setOrgTreeId(orgTreeId);
        orgTree.setOrgId(String.valueOf(orgId));
        orgTree.setOrgTreeType(orgTree.getOrgTreeType());
        orgTree.setSort(orgTree.getSort());
        orgTreeService.add(orgTree);

        for(OrgRelType orgRelType : orgRelTypeList){
            Long ogtOrgReftypeConfId = ogtOrgReltypeConfService.getId();
            OgtOrgReltypeConf ogtOrgReftypeConf = new OgtOrgReltypeConf();
            ogtOrgReftypeConf.setOrgTreeId(orgTreeId);
            ogtOrgReftypeConf.setOrgRelTypeId(orgRelType.getOrgRelTypeId());
            ogtOrgReftypeConf.setOgtOrgReltypeConfId(ogtOrgReftypeConfId);
            ogtOrgReltypeConfService.add(ogtOrgReftypeConf);

        }

        for(OrgType ot : orgTypeList){
            Long ogtOrgtypeConfId = ogtOrgtypeConfService.getId();
            OgtOrgtypeConf ogtOrgtypeConf = new OgtOrgtypeConf();
            ogtOrgtypeConf.setOrgTreeId(orgTreeId);
            ogtOrgtypeConf.setOrgTypeId(ot.getOrgTypeId());
            ogtOrgtypeConf.setOgtOrgtypeConfId(ogtOrgtypeConfId);
            ogtOrgtypeConfService.add(ogtOrgtypeConf);
        }

        //增加用工性质
        for(String userTypeId : userTtypeList){
            Long treeStaffTypeRelId = treeStaffTypeRelService.getId();
            TreeStaffTypeRel treeStaffTypeRel = new TreeStaffTypeRel();
            treeStaffTypeRel.setTreeStaffTypeId(treeStaffTypeRelId);
            treeStaffTypeRel.setOrgTreeId(orgTreeId);
            treeStaffTypeRel.setUserTypeId(Long.valueOf(userTypeId));
            treeStaffTypeRelService.add(treeStaffTypeRel);
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
    public ResponseResult<String> updateOrgTree(OrgTree orgTree){
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
        Wrapper ogtOrgReftypeConfWrapper = Condition.create().eq("STATUS_CD","1000")
                .eq("ORG_TREE_ID",orgTree.getOrgTreeId());
        List<OgtOrgReltypeConf> ogtOrgReftypeConfCurList
                =  ogtOrgReltypeConfService.selectList(ogtOrgReftypeConfWrapper);

        for(OrgRelType orgRelType : orgRelTypeList){
            isExists = false;
            for(OgtOrgReltypeConf ogtOrgRelTypeConf : ogtOrgReftypeConfCurList){
                if(ogtOrgRelTypeConf.getOrgRelTypeId().longValue()
                        == orgRelType.getOrgRelTypeId().longValue()){
                    isExists = true;
                    break;
                }
            }
            if(!isExists){
                Long ogtOrgReftypeConfId = ogtOrgReltypeConfService.getId();
                OgtOrgReltypeConf ogtOrgReftypeConf = new OgtOrgReltypeConf();
                ogtOrgReftypeConf.setOrgTreeId(orgTree.getOrgTreeId());
                ogtOrgReftypeConf.setOrgRelTypeId(orgRelType.getOrgRelTypeId());
                ogtOrgReftypeConf.setOgtOrgReltypeConfId(ogtOrgReftypeConfId);
                ogtOrgReltypeConfService.add(ogtOrgReftypeConf);
            }
        }
        for(OgtOrgReltypeConf ogtOrgRelTypeConf : ogtOrgReftypeConfCurList){
            isExists = false;
            for(OrgRelType orgRelType : orgRelTypeList){
                if(ogtOrgRelTypeConf.getOrgRelTypeId().longValue()
                        == orgRelType.getOrgRelTypeId().longValue()){
                    isExists = true;
                    break;
                }
            }
            if(!isExists){
                ogtOrgReltypeConfService.delete(ogtOrgRelTypeConf);
            }
        }

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
        List<TreeStaffTypeRel> treeStaffTypeRelCurList = treeStaffTypeRelService.selectList(treeStaffRelWrapper);

        for(String userTypeId : userTypeList){
            isExists = false;
            for(TreeStaffTypeRel tst : treeStaffTypeRelCurList){
                if(userTypeId.equals(StrUtil.strnull(tst.getUserTypeId()))){
                    isExists = true;
                    break;
                }
            }
            if(!isExists){
                Long treeStaffTypeRelId = treeStaffTypeRelService.getId();
                TreeStaffTypeRel treeStaffTypeRel = new TreeStaffTypeRel();
                treeStaffTypeRel.setTreeStaffTypeId(treeStaffTypeRelId);
                treeStaffTypeRel.setOrgTreeId(otree.getOrgTreeId());
                treeStaffTypeRel.setUserTypeId(Long.valueOf(userTypeId));
                treeStaffTypeRelService.add(treeStaffTypeRel);
            }
        }

        for(TreeStaffTypeRel tst : treeStaffTypeRelCurList){
            isExists = false;
            for(String userTypeId : userTypeList){
                if(userTypeId.equals(StrUtil.strnull(tst.getUserTypeId()))){
                    isExists = true;
                    break;
                }
            }
            if(!isExists){
                treeStaffTypeRelService.delete(tst);
            }
        }
        orgTree.updateById();
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("更新成功");
        return ret;
    }



    @ApiOperation(value = "查询组织树列表-web", notes = "查询组织树列表")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织树列表",key = "getOrgTreeList")
    @RequestMapping(value = "/getOrgTreeList",method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<OrgTree>>  getOrgTreeList(OrgTree orgTree){
        ResponseResult<List<OrgTree>> ret = new ResponseResult<List<OrgTree>>();
        Wrapper orgTreeWrapper = Condition.create().eq("STATUS_CD","1000").orderBy("SORT");
        if(orgTree != null){
            if(!StrUtil.isNullOrEmpty(orgTree.getOrgId())){
                orgTreeWrapper.eq("ORG_ID",orgTree.getOrgId());
            }
        }
        List<OrgTree> orgTreeList = orgTreeService.selectList(orgTreeWrapper);
        ret.setData(orgTreeList);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }
}

