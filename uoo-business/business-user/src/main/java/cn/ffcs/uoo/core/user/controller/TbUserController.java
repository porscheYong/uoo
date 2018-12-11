package cn.ffcs.uoo.core.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbAcctOrg;
import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbUser;
import cn.ffcs.uoo.core.user.service.TbAcctOrgService;
import cn.ffcs.uoo.core.user.service.TbAcctService;
import cn.ffcs.uoo.core.user.service.TbUserService;
import cn.ffcs.uoo.core.user.util.ResponseResult;
import cn.ffcs.uoo.core.user.util.ResultUtils;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.*;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-09-14
 */
@RestController
@RequestMapping("/tbUser")
public class TbUserController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TbUserService tbUserService;

    @Autowired
    private TbAcctService tbAcctService;

    @Autowired
    private TbAcctOrgService tbAcctOrgService;

    @ApiOperation(value = "用户组织条件查询", notes = "条件分页查询")
    @ApiImplicitParam(name = "psonOrgVo", value = "用户条件VO", required = true, dataType = "PsonOrgVo")
    @UooLog(value = "用户组织条件查询", key = "getPageUserOrg")
    @RequestMapping(value = "/getPageUserOrg/", method = RequestMethod.POST)
    public Object getPageUserOrg(@RequestBody PsonOrgVo psonOrgVo){
        Page<ListUserOrgVo> userOrgVoPage = tbUserService.selectUserOrgPage(psonOrgVo);
        return ResultUtils.success(userOrgVoPage);
    }



    @ApiOperation(value = "用户信息", notes = "用户信息")
    @ApiImplicitParam(name = "personnelId", value = "人员标识", required = true, dataType = "String",paramType="path")
    @UooLog(value = "用户组织条件查询", key = "getUser")
    @RequestMapping(value = "/getUserByPersonnelId", method = RequestMethod.GET)
    public Object getUser(String personnelId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TABLE_PERSONNEL_ID, personnelId);
        TbUser tbUser = tbUserService.selectOne(new EntityWrapper<TbUser>().allEq(map));
        if(StrUtil.isNullOrEmpty(tbUser)){
            return ResultUtils.error(EumUserResponeCode.USER_NOT_EXIST);
        }
        EditFormUserVo editFormUserVo = new EditFormUserVo();
        editFormUserVo.setUserId(tbUser.getUserId());

        /**
         * todo
         *  人员信息 调用personnel接口
         */
        //ResponseResult result = (ResponseResult) restTemplate.getForObject(
        ResponseResult result = restTemplate.getForObject(
                "http://PERSONNEL-SERVICE/personnel/getPsnByUser?personnelId=" + personnelId,
                ResponseResult.class
        );

        JSONObject jsonObject= JSONObject.fromObject(result); // 将数据转成json字符串
        ResponseResult per = (ResponseResult)JSONObject.toBean(jsonObject, ResponseResult.class); //将json转成需要的对象
        if("0".equals(per.getCode())){
            editFormUserVo.setPsnByUserVo((PsnByUserVo) per.getData());
        }else{
            return  ResultUtils.error(per.getCode(), per.getMessage());
        }

        /**
         * 角色信息
         */
        List<TbRoles> tbRolesList = tbUserService.getRoleByUserId(Long.valueOf(tbUser.getUserId()));
        editFormUserVo.setTbRolesList(tbRolesList);
        /**
         * todo
         * 归属组织信息 调用org接口
         */

        //主账号
        map.remove(BaseUnitConstants.TABLE_PERSONNEL_ID);
        map.put(BaseUnitConstants.TBALE_USER_ID, tbUser.getUserId());
        TbAcct tbAcct = tbAcctService.selectOne(new EntityWrapper<TbAcct>().allEq(map));
        BeanUtils.copyProperties(tbAcct, editFormUserVo);

        //从账号
        List<ListSlaveAcctVo> slaveAcctVoList =  tbUserService.getSlaveAcctInfo(Long.valueOf(tbUser.getUserId()), tbAcct.getAcctId());
        editFormUserVo.setSlaveAcctVoList(slaveAcctVoList);

        return ResultUtils.success(editFormUserVo);
    }
}

