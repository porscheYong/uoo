package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.*;
import cn.ffcs.uoo.core.user.service.TbAcctExtService;
import cn.ffcs.uoo.core.user.service.TbAcctService;
import cn.ffcs.uoo.core.user.service.TbSlaveAcctService;
import cn.ffcs.uoo.core.user.service.TbUserRoleService;
import cn.ffcs.uoo.core.user.util.AESTool;
import cn.ffcs.uoo.core.user.util.MD5Tool;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.ApplySlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.EditFormSlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.FormSlvacctAcctRelVo;
import cn.ffcs.uoo.core.user.vo.ResourceSlaveAcctVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.ApiImplicitParam;
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
        //验证主账号是否已有
        TbAcct tbAcct = (TbAcct) tbAcctService.getTbAcctByPsnId(editFormSlaveAcctVo.getPersonnelId());
        if(StrUtil.isNullOrEmpty(tbAcct)){
            return ResultUtils.error(EumUserResponeCode.ACCT_NO_EXIST_RE);
        }
        //从账号是否已存在
        if(tbSlaveAcctService.checkSlaveAcct(editFormSlaveAcctVo.getSlaveAcct(), editFormSlaveAcctVo.getAcctHostId(), editFormSlaveAcctVo.getResourceObjId())){
            return ResultUtils.error(EumUserResponeCode.SLAVE_ACCT_IS_EXIST);
        }

        TbSlaveAcct tbSlaveAcct = new TbSlaveAcct();
        BeanUtils.copyProperties(editFormSlaveAcctVo, tbSlaveAcct);
        // 获取盐
        String salt = MD5Tool.getSalt();
        // 非对称密码
        String password = MD5Tool.md5Encoding(tbSlaveAcct.getPassword(), salt);
        // 对称密码
        String symmetryPassword = AESTool.AESEncode(tbSlaveAcct.getPassword());
        tbSlaveAcct.setSalt(salt);
        tbSlaveAcct.setPassword(password);
        tbSlaveAcct.setSymmetryPassword(symmetryPassword);
        tbSlaveAcct.setAcctId(tbAcct.getAcctId());
        Long slaveAcctId = tbSlaveAcctService.getId();
        tbSlaveAcct.setSlaveAcctId(slaveAcctId);
        tbSlaveAcctService.addTbSlaveAcct(tbSlaveAcct);

        //角色
        tbUserRoleService.saveUserRole(editFormSlaveAcctVo.getRolesList(), slaveAcctId, 2L);

        //扩展属性
        if(!StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getTbAcctExt())){
            TbAcctExt tbAcctExt = editFormSlaveAcctVo.getTbAcctExt();
            tbAcctExt.setSlaveAcctId(slaveAcctId);
            tbAcctExtService.saveTbAcctExt(tbAcctExt);
        }

        return ResultUtils.success(null);
    }

    @ApiOperation(value = "删除从账号信息", notes = "删除从账号信息")
    @ApiImplicitParam(name = "slaveAcctId", value = "从账号账号标识", required = true, dataType = "Long", paramType = "path")
    @UooLog(value = "删除从账号信息", key = "delTbSlaveAcct")
    @RequestMapping(value = "/delTbSlaveAcct", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public Object delTbSlaveAcct(Long slaveAcctId){

        //从账号
        tbSlaveAcctService.delTbSlaveAcct(slaveAcctId);
        //扩展信息
        tbAcctExtService.delTbAcctExt(slaveAcctId);

        return ResultUtils.success(null);
    }

    @ApiOperation(value = "更新从账号信息", notes = "更新从账号信息")
    @ApiImplicitParam(name = "editFormSlaveAcctVo", value = "从账号信息", required = true, dataType = "EditFormSlaveAcctVo")
    @UooLog(value = "更新从账号信息", key = "updateTbSlaveAcct")
    @RequestMapping(value = "/updateTbSlaveAcct", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public Object updateTbSlaveAcct(@RequestBody EditFormSlaveAcctVo editFormSlaveAcctVo){
        //从账号是否已存在
        if(tbSlaveAcctService.checkSlaveAcct(editFormSlaveAcctVo.getSlaveAcct(), editFormSlaveAcctVo.getAcctHostId(), editFormSlaveAcctVo.getResourceObjId())){
            return ResultUtils.error(EumUserResponeCode.SLAVE_ACCT_IS_EXIST);
        }
        //从账号
        TbSlaveAcct tbSlaveAcct = new TbSlaveAcct();
        BeanUtils.copyProperties(editFormSlaveAcctVo, tbSlaveAcct);
        // 获取盐
        String salt = MD5Tool.getSalt();
        // 非对称密码
        String password = MD5Tool.md5Encoding(tbSlaveAcct.getPassword(), salt);
        // 对称密码
        String symmetryPassword = AESTool.AESEncode(tbSlaveAcct.getPassword());
        tbSlaveAcct.setSalt(salt);
        tbSlaveAcct.setPassword(password);
        tbSlaveAcct.setSymmetryPassword(symmetryPassword);
        tbSlaveAcctService.updateTbSlaveAcct(tbSlaveAcct);

        //角色
        List<TbRoles> oldTbRolesList = tbAcctService.getTbRoles(2L, tbSlaveAcct.getSlaveAcctId());
        tbUserRoleService.updateUserRole(editFormSlaveAcctVo.getRolesList(), oldTbRolesList, tbSlaveAcct.getSlaveAcctId(), 2L);

        //扩展属性
        if(!StrUtil.isNullOrEmpty(editFormSlaveAcctVo.getTbAcctExt())){
            TbAcctExt tbAcctExt = editFormSlaveAcctVo.getTbAcctExt();
            tbAcctExt.setSlaveAcctId(tbSlaveAcct.getSlaveAcctId());
            tbAcctExtService.saveTbAcctExt(tbAcctExt);
        }
        return ResultUtils.success(null);
    }



}

