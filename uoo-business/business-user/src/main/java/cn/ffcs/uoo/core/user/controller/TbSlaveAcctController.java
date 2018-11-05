package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.ListUser;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import cn.ffcs.uoo.core.user.service.TbAcctExtService;
import cn.ffcs.uoo.core.user.service.TbSlaveAcctService;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.vo.ApplySlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.FormSlvacctAcctRelVo;
import cn.ffcs.uoo.core.user.vo.ResourceSlaveAcctVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 从账号 前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-10-29
 */
@RestController
@RequestMapping("/tbSlaveAcct")
public class TbSlaveAcctController extends BaseController {

    @Autowired
    private TbSlaveAcctService tbSlaveAcctService;

    @Autowired
    private TbAcctExtService tbAcctExtService;

    @ApiOperation(value = "从账号信息查看", notes = "从账号信息查看")
    @ApiImplicitParam(name = "slaveAcctId", value = "从账号标识", required = true, dataType = "Integer",paramType="path")
    @UooLog(value = "从账号信息查看", key = "getSlaveAcct")
    @RequestMapping(value = "/getSlaveAcct", method = RequestMethod.GET)
    public Object saveAcct(String slaveAcctId) {

        TbSlaveAcct tbSlaveAcct = tbSlaveAcctService.selectById(slaveAcctId);
        // 账号类型 资源
        if("1".equals(tbSlaveAcct.getSlaveAcctType())){
            // 从账号
            ResourceSlaveAcctVo resourceSlaveAcctVo = new ResourceSlaveAcctVo();
            BeanUtils.copyProperties(tbSlaveAcct, resourceSlaveAcctVo);
            //系统名称  todo

            //从账号关联用户信息
            List<ListUser> userList = tbSlaveAcctService.getUserList(tbSlaveAcct.getSlaveAcctId());
            resourceSlaveAcctVo.setUserList(userList);
            return ResultUtils.success(resourceSlaveAcctVo);

        }else if("2".equals(tbSlaveAcct.getSlaveAcctType())){
            // 从账号
            ApplySlaveAcctVo applySlaveAcctVo = new ApplySlaveAcctVo();
            BeanUtils.copyProperties(tbSlaveAcct, applySlaveAcctVo);
            //系统名称 todo

            //扩展信息
            TbAcctExt tbAcctExt = tbAcctExtService.selectOne(new EntityWrapper<TbAcctExt>().eq("SLAVE_ACCT_ID", tbSlaveAcct.getSlaveAcctId()));
            applySlaveAcctVo.setTbAcctExt(tbAcctExt);

            //从账号关联用户信息
            List<ListUser> userList = tbSlaveAcctService.getApplyUserList(tbSlaveAcct.getSlaveAcctId());
            applySlaveAcctVo.setUser(userList.get(0));

            //关联主账号
            List<TbAcct> tbAcctList = tbSlaveAcctService.getAcct(tbSlaveAcct.getSlaveAcctId());
            applySlaveAcctVo.setTbAcct(tbAcctList.get(0));
            return ResultUtils.success(applySlaveAcctVo);

            //归属组织 todo

        }
        return ResultUtils.error(EumUserResponeCode.SLAVE_ACCT_NO_EXITST);
    }

}

