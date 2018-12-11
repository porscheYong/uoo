package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.service.PrivGrantService;
import cn.ffcs.uoo.web.maindata.permission.vo.BatchAddPositionPrivGrantVO;
import cn.ffcs.uoo.web.maindata.permission.vo.BatchAddRolePrivGrantVO;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class PrivGrantHystrix implements PrivGrantService {

    @Override
    public ResponseResult batchAddRolePrivGrant(BatchAddRolePrivGrantVO batchAddRolePrivGrantVO) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult batchAddPositionPrivGrant(BatchAddPositionPrivGrantVO batchAddPositionPrivGrantVO) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listAllPrivGrantByRole(long roleId) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listAllPrivGrantByPosition(long posId) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
