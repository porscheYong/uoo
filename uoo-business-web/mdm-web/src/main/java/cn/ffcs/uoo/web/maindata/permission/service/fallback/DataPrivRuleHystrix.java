package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.dto.DataPrivRule;
import cn.ffcs.uoo.web.maindata.permission.service.DataPrivRuleService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class DataPrivRuleHystrix implements DataPrivRuleService {

    @Override
    public ResponseResult addDataPrivRule(DataPrivRule dataPrivRule) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult updateDataPrivRule(DataPrivRule dataPrivRule) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

    @Override
    public ResponseResult deleteDataPrivRule(DataPrivRule dataPrivRule) {
        return ResponseResult.createErrorResult("系统数据异常");
    }

}
