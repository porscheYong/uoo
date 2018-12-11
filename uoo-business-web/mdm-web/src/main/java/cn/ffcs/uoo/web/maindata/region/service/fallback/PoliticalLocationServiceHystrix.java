package cn.ffcs.uoo.web.maindata.region.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.region.dto.TbPoliticalLocation;
import cn.ffcs.uoo.web.maindata.region.service.PoliticalLocationService;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;

@Service
public class PoliticalLocationServiceHystrix implements PoliticalLocationService{

    @Override
    public ResponseResult getPoliticalLocation(Long id) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listAllPoliticalLocation() {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addPoliticalLocation(TbPoliticalLocation polLoc) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updatePoliticalLocation(TbPoliticalLocation polLoc) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deletePoliticalLocation(TbPoliticalLocation polLoc) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult getChildPoliticalLocationInfo(Long id) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult getTreePoliticalLocation(Long id) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
