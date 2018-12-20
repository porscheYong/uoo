package cn.ffcs.uoo.web.maindata.personnel.service;

import cn.ffcs.uoo.web.maindata.personnel.service.fallback.FamilyServiceHystrix;
import cn.ffcs.uoo.web.maindata.personnel.service.fallback.HomeStatisticsServiceHystrix;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName HomeStatisticsService
 * @Description
 * @author wudj
 * @date 2018/12/20
 * @Version 1.0.0
 */
@FeignClient(name = "business-personnel", url = "http://192.168.58.146:11200",configuration = {PersonnelServiceConfiguration.class},fallback = HomeStatisticsServiceHystrix.class)
public interface HomeStatisticsService {

    @RequestMapping(value="/homeStatistics/getHomeStatistics",method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getHomeStatistics(@RequestParam("labelType") String labelType );
}
