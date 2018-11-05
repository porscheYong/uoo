package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.*;
import cn.ffcs.uoo.core.user.service.TbAcctExtService;
import cn.ffcs.uoo.core.user.service.TbSlaveAcctService;
import cn.ffcs.uoo.core.user.service.TbSlvacctAcctRelService;
import cn.ffcs.uoo.core.user.service.TbSlvacctUserRelService;
import cn.ffcs.uoo.core.user.util.EntityFillUtil;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.FormSlvacctAcctRelVo;
import cn.ffcs.uoo.core.user.vo.FormSlvacctUserRelVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 从账号主账号关系 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@RestController
@RequestMapping("/tbSlvacctAcctRel")
public class TbSlvacctAcctRelController extends BaseController {

    @Autowired
    private TbSlaveAcctService tbSlaveAcctService;

    @Autowired
    private TbAcctExtService tbAcctExtService;

    @Autowired
    private TbSlvacctAcctRelService tbSlvacctAcctRelService;

    @ApiOperation(value = "新增从账号主账号关系", notes = "从账号主账号关系新增")
    @ApiImplicitParam(name = "formSlvacctAcctRelVo", value = "主账号信息", required = true, dataType = "FormSlvacctAcctRelVo")
    @UooLog(value = "新增从账号主账号关系", key = "addSlvacctRel")
    @RequestMapping(value = "/addSlvacctRel", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object saveAcct(@RequestBody FormSlvacctAcctRelVo formSlvacctAcctRelVo) {
        //账号唯一校验
        TbSlaveAcct tbSlaveAcct = formSlvacctAcctRelVo.getTbSlaveAcct();
        Wrapper tbSlaveAcctPageWrapper = Condition.create().eq(true,"SLAVE_ACCT", tbSlaveAcct.getSlaveAcct());
        Page<TbSlaveAcct> tbSlaveAcctPage = tbSlaveAcctService.selectPage(new Page<TbSlaveAcct>(0, 12), tbSlaveAcctPageWrapper);
        if(tbSlaveAcctPage.getRecords().size() > 0){
            return ResultUtils.error(EumUserResponeCode.SLAVE_ACCT_IS_EXIST);
        }
        Long tbSlaveAcctId = tbSlaveAcctService.getId();
        tbSlaveAcct.setSlaveAcctId(tbSlaveAcctId);
        BeanUtils.copyProperties(EntityFillUtil.addEntity(), tbSlaveAcct);
        tbSlaveAcctService.insert(tbSlaveAcct);

        //   2.创建从账号扩展
        if(!StrUtil.isNullOrEmpty(formSlvacctAcctRelVo.getTbAcctExt())){
            TbAcctExt tbAcctExt = formSlvacctAcctRelVo.getTbAcctExt();
            tbAcctExt.setAcctExtId(tbAcctExtService.getId());
            tbAcctExt.setSlaveAcctId(tbSlaveAcctId);
            BeanUtils.copyProperties(EntityFillUtil.addEntity(), tbAcctExt);
            tbAcctExtService.insert(tbAcctExt);
        }
        //  3、从账号主账号关系
        TbSlvacctAcctRel tbSlvacctAcctRel = new TbSlvacctAcctRel();
        tbSlvacctAcctRel.setAcctId(formSlvacctAcctRelVo.getAcctId());
        tbSlvacctAcctRel.setSlaveAcctId(tbSlaveAcct.getSlaveAcctId());
        tbSlvacctAcctRel.setSlvacctAcctId(tbSlvacctAcctRelService.getId());
        BeanUtils.copyProperties(EntityFillUtil.addEntity(), tbSlvacctAcctRel);
        tbSlvacctAcctRelService.insert(tbSlvacctAcctRel);

        return ResultUtils.successfulTip(EumUserResponeCode.SLAVE_ACCT_ADD_SUCCESS);
    }

}

