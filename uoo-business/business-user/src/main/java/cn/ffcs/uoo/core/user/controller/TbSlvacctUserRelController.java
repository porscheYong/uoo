package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import cn.ffcs.uoo.core.user.entity.TbSlvacctUserRel;
import cn.ffcs.uoo.core.user.entity.TbUser;
import cn.ffcs.uoo.core.user.service.TbSlaveAcctService;
import cn.ffcs.uoo.core.user.service.TbSlvacctUserRelService;
import cn.ffcs.uoo.core.user.util.EntityFillUtil;
import cn.ffcs.uoo.core.user.util.ResultUtils;
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
import cn.ffcs.uoo.base.controller.BaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@RestController
@RequestMapping("/tbSlvacctUserRel")
public class TbSlvacctUserRelController extends BaseController {

    @Autowired
    private TbSlaveAcctService tbSlaveAcctService;

    @Autowired
    private TbSlvacctUserRelService tbSlvacctUserRelService;

    @ApiOperation(value = "新增从账号用户使用关系", notes = "从账号用户使用关系新增")
    @ApiImplicitParam(name = "formSlvacctUserRelVo", value = "主账号信息", required = true, dataType = "FormSlvacctUserRelVo")
    @UooLog(value = "新增从账号用户使用关系", key = "addSlvacctUserRel")
    @RequestMapping(value = "/addSlvacctUserRel", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object saveAcct(@RequestBody FormSlvacctUserRelVo formSlvacctUserRelVo) {
        //账号唯一校验
        TbSlaveAcct tbSlaveAcct = formSlvacctUserRelVo.getTbSlaveAcct();
        Wrapper tbSlaveAcctPageWrapper = Condition.create().eq(true,"SLAVE_ACCT", tbSlaveAcct.getSlaveAcct());
        Page<TbSlaveAcct> tbSlaveAcctPage = tbSlaveAcctService.selectPage(new Page<TbSlaveAcct>(0, 12), tbSlaveAcctPageWrapper);
        if(tbSlaveAcctPage.getRecords().size() > 0){
            return ResultUtils.error(EumUserResponeCode.SLAVE_ACCT_IS_EXIST);
        }
        Long tbSlaveAcctId = tbSlaveAcctService.getId();
        tbSlaveAcct.setSlaveAcctId(tbSlaveAcctId);
        BeanUtils.copyProperties(EntityFillUtil.addEntity(), tbSlaveAcct);
        tbSlaveAcctService.insert(tbSlaveAcct);

        List<TbUser> tbUserList = formSlvacctUserRelVo.getTbUserList();
        if(tbUserList != null && tbUserList.size() > 0){
            for(TbUser tbUser : tbUserList){
                TbSlvacctUserRel tbSlvacctUserRel = new TbSlvacctUserRel();
                tbSlvacctUserRel.setSlaveAcctId(tbSlaveAcctId);
                tbSlvacctUserRel.setUserId(String.valueOf(tbUser.getUserId()));
                tbSlvacctUserRel.setSlvacctUserId(tbSlvacctUserRelService.getId());
                BeanUtils.copyProperties(EntityFillUtil.addEntity(), tbSlvacctUserRel);

                tbSlvacctUserRelService.insert(tbSlvacctUserRel);
            }
        }

        return ResultUtils.successfulTip(EumUserResponeCode.SLAVE_ACCT_ADD_SUCCESS);
    }

}

