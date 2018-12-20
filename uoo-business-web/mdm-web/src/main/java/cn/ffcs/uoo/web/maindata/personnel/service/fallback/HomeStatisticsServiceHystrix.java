package cn.ffcs.uoo.web.maindata.personnel.service.fallback;

import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.personnel.service.HomeStatisticsService;
import org.springframework.stereotype.Component;

@Component
public class HomeStatisticsServiceHystrix implements HomeStatisticsService {
    @Override
    public Object getHomeStatistics(String labelType) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
