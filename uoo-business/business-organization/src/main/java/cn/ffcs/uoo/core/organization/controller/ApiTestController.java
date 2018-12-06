package cn.ffcs.uoo.core.organization.controller;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-22
 */

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.organization.Api.service.ExpandovalueService;
import cn.ffcs.uoo.core.organization.Api.service.TestService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.ExpandovalueVo;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import cn.ffcs.uoo.core.organization.vo.TbExpandovalue;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/ApiTestController")
public class ApiTestController {

    @Autowired
    private TestService testService;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ExpandovalueService expandovalueService;

    @ApiOperation(value = "test-web", notes = "test")
    @ApiImplicitParams({
    })
    @UooLog(value = "test",key = "test")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseResult<Void> test(String orgId){
        ResponseResult<Void> ret = new ResponseResult<Void>();
        List<PsonOrgVo> psonOrgVo = new ArrayList<PsonOrgVo>();
        //新增
//        ExpandovalueVo vo = new ExpandovalueVo();
//        vo.setTableName("TB_ORG");
//        vo.setColumnName("nodeType");
//        vo.setRecordId("1000000001");
//        vo.setData("AA");
//        ResponseResult<ExpandovalueVo> voret = expandovalueService.addExpandoInfo(vo);
//        ResponseResult<String> obj = testService.addOrgPsn(psonOrgVo);

        //更新
        ResponseResult<TbExpandovalue> voret = expandovalueService.removeTbExpandovalue(2709887L,0L);


       ResponseResult<List<ExpandovalueVo>> list = expandovalueService.queryExpandovalueVoList("TB_ORG","1000000001");
       ret.setState(ResponseResult.STATE_OK);
       return ret;
    }
}
