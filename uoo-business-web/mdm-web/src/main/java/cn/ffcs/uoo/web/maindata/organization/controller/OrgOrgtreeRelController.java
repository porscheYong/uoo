package cn.ffcs.uoo.web.maindata.organization.controller;


import cn.ffcs.uoo.web.maindata.common.system.dto.SysUser;
import cn.ffcs.uoo.web.maindata.mdm.consts.LoginConsts;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgOrgtreeRel;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgOrgtreeRelService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-18
 */
@RestController
@RequestMapping("/orgOrgtreeRel")
public class OrgOrgtreeRelController{

    @Resource
    private OrgOrgtreeRelService orgOrgtreeRelService;

    @ApiOperation(value = "修改组织组织树信息", notes = "修改组织组织树信息")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateOrgOrgTreeRel", method = RequestMethod.POST)
    public ResponseResult<String> updateOrgOrgTreeRel(@RequestBody OrgOrgtreeRel orgOrgTreeRel){
        Subject subject=SecurityUtils.getSubject();
        SysUser currentLoginUser = (SysUser) subject.getSession().getAttribute(LoginConsts.LOGIN_KEY);
        Long userId = currentLoginUser.getUserId();
        orgOrgTreeRel.setUpdateUser(userId);
        return orgOrgtreeRelService.updateOrgOrgTreeRel(orgOrgTreeRel);
    }
}

