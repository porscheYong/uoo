package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import cn.ffcs.uoo.core.user.service.RabbitMqService;
import cn.ffcs.uoo.core.user.service.TbAcctExtService;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.EditFormSlaveAcctVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 主账号扩展 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@RestController
@RequestMapping("/tbAcctExt")
public class TbAcctExtController extends BaseController {

    @Autowired
    private TbAcctExtService tbAcctExtService;
    @Autowired
    private RabbitMqService rabbitMqService;

    @ApiOperation(value = "新增修改从账号扩展信息", notes = "新增修改从账号扩展信息")
    @ApiImplicitParam(name = "tbAcctExt", value = "从账号扩展信息", required = true, dataType = "TbAcctExt")
    @UooLog(value = "新增修改从账号扩展信息", key = "addOrUpdateTbAcctExt")
    @RequestMapping(value = "/addOrUpdateTbAcctExt", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object addOrUpdateTbAcctExt(@RequestBody TbAcctExt tbAcctExt){
        tbAcctExtService.saveTbAcctExt(tbAcctExt);
        rabbitMqService.sendMqMsg("person", "update", "slaveAcctId", tbAcctExt.getSlaveAcctId());
        return ResultUtils.success(null);
    }

    @ApiOperation(value = "删除从账号扩展信息", notes = "删除从账号扩展信息")
    @ApiImplicitParam(name = "tbAcctExt", value = "从账号扩展信息", required = true, dataType = "TbAcctExt")
    @UooLog(value = "删除从账号扩展信息", key = "delTbAcctExt")
    @RequestMapping(value = "/delTbAcctExt", method = RequestMethod.DELETE)
    public Object delTbAcctExt(TbAcctExt tbAcctExt){
        tbAcctExtService.delTbAcctExt(tbAcctExt.getSlaveAcctId(), tbAcctExt.getUpdateUser());
        rabbitMqService.sendMqMsg("person", "update", "slaveAcctId", tbAcctExt.getSlaveAcctId());
        return ResultUtils.success(null);
    }


}

