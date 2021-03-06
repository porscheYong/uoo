package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.permission.dto.Roles;
import cn.ffcs.uoo.web.maindata.permission.service.RolesService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class RolesHystrix implements RolesService {

    @Override
    public ResponseResult get(Long id) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult<Page<Roles>> listPageRoles(Integer pageNo, Integer pageSize) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listRoles() {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult removeTbRoles(Roles role) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updateTbRoles(Roles tbRoles) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addTbRoles(Roles role) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult getRolesPermission(Long systemInfoId,Long acctId) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult getPermissionMenu(Long systemInfoId,Long acctId) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
