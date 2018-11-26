package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.dto.PrivFuncRel;
import cn.ffcs.uoo.web.maindata.permission.service.PrivFuncRelService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class PrivFuncRelHystrix implements PrivFuncRelService {

    @Override
    public ResponseResult getPrivFuncRel(Long id) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listPrivFuncRel(Integer pageNo, Integer pageSize) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addPrivFuncRel(PrivFuncRel privFuncRel) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updatePrivFuncRel(PrivFuncRel privFuncRel) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deleteFuncComp(PrivFuncRel privFuncRel) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
