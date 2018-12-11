package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.dto.FuncMenu;
import cn.ffcs.uoo.web.maindata.permission.service.FuncMenuService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class FuncMenuHystrix implements FuncMenuService {

    @Override
    public ResponseResult getFuncMenuPage() {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addFuncMenu(FuncMenu funcMenu) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updateFuncMenu(FuncMenu funcMenu) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deleteFuncMenu(FuncMenu funcMenu) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
