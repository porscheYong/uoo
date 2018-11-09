package cn.ffcs.uoo.core.permission.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;

import cn.ffcs.uoo.core.permission.consts.PrivGrantObjectTypeConsts;
import cn.ffcs.uoo.core.permission.consts.StatusCD;
import cn.ffcs.uoo.core.permission.entity.PrivGrant;
import cn.ffcs.uoo.core.permission.entity.Privilege;
import cn.ffcs.uoo.core.permission.entity.TbRoles;
import cn.ffcs.uoo.core.permission.service.IPrivGrantService;
import cn.ffcs.uoo.core.permission.service.IPrivilegeService;
import cn.ffcs.uoo.core.permission.service.TbRolesService;
import cn.ffcs.uoo.core.permission.vo.BatchAddPositionPrivGrantVO;
import cn.ffcs.uoo.core.permission.vo.BatchAddRolePrivGrantVO;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;

/**
 * <p>
 * 描述将权限授权给不同的对象，包括系统用户、系统岗位、角色；
一个系统用户也可以拥有多个私有的权限，一个权限可以分配给多个系统用户。
一个系统岗位除了拥有角色所带的权限，也可以拥有私有的多个权限，一个权限可以分配给多个系统岗位。
一个角色可以包含多个权限，一个权限也可以分配给多个角色。 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@Controller
@RequestMapping("/permission/privGrant")
public class PrivGrantController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${uoo.serviceName.position}")
    private String positionServiceName;
    @Autowired
    private IPrivGrantService grantSvc;
    @Autowired
    private TbRolesService roleSvc;
    @Autowired
    private IPrivilegeService privSvc;
    
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value="batchAddRolePrivGrant",method=RequestMethod.POST)
    public ResponseResult batchAddRolePrivGrant(@RequestBody BatchAddRolePrivGrantVO batchAddRolePrivGrantVO){
        long roleId = batchAddRolePrivGrantVO.getRoleId();
        List<Long> privIds = batchAddRolePrivGrantVO.getPrivIds();
        //先把角色的所有权限清空，再重新保存
        TbRoles role = roleSvc.selectById(Long.valueOf(roleId));
        if(role==null){
            return ResponseResult.createErrorResult("角色不存在");
        }
        if(privIds!=null&&!privIds.isEmpty()){
            List<Privilege> batchPrivs = privSvc.selectBatchIds(privIds);
            if(batchPrivs.size()!=privIds.size()){
                return ResponseResult.createErrorResult("选中的权限中有无效权限，刷新重试");
            }
            for (Privilege privilege : batchPrivs) {
                if(!StatusCD.VALID.equals(privilege.getStatusCd())){
                    return ResponseResult.createErrorResult("选中的权限中有无效权限，刷新重试");
                }
            }
        }
        Wrapper<PrivGrant> wrapper = Condition.create().eq("GRANT_OBJ_TYPE", PrivGrantObjectTypeConsts.GRANT_OBJECT_TYPE_ROLE).eq("GRANT_OBJ_ID", roleId);
        grantSvc.delete(wrapper);
        for (Long privId : privIds) {
            PrivGrant pg=new PrivGrant();
            pg.setCreateDate(new Date());
            pg.setCreateUser(batchAddRolePrivGrantVO.getOperateUser());
            pg.setEffDate(batchAddRolePrivGrantVO.getEffDate());
            pg.setExpDate(batchAddRolePrivGrantVO.getExpDate());
            pg.setGrantObjId(roleId);
            pg.setGrantObjType(PrivGrantObjectTypeConsts.GRANT_OBJECT_TYPE_ROLE);
            pg.setPrivGrantId(grantSvc.getId());
            pg.setStatusCd(StatusCD.VALID);
            pg.setPrivId(privId);
            grantSvc.insert(pg);
        }
        return ResponseResult.createSuccessResult("success");
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor=Exception.class)
    @RequestMapping(value="batchAddPositionPrivGrant",method=RequestMethod.POST)
    public ResponseResult batchAddPositionPrivGrant(@RequestBody BatchAddPositionPrivGrantVO batchAddPositionPrivGrantVO){
        long posId = batchAddPositionPrivGrantVO.getPosId();
        List<Long> privIds = batchAddPositionPrivGrantVO.getPrivIds();
        //先把    所有权限清空，再重新保存
        // 
        if(posId == 0){
            return ResponseResult.createErrorResult("请选择岗位");
        }
        if(privIds!=null&&!privIds.isEmpty()){
            List<Privilege> batchPrivs = privSvc.selectBatchIds(privIds);
            if(batchPrivs.size()!=privIds.size()){
                return ResponseResult.createErrorResult("选中的权限中有无效权限，刷新重试");
            }
            for (Privilege privilege : batchPrivs) {
                if(!StatusCD.VALID.equals(privilege.getStatusCd())){
                    return ResponseResult.createErrorResult("选中的权限中有无效权限，刷新重试");
                }
            }
        }
        Wrapper<PrivGrant> wrapper = Condition.create().eq("GRANT_OBJ_TYPE", PrivGrantObjectTypeConsts.GRANT_OBJECT_TYPE_ROLE).eq("GRANT_OBJ_ID", posId);
        grantSvc.delete(wrapper);
        for (Long privId : privIds) {
            PrivGrant pg=new PrivGrant();
            pg.setCreateDate(new Date());
            pg.setCreateUser(batchAddPositionPrivGrantVO.getOperateUser());
            pg.setEffDate(batchAddPositionPrivGrantVO.getEffDate());
            pg.setExpDate(batchAddPositionPrivGrantVO.getExpDate());
            pg.setGrantObjId(posId);
            pg.setGrantObjType(PrivGrantObjectTypeConsts.GRANT_OBJECT_TYPE_POSITION);
            pg.setPrivGrantId(grantSvc.getId());
            pg.setStatusCd(StatusCD.VALID);
            pg.setPrivId(privId);
            grantSvc.insert(pg);
        }
        return ResponseResult.createSuccessResult("success");
    }
    @GetMapping("listAllPrivGrantByRole/{roleId}")
    public ResponseResult listAllPrivGrantByRole(@PathVariable long roleId){
        HashMap<String, Object> params = new HashMap<>();
        params.put("GRANT_OBJ_TYPE", PrivGrantObjectTypeConsts.GRANT_OBJECT_TYPE_ROLE);
        params.put("GRANT_OBJ_ID", roleId);
        List<Map> list = grantSvc.selectPrivGrantByGranObj(params);
        return ResponseResult.createSuccessResult(list, "");
    }
    @GetMapping("listAllPrivGrantByPosition/{posId}")
    public ResponseResult listAllPrivGrantByPosition(@PathVariable long posId){
        HashMap<String, Object> params = new HashMap<>();
        params.put("GRANT_OBJ_TYPE", PrivGrantObjectTypeConsts.GRANT_OBJECT_TYPE_POSITION);
        params.put("GRANT_OBJ_ID", posId);
        List<Map> list = grantSvc.selectPrivGrantByGranObj(params);
        return ResponseResult.createSuccessResult(list, "");
    }
        
}

