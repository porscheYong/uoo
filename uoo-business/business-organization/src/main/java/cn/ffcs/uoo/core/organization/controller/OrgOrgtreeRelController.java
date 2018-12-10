package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.entity.OrgOrgtreeRel;
import cn.ffcs.uoo.core.organization.entity.OrgPostRel;
import cn.ffcs.uoo.core.organization.service.OrgOrgtreeRelService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@Controller
@RequestMapping("/orgOrgtreeRel")
@Api(value = "/orgOrgtreeRel", description = "组织组织树关系相关操作")
public class OrgOrgtreeRelController extends BaseController {


    private OrgOrgtreeRelService orgOrgtreeRelService;

    @ApiOperation(value = "修改组织组织树信息", notes = "修改组织组织树信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "修改组织组织树称谓", key = "updateOrgOrgTreeRel")
    @RequestMapping(value = "/updateOrgOrgTreeRel", method = RequestMethod.POST)
    public ResponseResult<String> updateOrgOrgTreeRel(OrgOrgtreeRel orgOrgTreeRel){
        ResponseResult<String> ret = new ResponseResult<>();
        if(StrUtil.isNullOrEmpty(orgOrgTreeRel.getOrgTreeId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织树标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgOrgTreeRel.getOrgId())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织标识不能为空");
            return ret;
        }
        if(StrUtil.isNullOrEmpty(orgOrgTreeRel.getOrgBizName())){
            ret.setState(ResponseResult.PARAMETER_ERROR);
            ret.setMessage("组织专业称谓不能为空");
            return ret;
        }

        Wrapper orgTreeRelWrapper = Condition.create()
                .eq("ORG_TREE_ID",orgOrgTreeRel.getOrgTreeId())
                .eq("ORG_ID",orgOrgTreeRel.getOrgId())
                .eq("STATUS_CD","1000");
        List<OrgOrgtreeRel> orgOrgTreeRelList = orgOrgtreeRelService.selectList(orgTreeRelWrapper);
        if(orgOrgTreeRelList!=null && orgOrgTreeRelList.size()>0){
            OrgOrgtreeRel orgTreeRel = orgOrgTreeRelList.get(0);
            orgTreeRel.setOrgBizName(orgOrgTreeRel.getOrgBizName());
            orgOrgtreeRelService.update(orgTreeRel);
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("修改成功");
        return ret;
    }
}

