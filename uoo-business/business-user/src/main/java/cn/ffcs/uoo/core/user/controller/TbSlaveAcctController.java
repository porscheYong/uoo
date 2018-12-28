package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.*;
import cn.ffcs.uoo.core.user.service.*;
import cn.ffcs.uoo.core.user.util.*;
import cn.ffcs.uoo.core.user.vo.ApplySlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.EditFormSlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.ResourceSlaveAcctVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
    private TbAcctService tbAcctService;
    @Autowired
    private TbAcctExtService tbAcctExtService;
    @Autowired
    private TbUserRoleService tbUserRoleService;
    @Autowired
    private RabbitMqService rabbitMqService;


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

    //todo -新版本--------------------------------------------------------------------

    @ApiOperation(value = "新增从账号信息", notes = "从账号信息新增")
    @ApiImplicitParam(name = "editFormSlaveAcctVo", value = "从账号信息", required = true, dataType = "EditFormSlaveAcctVo")
    @UooLog(value = "新增从账号信息", key = "addTbSlaveAcct")
    @RequestMapping(value = "/addTbSlaveAcct", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object saveSlaveAcct(@RequestBody EditFormSlaveAcctVo editFormSlaveAcctVo){

        Object obj = checkFormSlaveAcct(editFormSlaveAcctVo);
        if(!StrUtil.isNullOrEmpty(obj)){
            return obj;
        }
//
//        TbAcct tbAcct = (TbAcct) tbAcctService.getTbAcctByPsnId(editFormSlaveAcctVo.getPersonnelId());
//        TbSlaveAcct tbSlaveAcct = new TbSlaveAcct();
//        BeanUtils.copyProperties(editFormSlaveAcctVo, tbSlaveAcct);
//        // 获取盐
//        String salt = MD5Tool.getSalt();
//        // 非对称密码
//        String password = MD5Tool.md5Encoding(tbSlaveAcct.getPassword(), salt);
//        // 对称密码
//        String symmetryPassword = AESTool.AESEncode(tbSlaveAcct.getPassword());
//        tbSlaveAcct.setSalt(salt);
//        tbSlaveAcct.setPassword(password);
//        tbSlaveAcct.setSymmetryPassword(symmetryPassword);
//        tbSlaveAcct.setAcctId(tbAcct.getAcctId());
        Long slaveAcctId = tbSlaveAcctService.getId();
        Long userId = editFormSlaveAcctVo.getUserId();
//        tbSlaveAcct.setSlaveAcctId(slaveAcctId);
//        tbSlaveAcctService.addTbSlaveAcct(tbSlaveAcct);
        obj =  tbSlaveAcctService.insertOrUpdateTbSlaveAcct(editFormSlaveAcctVo, slaveAcctId);
        if(!StrUtil.isNullOrEmpty(obj)){
            return obj;
        }

        //角色
        tbUserRoleService.saveUserRole(editFormSlaveAcctVo.getRolesList(), slaveAcctId, 2L, userId);

        //扩展属性
        if(!StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getTbAcctExt())){
            TbAcctExt tbAcctExt = editFormSlaveAcctVo.getTbAcctExt();
            tbAcctExt.setSlaveAcctId(slaveAcctId);
            tbAcctExt.setCreateUser(userId);
            tbAcctExt.setUpdateUser(userId);
            tbAcctExtService.saveTbAcctExt(tbAcctExt);
        }


        rabbitMqService.sendMqMsg("person", "insert", "slaveAcctId", slaveAcctId);

        return ResultUtils.success(null);
    }

    @ApiOperation(value = "删除从账号信息", notes = "删除从账号信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slaveAcctId", value = "从账号账号标识", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "user", value = "操作人标识", required = true, dataType = "Long", paramType = "path")
    })
    @UooLog(value = "删除从账号信息", key = "delTbSlaveAcct")
    @RequestMapping(value = "/delTbSlaveAcct", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public Object delTbSlaveAcct(Long slaveAcctId, Long userId){
        tbSlaveAcctService.delAllTbSlaveAcct(slaveAcctId, userId);
        return ResultUtils.success(null);
    }

    @ApiOperation(value = "更新从账号信息", notes = "更新从账号信息")
    @ApiImplicitParam(name = "editFormSlaveAcctVo", value = "从账号信息", required = true, dataType = "EditFormSlaveAcctVo")
    @UooLog(value = "更新从账号信息", key = "updateTbSlaveAcct")
    @RequestMapping(value = "/updateTbSlaveAcct", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object updateTbSlaveAcct(@RequestBody EditFormSlaveAcctVo editFormSlaveAcctVo){
        Object obj = checkFormSlaveAcct(editFormSlaveAcctVo);
        if(!StrUtil.isNullOrEmpty(obj)){
            return obj;
        }

//        //从账号
//        TbSlaveAcct tbSlaveAcct = tbSlaveAcctService.selectById(editFormSlaveAcctVo.getSlaveAcctId());
//        BeanUtils.copyProperties(editFormSlaveAcctVo, tbSlaveAcct);
//        if(editFormSlaveAcctVo.getPassword().equals(tbSlaveAcct.getPassword())){
//            // 获取盐
//            String salt = MD5Tool.getSalt();
//            // 非对称密码
//            String password = MD5Tool.md5Encoding(tbSlaveAcct.getPassword(), salt);
//            // 对称密码
//            String symmetryPassword = AESTool.AESEncode(tbSlaveAcct.getPassword());
//            tbSlaveAcct.setSalt(salt);
//            tbSlaveAcct.setPassword(password);
//            tbSlaveAcct.setSymmetryPassword(symmetryPassword);
//        }
//
//        tbSlaveAcctService.updateTbSlaveAcct(tbSlaveAcct);
        obj =  tbSlaveAcctService.insertOrUpdateTbSlaveAcct(editFormSlaveAcctVo, editFormSlaveAcctVo.getSlaveAcctId());
        if(!StrUtil.isNullOrEmpty(obj)){
            return obj;
        }
        Long userId = editFormSlaveAcctVo.getUserId();

        //角色
        List<TbRoles> oldTbRolesList = tbAcctService.getTbRoles(2L, editFormSlaveAcctVo.getSlaveAcctId());
        tbUserRoleService.updateUserRole(editFormSlaveAcctVo.getRolesList(), oldTbRolesList, editFormSlaveAcctVo.getSlaveAcctId(), 2L, userId);

        //扩展属性
        if(!StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getTbAcctExt())){
            TbAcctExt tbAcctExt = editFormSlaveAcctVo.getTbAcctExt();
            tbAcctExt.setSlaveAcctId(editFormSlaveAcctVo.getSlaveAcctId());
            tbAcctExt.setUpdateUser(userId);
            tbAcctExtService.saveTbAcctExt(tbAcctExt);
        }else{
            tbAcctExtService.delTbAcctExt(editFormSlaveAcctVo.getSlaveAcctId(), userId);
        }

        rabbitMqService.sendMqMsg("person", "update", "slaveAcctId", editFormSlaveAcctVo.getSlaveAcctId());

        return ResultUtils.success(null);
    }


    public Object checkFormSlaveAcct(EditFormSlaveAcctVo editFormSlaveAcctVo){

        //验证主账号是否已有
        TbAcct tbAcct = (TbAcct) tbAcctService.getTbAcctByPsnId(editFormSlaveAcctVo.getPersonnelId());
        if(StrUtil.isNullOrEmpty(tbAcct)){
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST_RE);
        }

        if(StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getPersonnelId())){
            return ResultUtils.error(EumUserResponeCode.PERSONNEL_NO_CHOOSE);
        }
        if(StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getSlaveAcct())){
            return ResultUtils.error(EumUserResponeCode.SLAVE_ACCT_NULL);
        }
        if(StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getAcctOrgRelId())){
            return ResultUtils.error(EumUserResponeCode.ACCT_HOST_NULL);
        }
        if(StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getEnableDate())){
            return ResultUtils.error(EumUserResponeCode.EFF_DATE_NULL);
        }
        if(StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getDisableDate())){
            return ResultUtils.error(EumUserResponeCode.EXP_DATE_NULL);
        }
        if(StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getStatusCd())){
            return ResultUtils.error(EumUserResponeCode.STATUS_CD_NULL);
        }
        if(StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getRolesList())){
            return ResultUtils.error(EumUserResponeCode.ROLES_NULL);
        }
        if(StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getResourceObjId())){
            return ResultUtils.error(EumUserResponeCode.RESOURCE_OBJ_NULL);
        }
        if(StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getAcctOrgRelId())){
            return ResultUtils.error(EumUserResponeCode.ACCT_HOST_NULL);
        }
        if(!StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getTbAcctExt())){
            TbAcctExt tbAcctExt = editFormSlaveAcctVo.getTbAcctExt();
            if(StrUtil.isNullOrEmpty(tbAcctExt.getWorkEmail()) || !StrUtil.checkEmail(tbAcctExt.getWorkEmail())){
                return ResultUtils.error(EumUserResponeCode.EMAIL_ERROR);
            }
            if(StrUtil.isNullOrEmpty(tbAcctExt.getContactWay()) || !StrUtil.checkTelephoneNumber(tbAcctExt.getContactWay())) {
                return ResultUtils.error(EumUserResponeCode.MOBILE_ERROR);
            }
            if(StrUtil.isNullOrEmpty(tbAcctExt.getCertNo())) {
                return ResultUtils.error(EumUserResponeCode.CERT_NO_ERROR);
            }
            if("1".equals(tbAcctExt.getCertType()) && !IdCardVerification.idCardValidate(tbAcctExt.getCertNo())){
                return ResultUtils.error(EumUserResponeCode.CERT_NO_ERROR);
            }
        }

        //从账号是否已存在
        if(tbSlaveAcctService.checkSlaveAcct(editFormSlaveAcctVo.getSlaveAcct(), editFormSlaveAcctVo.getAcctOrgRelId(), editFormSlaveAcctVo.getResourceObjId(), editFormSlaveAcctVo.getSlaveAcctId(), null)){
            return ResultUtils.error(EumUserResponeCode.SLAVE_ACCT_IS_EXIST);
        }

        if(tbSlaveAcctService.checkSlaveAcct(null, editFormSlaveAcctVo.getAcctOrgRelId(), editFormSlaveAcctVo.getResourceObjId(), editFormSlaveAcctVo.getSlaveAcctId(), tbAcct.getAcctId())){
            return ResultUtils.error(EumUserResponeCode.SLAVE_ACCT_ORGREL_EXIST);
        }

        return null;
    }



}

