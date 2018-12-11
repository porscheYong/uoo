package cn.ffcs.uoo.web.maindata.region.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.region.dto.TbAreaCode;
import cn.ffcs.uoo.web.maindata.region.service.AreaCodeService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;

@Service
public class AreaCodeServiceHystrix implements AreaCodeService{

    @Override
    public ResponseResult getAreaCode(Long id) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listAreaCode(String keyWord,Integer pageNo, Integer pageSize) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addAreaCode(TbAreaCode areaCode) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updateAreaCode(TbAreaCode areaCode) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deleteAreaCode(TbAreaCode areaCode) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
