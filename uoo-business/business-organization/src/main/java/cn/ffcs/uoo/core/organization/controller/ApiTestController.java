package cn.ffcs.uoo.core.organization.controller;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-22
 */

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.organization.Api.service.TestService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/22
 */
public class ApiTestController {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "test-web", notes = "test")
    @ApiImplicitParams({
    })
    @UooLog(value = "test",key = "test")
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public ResponseResult<Void> test(String orgId){
        ResponseResult<Void> ret = new ResponseResult<Void>();
        List<PsonOrgVo> psonOrgVo = new ArrayList<PsonOrgVo>();
        ResponseResult<String> obj = testService.addOrgPsn(psonOrgVo);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }
}
