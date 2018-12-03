package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.dto.FuncComp;
import cn.ffcs.uoo.web.maindata.permission.service.FuncCompService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class FuncCompHystrix implements FuncCompService {

    @Override
    public ResponseResult get(Long id) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listFuncComp(Integer pageNo, Integer pageSize) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addFuncComp(FuncComp funcComp) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updateFuncComp(FuncComp funcComp) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deleteFuncComp(FuncComp funcComp) {
        return ResponseResult.createErrorResult("系统数据异常");
    }
}
