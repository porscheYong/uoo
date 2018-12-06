package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.permission.dto.UserRole;
import cn.ffcs.uoo.web.maindata.permission.service.UserRoleService;
import cn.ffcs.uoo.web.maindata.permission.vo.BatchAddRoleUserVO;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.permission.vo.UserPersonnelVo;
@Service
public class UserRoleHystrix implements UserRoleService {

    @Override
    public ResponseResult removeTbUserRole(UserRole userRole) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addTbUserRole(UserRole tbUserRole) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addTbUserRoleBatch(BatchAddRoleUserVO batchAddRoleUserVO) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult<List<UserPersonnelVo>> getUserPersonnelVoPage(Integer pageNo, Integer pageSize, Long roleId) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
