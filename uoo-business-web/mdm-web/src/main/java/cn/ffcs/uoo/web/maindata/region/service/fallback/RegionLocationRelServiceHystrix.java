package cn.ffcs.uoo.web.maindata.region.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.region.entity.TbRegionLocationRel;
import cn.ffcs.uoo.web.maindata.region.service.RegionLocationRelService;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByLoc;
import cn.ffcs.uoo.web.maindata.region.vo.LocRegRelByReg;
import cn.ffcs.uoo.web.maindata.region.vo.ResponseResult;

@Service
public class RegionLocationRelServiceHystrix implements RegionLocationRelService{

    @Override
    public ResponseResult addLocRegRelByLoc(LocRegRelByLoc locRegRelByLoc) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addLocRegRelByReg(LocRegRelByReg locRegRelByReg) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deleteRegionLocationRel(TbRegionLocationRel regionLocationRel) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listRegionLocationRel(Integer pageNo, Integer pageSize) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listRegionLocationRelByReg(Long regId, Integer pageNo, Integer pageSize) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult listRegionLocationRelByLoc(Long locId, Integer pageNo, Integer pageSize) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult addRegionLocationRel(TbRegionLocationRel regLocRel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseResult updateRegionLocationRel(TbRegionLocationRel regLocRel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseResult getRegionLocationRel(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

}
