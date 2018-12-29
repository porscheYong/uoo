package cn.ffcs.uoo.web.maindata.personnel.service;

import cn.ffcs.uoo.web.maindata.personnel.dto.TbPsnjob;
import cn.ffcs.uoo.web.maindata.personnel.service.fallback.PsnjobServiceHystrix;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName PersonnelController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@FeignClient(value = "business-personnel",configuration = {PersonnelServiceConfiguration.class},fallback = PsnjobServiceHystrix.class)
public interface PsnjobService {

    @RequestMapping(value="/tbPsnjob/saveTbPsnjob", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object saveTbPsnjob(@RequestBody TbPsnjob tbPsnjob);

    @RequestMapping(value="/tbPsnjob/updateTbPsnjob", method = RequestMethod.PUT, headers={"Content-Type=application/json"})
    public Object updateTbPsnjob(@RequestBody TbPsnjob tbPsnjob);

    @RequestMapping(value="/tbPsnjob/delTbPsnjob", method = RequestMethod.DELETE, headers={"Content-Type=application/json"})
    public Object delTbPsnjob(@RequestParam("psnjobId") Long psnjobId, @RequestParam("userId") Long userId );

    @RequestMapping(value="/tbPsnjob/getTbPsnjob", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getTbPsnjob(@RequestParam("psnjobId") Long psnjobId);

    @RequestMapping(value="/tbPsnjob/getTbPsnjobPage",method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getTbPsnjobPage(@RequestParam("personnelId") Long personnelId, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

}
