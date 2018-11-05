package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import cn.ffcs.uoo.core.user.entity.TbAcctOrg;
import cn.ffcs.uoo.core.user.entity.TbUser;
import cn.ffcs.uoo.core.user.service.TbAcctExtService;
import cn.ffcs.uoo.core.user.service.TbAcctOrgService;
import cn.ffcs.uoo.core.user.service.TbAcctService;
import cn.ffcs.uoo.core.user.service.TbUserService;
import cn.ffcs.uoo.core.user.util.*;
import cn.ffcs.uoo.core.user.vo.FormAcctVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 主账号 前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-10-25
 */
@Api(description="主账号",value="Acct")
@RestController
@RequestMapping("/tbAcct")
public class TbAcctController extends BaseController {

    @Autowired
    private TbAcctService tbAcctService;
    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private TbAcctExtService tbAcctExtService;



    @ApiOperation(value = "新增主账号信息", notes = "主账号信息新增")
    @ApiImplicitParam(name = "formAcctVo", value = "主账号信息", required = true, dataType = "FormAcctVo")
    @UooLog(value = "新增主账号信息", key = "addTbAcct")
    @RequestMapping(value = "/addTbAcct", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object saveAcct(@RequestBody FormAcctVo formAcctVo) {

        //创建用户
        Wrapper tbUserWrapper = Condition.create().eq(true,"PERSONNEL_ID", formAcctVo.getPersonnelId());
        Page<TbUser> tbUserPage = tbUserService.selectPage(new Page<TbUser>(0, 12), tbUserWrapper);
        if(tbUserPage.getRecords().size() == 0){
            TbUser tbUser = new TbUser();
            tbUser.setPersonnelId(formAcctVo.getPersonnelId());
            Long userId = tbUserService.getId();
            formAcctVo.setUserId(userId);
            tbUser.setUserId(String.valueOf(userId));
            BeanUtils.copyProperties(EntityFillUtil.addEntity(), tbUser);
            tbUserService.insert(tbUser);
        }
        // 角色  todo

        // 校验
        TbAcct tbAcct = formAcctVo.getTbAcct();
        Wrapper tbAcctWrapper = Condition.create().eq(true,"ACCT", tbAcct.getAcct());
        Page<TbAcct> tbAcctPage = tbAcctService.selectPage(new Page<TbAcct>(0, 12), tbAcctWrapper);
        if (tbAcctPage.getRecords().size() > 0) {
            return ResultUtils.error(EumUserResponeCode.ACCT_IS_EXIST);
        }

        // 获取盐
        String salt = MD5Tool.getSalt();
        // 非对称密码
        String password = MD5Tool.md5Encoding(tbAcct.getPassword(), salt);
        // 对称密码
        String symmetryPassword = AESTool.AESEncode(tbAcct.getPassword());
        // 来源 todo
        Long acctId = tbAcctService.getId();
        tbAcct.setUserId(String.valueOf(formAcctVo.getUserId()));
        tbAcct.setAcctId(acctId);
        tbAcct.setSalt(salt);
        tbAcct.setPassword(password);
        tbAcct.setSymmetryPassword(symmetryPassword);
        BeanUtils.copyProperties(EntityFillUtil.addEntity(), tbAcct);
        tbAcctService.insert(tbAcct);

        return ResultUtils.success(null);
    }

    @ApiOperation(value = "删除主账号信息", notes = "主账号相关信息删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acct", value = "主账号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "删除主账号信息", key = "deleteTbAcct")
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public Object removeAcct(String acct, String updateUser) {

        Wrapper tbAcctWrapper = Condition.create().eq(true,"ACCT", acct);
        Page<TbAcct> tbAcctPage = tbAcctService.selectPage(new Page<TbAcct>(0, 12), tbAcctWrapper);
        if (tbAcctPage.getRecords().size() == 0) {
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST);
        }
        // 1.删除主账号
        TbAcct tbAcct = tbAcctPage.getRecords().get(0);
        BeanUtils.copyProperties(EntityFillUtil.delEntity(), tbAcct);
        tbAcctService.updateById(tbAcct);

        //3、删除角色 todo

        return ResultUtils.successfulTip(EumUserResponeCode.ACCT_IS_DELETE);
    }

    @ApiOperation(value = "修改主账号密码",notes = "主账号密码修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acct", value = "主账号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "pwd", value = "主账号新密码", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "修改主账号密码",key = "updateAcctPassword")
    @RequestMapping(value = "/modifyAcctPassword", method = RequestMethod.PUT)
    public Object modifyAcctPassword(String acct, String pwd, String updateUser) {

        Wrapper tbAcctWrapper = Condition.create().eq(true,"ACCT", acct);
        Page<TbAcct> tbEduPage = tbAcctService.selectPage(new Page<TbAcct>(0, 12), tbAcctWrapper);
        if (tbEduPage.getRecords().size() == 0) {
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST);
        }

        TbAcct tbAcct = tbEduPage.getRecords().get(0);
        boolean isRight = MD5Tool.verify(pwd, tbAcct.getSalt(), tbAcct.getPassword());
        if (isRight) {
            return ResultUtils.error(EumUserResponeCode.PWD_COMPARE_ERROR);
        }
        // 获取盐
        String salt = MD5Tool.getSalt();
        // 非对称密码
        String password = MD5Tool.md5Encoding(pwd, salt);
        // 对称密码
        String symmetryPassword = AESTool.AESEncode(pwd);
        tbAcct.setSalt(salt);
        tbAcct.setPassword(password);
        tbAcct.setSymmetryPassword(symmetryPassword);
        tbAcct.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbAcct.setUpdateUser(Long.valueOf(updateUser));
        tbAcct.updateById();

        return ResultUtils.successfulTip(EumUserResponeCode.ACCT_UPDATE_SUCCESS);

    }

    @ApiOperation(value = "修改主账号扩展信息",notes = "主账号扩展修改")
    @ApiImplicitParam(name = "formAcctVo", value = "主账号信息", required = true, dataType = "FormAcctVo")
    @UooLog(value = "修改主账号扩展信息",key = "updateAcctInfo")
    @RequestMapping(value = "/modifyAcctInfo", method = RequestMethod.PUT)
    public Object modifyAcctExtInfo(@RequestBody FormAcctVo formAcctVo) {
        TbAcct tbAcct = formAcctVo.getTbAcct();
        Wrapper tbAcctWrapper = Condition.create().eq(true,"ACCT", tbAcct.getAcct()).eq(true, "STATUS_CD", "1000");
        Page<TbAcct> tbAcctPage = tbAcctService.selectPage(new Page<TbAcct>(0, 12), tbAcctWrapper);
        if (tbAcctPage.getRecords().size() == 0) {
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST);
        }
        tbAcctService.updateById(tbAcct);

        //修改角色 todo

        return ResultUtils.successfulTip(EumUserResponeCode.ACCT_EXT_UPDATE_SUCCESS);
    }

    @ApiOperation(value = "主账号查询",notes = "主账号查询")
    @ApiImplicitParam(name = "personnelId", value = "主账号", required = true, dataType = "String", paramType = "path")
    @UooLog(value = "主账号查询",key = "getFormAcctByPersonnelId")
    @RequestMapping(value = "/getFormAcct", method = RequestMethod.GET)
    public Object getAcct(String personnelId){
        //用户
        TbUser tbUser = tbUserService.selectOne(new EntityWrapper<TbUser>().eq("PERSONNEL_ID", personnelId));

        Wrapper tbAcctWrapper = Condition.create().eq(true,"USER_ID", tbUser.getUserId()).eq(true, "STATUS_CD", BaseUnitConstants.ENTT_STATE_ACTIVE);
        Page<TbAcct> tbAcctPage = tbAcctService.selectPage(new Page<TbAcct>(0, 12), tbAcctWrapper);
        if (tbAcctPage.getRecords().size() == 0) {
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST);
        }
        FormAcctVo formAcctVo = new FormAcctVo();
        TbAcct tbAcct = tbAcctPage.getRecords().get(0);
        formAcctVo.setTbAcct(tbAcct);

        formAcctVo.setUserId(Long.valueOf(tbUser.getUserId()));
        formAcctVo.setPersonnelId(tbUser.getPersonnelId());


        return ResultUtils.success(formAcctVo);
    }



}

