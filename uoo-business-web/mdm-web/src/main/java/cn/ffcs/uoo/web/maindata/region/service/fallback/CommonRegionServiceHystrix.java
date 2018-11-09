package cn.ffcs.uoo.web.maindata.region.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.region.dto.CommonRegionDTO;
import cn.ffcs.uoo.web.maindata.region.dto.TbCommonRegion;
import cn.ffcs.uoo.web.maindata.region.service.CommonRegionService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;
@Service
public class CommonRegionServiceHystrix implements CommonRegionService{

    @Override
    public ResponseResult getCommonRegion(Long id) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listAllCommonRegion() {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addCommonRegion(CommonRegionDTO commonRegion) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updateCommonRegion(CommonRegionDTO commonRegion) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deleteCommonRegion(TbCommonRegion commonRegion) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
