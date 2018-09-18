package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.personnel.entity.TbAcct;
import cn.ffcs.uoo.core.personnel.entity.TbAcctExt;
import cn.ffcs.uoo.core.personnel.entity.TbAcctOrg;
import cn.ffcs.uoo.core.personnel.service.TbAcctExtService;
import cn.ffcs.uoo.core.personnel.service.TbAcctOrgService;
import cn.ffcs.uoo.core.personnel.service.TbAcctService;
import cn.ffcs.uoo.core.personnel.service.TbUserService;
import cn.ffcs.uoo.core.personnel.util.AESTool;
import cn.ffcs.uoo.core.personnel.util.MD5Tool;
import cn.ffcs.uoo.core.personnel.util.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
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


    /**
     * 创建主账号关联人员，同时创建主账号扩展，同时创建主账号与组织关系
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Void> saveAcct(String acct, String pwd, String name, String contactWay, String workEmail,
                                         String certType, String certNo, String gender, String nation, String nativePlace,
                                         String createUser) {
        ResponseResult<Void> result = new ResponseResult<Void>();
        // 添加校验
//        TbUser tbUser = tbUserService.selectById(userId);
//        if (null == tbUser) {
//            result.setMessage("无此用户！");
//            result.setState(ResponseResult.STATE_ERROR);
//            return result;
//        }
        // 1.创建主账号
        TbAcct tbAcct = new TbAcct();
        // 用户标识  todo

        // 获取盐
        String salt = MD5Tool.getSalt();
        // 非对称密码
        String password = MD5Tool.md5Encoding(pwd, salt);
        // 对称密码
        String symmetryPassword = AESTool.AESEncode(pwd);
        // 来源 todo

        tbAcct.setAcct(acct);
        tbAcct.setSalt(salt);
        tbAcct.setPassword(password);
        tbAcct.setSymmetryPassword(symmetryPassword);
        tbAcct.setStatusCd("1000");
        tbAcct.setCreateDate(new Date());
        tbAcct.setCreateUser(BigDecimal.valueOf(Long.valueOf(createUser)));
//        tbAcct.setStatusDate(new Date());
        tbAcctService.saveAcct(tbAcct);
        Long acctId = tbAcct.getAcctId();

//        // 2.创建主账号扩展
        TbAcctExt tbAcctExt = new TbAcctExt();
        tbAcctExt.setPkAcct(acctId);
        tbAcctExt.setName(name);
        tbAcctExt.setContactWay(contactWay);
        tbAcctExt.setWorkEmail(workEmail);
        tbAcctExt.setCertType(certType);
        tbAcctExt.setCertNo(certNo);
        tbAcctExt.setGender(gender);
        tbAcctExt.setNation(nation);
        tbAcctExt.setNativePlace(nativePlace);
        tbAcctExt.setStatusCd("1000");
        tbAcctExt.setCreateDate(new Date());
        tbAcctExt.setCreateUser(BigDecimal.valueOf(Long.valueOf(createUser)));
//        tbAcctExt.setStatusDate(new Date());
        tbAcctExtService.saveAcctExt(tbAcctExt);

        // 3.创建主账号与组织关系
        TbAcctOrg tbAcctOrg = new TbAcctOrg();
        tbAcctOrg.setAcctId(acctId);
        // 组织标识 todo
        // 关系类型 todo
        // 排序号  todo
        // 重名称谓 todo

        tbAcctOrg.setStatusCd("1000");
        tbAcctOrg.setCreateDate(new Date());
        tbAcctOrg.setCreateUser(BigDecimal.valueOf(Long.valueOf(createUser)));
        tbAcctOrg.setStatusDate(new Date());
        tbAcctOrgService.saveAcctOrg(tbAcctOrg);

        result.setMessage("主账号创建完毕");
        result.setState(ResponseResult.STATE_OK);
        return result;
    }

    /**删除主账号
     * @param acct
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Void> removeAcct(String acct) {
        ResponseResult<Void> result = new ResponseResult<Void>();
        // 校验  todo
        // 1.删除主账号
        // resource 与关键字重复，需要重新写方法 todo
        List<TbAcct> acctList = tbAcctService.selectList(new EntityWrapper<TbAcct>().eq("acct", acct));
        TbAcct tbAcct = acctList.get(0);
        tbAcct.setStatusCd("1100");
        tbAcct.updateById();
        // 2.删除主账号与组织关系
        List<TbAcctOrg> acctOrgList = tbAcctOrgService.selectList(new EntityWrapper<TbAcctOrg>().eq("acct_id",tbAcct.getAcct()));
        for (TbAcctOrg acctOrg : acctOrgList) {
            acctOrg.setStatusCd("1100");
            acctOrg.updateById();
        }
        // 3.删除主账号扩展
        TbAcctExt tbAcctExt = tbAcctExtService.selectOne(new EntityWrapper<TbAcctExt>().eq("pk_acct",tbAcct.getAcctId()));
        tbAcctExt.setStatusCd("1100");
        tbAcctExt.updateById();

        result.setMessage("主账号已删除");
        result.setState(ResponseResult.STATE_OK);
        return result;
    }

    /**
     * 主账号修改密码
     * @param acct
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/modifyAcctPassword", method = RequestMethod.POST)
    public ResponseResult<Void> modifyAcctPassword(String acct, String pwd) {
        ResponseResult<Void> result = new ResponseResult<Void>();

        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().eq("acct", acct));
        // 获取盐
        String salt = MD5Tool.getSalt();
        // 非对称密码
        String password = MD5Tool.md5Encoding(pwd, salt);
        // 对称密码
        String symmetryPassword = AESTool.AESEncode(pwd);
        tbAcct.setSalt(salt);
        tbAcct.setPassword(password);
        tbAcct.setSymmetryPassword(symmetryPassword);
        tbAcct.updateById();

        result.setMessage("主账号密码已更新！");
        result.setState(ResponseResult.STATE_OK);
        return result;

    }

    /**
     * 修改主账号扩展信息
     * @param acct
     * @param name
     * @param contactWay
     * @param workEmail
     * @param certType
     * @param certNo
     * @param gender
     * @param nation
     * @param nativePlace
     * @return
     */
    @RequestMapping(value = "/modifyAcctExtInfo", method = RequestMethod.POST)
    public ResponseResult<Void> modifyAcctExtInfo(String acct, String name, String contactWay, String workEmail, String certType,
                        String certNo, String gender, String nation, String nativePlace) {
        ResponseResult<Void> result = new ResponseResult<Void>();

        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().eq("acct", acct));
        TbAcctExt tbAcctExt = tbAcctExtService.selectOne(new EntityWrapper<TbAcctExt>().eq("pk_acct", tbAcct.getAcctId()));
        tbAcctExt.setName(name);
        tbAcctExt.setContactWay(contactWay);
        tbAcctExt.setWorkEmail(workEmail);
        tbAcctExt.setCertType(certType);
        tbAcctExt.setCertNo(certNo);
        tbAcctExt.setGender(gender);
        tbAcctExt.setNation(nation);
        tbAcctExt.setNativePlace(nativePlace);
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
    public ResponseResult<String> testSecurity2(String password,String md5Content) {
        ResponseResult<String> result = new ResponseResult<String>();
        String salt = "8330693158979529";
        boolean isRight = MD5Tool.verify(password,salt, md5Content);
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

