package cn.ffcs.uoo.web.maindata.region.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.region.entity.TbExch;
import cn.ffcs.uoo.web.maindata.region.service.ExchService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
@Service
public class ExchServiceHystrix implements ExchService{

    @Override
    public ResponseResult getExch(Long id) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listExch(Integer pageNo, Integer pageSize) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addExch(TbExch exch) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updateExch(TbExch exch) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deleteExch(TbExch exch) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
