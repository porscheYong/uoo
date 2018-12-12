package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.service.OrgContactRelService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@RestController
@RequestMapping("/orgContactRel")
@Api(value = "/orgContactRel", description = "组织联系人相关操作")
public class OrgContactRelController extends BaseController {

    @Autowired
    private OrgContactRelService orgContactRelService;

    @ApiOperation(value = "获取组织联系人-web", notes = "获取组织联系人")
    @ApiImplicitParams({
    })
    @UooLog(value = "获取组织联系人", key = "getOrgContactPage")
    @RequestMapping(value = "/getOrgContactPage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Page<PsonOrgVo>> getOrgContactPage(String orgId,
                                                             Integer pageSize,
                                                             Integer pageNo) throws IOException {
        ResponseResult<Page<PsonOrgVo>> ret = new ResponseResult<>();
        PsonOrgVo psonOrgVo = new PsonOrgVo();
        psonOrgVo.setOrgId(new Long(orgId));
        if(!StrUtil.isNullOrEmpty(pageNo)){
            psonOrgVo.setPageNo(pageNo);
        }
        if(!StrUtil.isNullOrEmpty(pageSize)){
            psonOrgVo.setPageSize(pageSize);
        }
        Page<PsonOrgVo> page = orgContactRelService.selectOrgContactPage(psonOrgVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(page);
        return ret;
    }
}

