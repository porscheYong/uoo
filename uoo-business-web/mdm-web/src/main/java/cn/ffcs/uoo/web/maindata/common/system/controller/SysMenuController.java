package cn.ffcs.uoo.web.maindata.common.system.controller;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.web.maindata.common.system.client.SysMenuClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.log.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.log.OperateType;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/system")
public class SysMenuController {
    @Autowired
    private SysMenuClient sysMenuClient;
    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    //type 必填，module必填，methods必填，desc选填
    @OperateLog(type=OperateType.SELECT,module="平台系统菜单模块",methods="查询账号所有菜单",desc="获取当前所有菜单")
    @ApiOperation(value = "查询账号所有菜单", notes = "查询账号所有菜单")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getAccoutMenu", method = RequestMethod.GET)
    public ResponseResult<List<SysMenu>> getAccoutMenu(){
        Subject sub=SecurityUtils.getSubject();
        Object primaryPrincipal = sub.getPrincipals().getPrimaryPrincipal();
        //alterPwdDTO.setAccout(primaryPrincipal.toString());
        ResponseResult<List<SysMenu>> alterPwd = sysMenuClient.getMenuByAccout(primaryPrincipal.toString());
        return alterPwd;
    }
}
