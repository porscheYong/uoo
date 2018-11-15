package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.dto.Privilege;
import cn.ffcs.uoo.web.maindata.permission.service.PrivilegeService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class PrivilegeHystrix implements PrivilegeService {

    @Override
    public ResponseResult listPrivilege(Integer pageNo, Integer pageSize) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addPrivilege(Privilege privilege) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updatePrivilege(Privilege privilege) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deletePrivilege(Privilege privilege) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
