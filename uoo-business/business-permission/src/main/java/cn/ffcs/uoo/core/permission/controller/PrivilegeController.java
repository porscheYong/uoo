package cn.ffcs.uoo.core.permission.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.entity.PrivDataRel;
import cn.ffcs.uoo.core.permission.entity.Privilege;
import cn.ffcs.uoo.core.permission.service.IPrivDataRelService;
import cn.ffcs.uoo.core.permission.service.IPrivilegeService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;

/**
 * <p>
 * 权限规格表，记录权限的配置 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@RestController
@RequestMapping("/permission/privilege")
public class PrivilegeController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${uoo.serviceName.region}")
    private String regionServiceName;
    @Autowired
    private IPrivilegeService privilegeService;
    @Autowired
    private IPrivDataRelService relSvc;

    @GetMapping("/lisAllPrivilege")
    public ResponseResult lisAllPrivilege(){
        Wrapper<Privilege> wrapper =Condition.create().eq("STATUS_CD", StatusCD.VALID);
        List<Privilege> selectList = privilegeService.selectList(wrapper);
        return ResponseResult.createSuccessResult(selectList, "");
    }
    
    @Transactional
    @RequestMapping(value = "/addPrivilege", method = RequestMethod.POST)
    public ResponseResult addPrivilege(@RequestBody Privilege privilege) {
        // regionId 校验
        Long regionId = privilege.getRegionId();
        ResponseResult result = restTemplate.getForObject(
                "http://" + regionServiceName + "/region/commonRegion/getCommonRegion/id=" + regionId,
                ResponseResult.class);

        if (result.getState() != ResponseResult.STATE_OK) {
            return ResponseResult.createErrorResult("公用管理区域是无效数据");
        }
        privilege.setPrivId(privilegeService.getId());
        privilege.setStatusCd(StatusCD.VALID);
        privilege.setCreateDate(new Date());
        privilege.setUpdateDate(new Date());
        return ResponseResult.createSuccessResult("success");
    }

    @Transactional
    @RequestMapping(value = "/updatePrivilege", method = RequestMethod.POST)
    public ResponseResult updatePrivilege(@RequestBody Privilege privilege) {
        Long privId = privilege.getPrivId();
        if (privId == null) {
            return ResponseResult.createErrorResult("无效数据");
        }
        Privilege obj = privilegeService.selectById(privId);
        if (obj == null || !StatusCD.VALID.equals(obj.getStatusCd())) {
            return ResponseResult.createErrorResult("不能修改无效数据");
        }
        // regionId 校验
        Long regionId = privilege.getRegionId();
        ResponseResult result = restTemplate.getForObject(
                "http://" + regionServiceName + "/region/commonRegion/getCommonRegion/id=" + regionId,
                ResponseResult.class);

        if (result.getState() != ResponseResult.STATE_OK) {
            return ResponseResult.createErrorResult("公用管理区域是无效数据");
        }
        // privilege.setPrivId(privilegeService.getId());
        privilege.setStatusCd(StatusCD.VALID);
        privilege.setUpdateDate(new Date());
        return ResponseResult.createSuccessResult("success");
    }
    
    @SuppressWarnings("unchecked")
    @Transactional
    @RequestMapping(value = "/deletePrivilege", method = RequestMethod.POST)
    public ResponseResult deletePrivilege(@RequestBody Privilege privilege) {
        Long privId = privilege.getPrivId();
        if (privId == null) {
            return ResponseResult.createErrorResult("无效数据");
        }
        Privilege obj = privilegeService.selectById(privId);
        if (obj == null ) {
            return ResponseResult.createErrorResult("不能删除无效数据");
        }
        Wrapper<PrivDataRel> wrapper = Condition.create().eq("PRIV_ID", privId);
        List<PrivDataRel> selectList = relSvc.selectList(wrapper);
        if(selectList!=null){
            for (PrivDataRel privDataRel : selectList) {
                if(StatusCD.VALID.equals(privDataRel.getStatusCd())){
                    return ResponseResult.createErrorResult("请先删除相关权限数据信息");
                }
            }
            
        }
        
        obj.setStatusCd(StatusCD.INVALID);
        obj.setStatusDate(new Date());
        obj.setUpdateDate(new Date());
        privilegeService.updateById(obj);
        return ResponseResult.createSuccessResult("success");
    }
    
}
