package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.dto.PrivDataRel;
import cn.ffcs.uoo.web.maindata.permission.service.PrivDataRelService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class PrivDataRelHystrix implements PrivDataRelService {

    @Override
    public ResponseResult addPrivDataRel(PrivDataRel privDataRel) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updatePrivDataRel(PrivDataRel privDataRel) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deletePrivDataRel(PrivDataRel privDataRel) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
