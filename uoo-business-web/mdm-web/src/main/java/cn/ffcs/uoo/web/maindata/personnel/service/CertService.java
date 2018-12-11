package cn.ffcs.uoo.web.maindata.personnel.service;

import cn.ffcs.uoo.web.maindata.personnel.service.fallback.CertServiceHystrix;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName CertService
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@FeignClient(value = "business-personnel",configuration = {PersonnelServiceConfiguration.class},fallback = CertServiceHystrix.class)
public interface CertService {

    @RequestMapping(value="/cert/getCertInfo", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getCertInfo(@RequestParam("keyWord") String keyWord, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);
}
