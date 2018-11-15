package cn.ffcs.uoo.core.organization.controller;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.entity.*;
import cn.ffcs.uoo.core.organization.service.OrgRelTypeService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
//import com.alibaba.fastjson.JSON;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/orgRelType")
public class OrgRelTypeController extends BaseController {

    @Autowired
    private OrgRelTypeService orgRefTypeService;

    @ApiOperation(value = "查询组织关系类型列表", notes = "查询组织关系类型信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgTypeCode", value = "组织关系类型编码", required = true, dataType = "String", paramType = "path"),
     })
    @UooLog(value = "查询组织关系类型信息", key = "getOrgRelTypeList")
    @RequestMapping(value = "/getOrgRelTypeList", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<OrgRelType>> getOrgRelTypeList(String orgRelCode){
        ResponseResult<List<OrgRelType>> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgRelCode)){
            ret.setMessage("组织关系编码不能为空");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        Wrapper orgRefTypeWrapper = Condition.create()
                .eq("STATUS_CD","1000")
                .eq("REF_CODE",orgRelCode);
        OrgRelType orgRefType = orgRefTypeService.selectOne(orgRefTypeWrapper);
        if(orgRefType==null){
            ret.setMessage("组织关系类型不存在");
            ret.setState(ResponseResult.PARAMETER_ERROR);
            return ret;
        }
        Wrapper orgRefTypeListWrapper = Condition.create()
                .eq("STATUS_CD","1000")
                .eq("PARENT_ORG_REL_TYPE_ID",orgRefType.getOrgRelTypeId());
        List<OrgRelType> orgRefTypeList = orgRefTypeService.selectList(orgRefTypeListWrapper);

        ret.setState(ResponseResult.STATE_OK);
        ret.setData(orgRefTypeList);
        ret.setMessage("查询成功");
        return ret;
    }




//    @ApiOperation(value = "查询组织关系类型翻页", notes = "查询组织关系类型翻页")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "orgRefTypeId", value = "组织关系类型Id", required = true, dataType = "String", paramType = "path"),
//    })
//    @UooLog(value = "查询组织关系类型翻页", key = "queryOrgRefTypeList")
//    @RequestMapping(value = "/queryOrgRefTypePage", method = RequestMethod.GET)
//    @Transactional(rollbackFor = Exception.class)
//    public Page<String> getOrgRefTypePage(Long orgRefTypeId){
//
//        return ret;
//    }
}

