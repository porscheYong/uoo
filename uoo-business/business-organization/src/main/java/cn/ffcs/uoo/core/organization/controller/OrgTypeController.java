package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.organization.entity.OrgOrgtypeRel;
import cn.ffcs.uoo.core.organization.entity.OrgType;
import cn.ffcs.uoo.core.organization.service.OrgTypeService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
@RequestMapping("/orgType")
@Api(value = "/orgType", description = "组织类别相关操作")
public class OrgTypeController {

    @Autowired
    private OrgTypeService orgTypeService;



    @ApiOperation(value = "查询组织类别列表信息", notes = "查询组织类别列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgTypeCode", value = "类型", required = true, dataType = "String", paramType = "path"),
    })
    @UooLog(value = "查询组织关系类型信息", key = "getOrgRefTypeList")
    @RequestMapping(value = "/getOrgTypeList", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<OrgType>> getOrgTypeList(String orgTypeCode){

        ResponseResult<List<OrgType>> ret = new ResponseResult<>();
        if(orgTypeCode == null || orgTypeCode == ""){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织类别编码不能为空");
        }

        Wrapper orgTypeWrapper = Condition.create()
                .eq("STATUS_CD","1000")
                .eq("ORG_TYPE_CODE",orgTypeCode);

        OrgType orgType = orgTypeService.selectOne(orgTypeWrapper);
        if(orgType==null){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织类别编码不存在");
        }
        Wrapper orgTypeListWrapper = Condition.create()
                .eq("STATUS_CD","1000")
                .eq("SUP_ORG_TYPE_ID",orgType.getOrgTypeId());

        List<OrgType> orgTypeList = orgTypeService.selectList(orgTypeListWrapper);
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(orgTypeList);
        ret.setMessage("查询成功");
        return ret;
    }



    @ApiOperation(value = "查询组织类别树信息-web", notes = "查询组织类别树信息")
    @ApiImplicitParams({

    })
    @UooLog(value = "查询组织关系类型信息", key = "getOrgTypeTree")
    @RequestMapping(value = "/getOrgTypeTree", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getOrgTypeTree(String id,String orgTypeCode){
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        ret.setState(ResponseResult.STATE_OK);
        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        ret.setData(orgTypeService.selectOrgTypeTree(id,orgTypeCode));
        ret.setMessage("成功");
        return ret;
    }


    @ApiOperation(value = "查询组织类别树全信息-web", notes = "查询组织类别树全信息")
    @ApiImplicitParams({

    })
    @UooLog(value = "查询组织类别树全信息", key = "getFullOrgTypeTree")
    @RequestMapping(value = "/getFullOrgTypeTree", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getFullOrgTypeTree(String id,String orgTypeCode,String orgId){
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        ret.setState(ResponseResult.STATE_OK);
        if (StrUtil.isNullOrEmpty(orgId)) {
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织id不能为空");
        }
        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        if(StrUtil.isNullOrEmpty(id)){
            ret.setData(orgTypeService.selectFullOrgTypeTreeByOrgId(id,orgTypeCode,orgId));
        }else{
            ret.setData(orgTypeService.selectOrgTypeTree(id,orgTypeCode));
        }

        ret.setMessage("成功");
        return ret;
    }

}

