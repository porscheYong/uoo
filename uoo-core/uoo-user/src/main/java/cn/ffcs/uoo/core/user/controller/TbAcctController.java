package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import cn.ffcs.uoo.core.user.entity.TbAcctOrg;
import cn.ffcs.uoo.core.user.service.TbAcctExtService;
import cn.ffcs.uoo.core.user.service.TbAcctOrgService;
import cn.ffcs.uoo.core.user.service.TbAcctService;
import cn.ffcs.uoo.core.user.service.TbUserService;
import cn.ffcs.uoo.core.user.util.AESTool;
import cn.ffcs.uoo.core.user.util.MD5Tool;
import cn.ffcs.uoo.core.user.util.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @author zhanglu
 * @since 2018-09-14
 */
@RestController
@RequestMapping("/tbAcct")
public class TbAcctController extends BaseController {

    @Autowired
    private TbAcctService tbAcctService;
    @Resource
    private TbUserService tbUserService;
    @Resource
    private TbAcctExtService tbAcctExtService;
    @Resource
    private TbAcctOrgService tbAcctOrgService;


    @ApiOperation(value = "新增主账号信息", notes = "主账号信息新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acct", value = "主账号信息", required = true, dataType = "TbAcct"),
            @ApiImplicitParam(name = "acctExt", value = "主账号扩展信息", required = true, dataType = "TbAcctExt"),
            @ApiImplicitParam(name = "acctOrg", value = "主账号组织关系", required = true, dataType = "TbAcctOrg"),
            @ApiImplicitParam(name = "pwd", value = "主账号明文密码", required = true, dataType = "String",paramType="path")
    })
    @UooLog(value = "新增主账号信息", key = "addTbAcct")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Void> saveAcct(TbAcct acct, TbAcctExt acctExt, TbAcctOrg acctOrg, String pwd) {
        ResponseResult<Void> result = new ResponseResult<Void>();
        // 校验
        TbAcct tbAcct = new TbAcct();
        tbAcct.setAcct(acct.getAcct());
        List<TbAcct> list = tbAcctService.selectAcctList(tbAcct);
        if (list.size() > 0) {
            result.setMessage("主账号已存在");
            result.setState(ResponseResult.STATE_ERROR);
            return result;
        }
        // 1.创建主账号
        tbAcct = new TbAcct();
        // 用户标识  todo

        // 获取盐
        String salt = MD5Tool.getSalt();
        // 非对称密码
        String password = MD5Tool.md5Encoding(pwd, salt);
        // 对称密码
        String symmetryPassword = AESTool.AESEncode(pwd);
        // 来源 todo

        tbAcct.setAcct(acct.getAcct());
        tbAcct.setSalt(salt);
        tbAcct.setPassword(password);
        tbAcct.setSymmetryPassword(symmetryPassword);
        tbAcct.setStatusCd("1000");
        tbAcct.setCreateDate(new Date());
        tbAcct.setCreateUser(acct.getCreateUser());
        tbAcct.setStatusDate(new Date());
        tbAcctService.saveAcct(tbAcct);
        Long acctId = tbAcct.getAcctId();
//      2.创建主账号扩展
        TbAcctExt tbAcctExt = new TbAcctExt();
        tbAcctExt.setPkAcct(acctId);
        tbAcctExt.setName(acctExt.getName());
        tbAcctExt.setContactWay(acctExt.getContactWay());
        tbAcctExt.setWorkEmail(acctExt.getWorkEmail());
        tbAcctExt.setCertType(acctExt.getCertType());
        tbAcctExt.setCertNo(acctExt.getCertNo());
        tbAcctExt.setGender(acctExt.getGender());
        tbAcctExt.setNation(acctExt.getNation());
        tbAcctExt.setNativePlace(acctExt.getNativePlace());
        tbAcctExt.setStatusCd("1000");
        tbAcctExt.setCreateDate(new Date());
        tbAcctExt.setCreateUser(acct.getCreateUser());
        tbAcctExt.setStatusDate(new Date());
        tbAcctExtService.saveAcctExt(tbAcctExt);
        // 3.创建主账号与组织关系
        TbAcctOrg tbAcctOrg = new TbAcctOrg();
        tbAcctOrg.setAcctId(acctId);
        tbAcctOrg.setOrgId(acctOrg.getOrgId());
        // 关系类型 todo
        // 排序号  todo
        // 重名称谓 todo

        tbAcctOrg.setStatusCd("1000");
        tbAcctOrg.setCreateDate(new Date());
        tbAcctOrg.setCreateUser(acct.getCreateUser());
        tbAcctOrg.setStatusDate(new Date());
        tbAcctOrgService.saveAcctOrg(tbAcctOrg);

        result.setMessage("主账号创建完毕");
        result.setState(ResponseResult.STATE_OK);
        return result;
    }

    @ApiOperation(value = "删除主账号信息", notes = "主账号相关信息删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acct", value = "主账号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "删除主账号信息", key = "deleteTbAcct")
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Void> removeAcct(String acct, String updateUser) {
        ResponseResult<Void> result = new ResponseResult<Void>();
        // 校验
        TbAcct tbAcct = new TbAcct();
        tbAcct.setAcct(acct);
        List<TbAcct> list = tbAcctService.selectAcctList(tbAcct);
        if (list.size() == 0) {
            result.setMessage("主账号不存在");
            result.setState(ResponseResult.STATE_ERROR);
            return result;
        }
        // 1.删除主账号
        tbAcct = new TbAcct();
        tbAcct.setAcct(acct);
        tbAcct.setStatusCd("1100");
        tbAcct.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbAcct.setUpdateUser(Long.valueOf(updateUser));
        tbAcct.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbAcctService.removeAcct(tbAcct);

        List<TbAcct> acctList = tbAcctService.selectAcctList(tbAcct);
        tbAcct = acctList.get(0);
        // 2.删除主账号与组织关系
        List<TbAcctOrg> acctOrgList = tbAcctOrgService.selectList(new EntityWrapper<TbAcctOrg>().eq("ACCT_ID", tbAcct.getAcctId()));
        for (TbAcctOrg acctOrg : acctOrgList) {
            acctOrg.setStatusCd("1100");
            acctOrg.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
            acctOrg.setUpdateUser(Long.valueOf(updateUser));
            acctOrg.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
            acctOrg.updateById();
        }
        // 3.删除主账号扩展
        TbAcctExt tbAcctExt = tbAcctExtService.selectOne(new EntityWrapper<TbAcctExt>().eq("pk_acct", tbAcct.getAcctId()));
        tbAcctExt.setStatusCd("1100");
        tbAcctExt.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbAcctExt.setUpdateUser(Long.valueOf(updateUser));
        tbAcctExt.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbAcctExt.updateById();

        result.setMessage("主账号已删除");
        result.setState(ResponseResult.STATE_OK);
        return result;
    }

    @ApiOperation(value = "修改主账号密码",notes = "主账号密码修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "acct", value = "主账号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "pwd", value = "主账号新密码", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "修改主账号密码",key = "updateAcctPassword")
    @RequestMapping(value = "/modifyAcctPassword", method = RequestMethod.PUT)
    public ResponseResult<Void> modifyAcctPassword(String acct, String pwd, String updateUser) {
        ResponseResult<Void> result = new ResponseResult<Void>();

//        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().eq("acct", acct));
        TbAcct tbAcct = new TbAcct();
        tbAcct.setAcct(acct);
        List<TbAcct> acctList = tbAcctService.selectAcctList(tbAcct);
        if (acctList.size() == 0) {
            result.setMessage("主账号不存在！");
            result.setState(ResponseResult.STATE_ERROR);
            return result;
        }
        tbAcct = acctList.get(0);
        boolean isRight = MD5Tool.verify(pwd, tbAcct.getSalt(), tbAcct.getPassword());
        if (isRight) {
            result.setState(ResponseResult.STATE_ERROR);
            result.setMessage("新密码与原密码不能相同！");
            return result;
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

        result.setMessage("主账号密码已更新！");
        result.setState(ResponseResult.STATE_OK);
        return result;

    }

    @ApiOperation(value = "修改主账号扩展信息",notes = "主账号扩展修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbAcct", value = "主账号", required = true, dataType = "TbAcct"),
            @ApiImplicitParam(name = "tbAcctExt", value = "主账号扩展信息", required = true, dataType = "TbAcctExt")
    })
    @UooLog(value = "修改主账号扩展信息",key = "updateAcctExtInfo")
    @RequestMapping(value = "/modifyAcctExtInfo", method = RequestMethod.PUT)
    public ResponseResult<Void> modifyAcctExtInfo(TbAcct tbAcct,TbAcctExt acctExt) {
        ResponseResult<Void> result = new ResponseResult<Void>();

//        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().eq("acct", acct));
        TbAcct acct = new TbAcct();
        acct.setAcct(tbAcct.getAcct());
        List<TbAcct> acctList = tbAcctService.selectAcctList(acct);
        if (acctList.size() == 0) {
            result.setMessage("主账号不存在！");
            result.setState(ResponseResult.STATE_ERROR);
            return result;
        }
        acct = acctList.get(0);
        TbAcctExt tbAcctExt = tbAcctExtService.selectOne(new EntityWrapper<TbAcctExt>().eq("pk_acct", acct.getAcctId()));
        tbAcctExt.setName(acctExt.getName());
        tbAcctExt.setContactWay(acctExt.getContactWay());
        tbAcctExt.setWorkEmail(acctExt.getWorkEmail());
        tbAcctExt.setCertType(acctExt.getCertType());
        tbAcctExt.setCertNo(acctExt.getCertNo());
        tbAcctExt.setGender(acctExt.getGender());
        tbAcctExt.setNation(acctExt.getNation());
        tbAcctExt.setNativePlace(acctExt.getNativePlace());
        tbAcctExt.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbAcctExt.setUpdateUser(acctExt.getUpdateUser());
        tbAcctExt.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbAcctExt.updateById();

        result.setMessage("主账号扩展信息更新！");
        result.setState(ResponseResult.STATE_OK);
        return result;
    }


    @RequestMapping("/testMD5")
    public ResponseResult<String> testSecurity(String password) {
        ResponseResult<String> result = new ResponseResult<String>();
//        String salt = PBETool.getSalt();
        String salt = "8330693158979529";
//        System.out.println("salt" + salt);
        result.setState(ResponseResult.STATE_OK);
        result.setMessage("测试成功");
        result.setData(MD5Tool.md5Encoding(password, salt));
        return result;
    }

    @RequestMapping("/testMD5Verfy")
    public ResponseResult<String> testSecurity2(String password, String md5Content) {
        ResponseResult<String> result = new ResponseResult<String>();
        String salt = "2932845411903990";
        boolean isRight = MD5Tool.verify(password, salt, md5Content);
        if (isRight) {
            result.setState(ResponseResult.STATE_OK);
            result.setMessage("测试成功");
            return result;
        }

        result.setState(ResponseResult.STATE_ERROR);
        result.setMessage("测试失败");
        return result;
    }


}

