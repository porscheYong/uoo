package cn.ffcs.uoo.web.maindata.personnel.service;

import cn.ffcs.uoo.web.maindata.personnel.dto.TbEdu;
import cn.ffcs.uoo.web.maindata.personnel.service.fallback.EduServiceHystrix;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName EduService
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@FeignClient(value = "business-personnel",configuration = {PersonnelServiceConfiguration.class},fallback = EduServiceHystrix.class)
public interface EduService {

    @RequestMapping(value = "/tbEdu/saveTbEdu", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object saveTbEdu(@RequestBody TbEdu tbEdu);

    @RequestMapping(value="/tbEdu/updateTbEdu", method = RequestMethod.PUT, headers={"Content-Type=application/json"})
    public Object updateTbEdu(@RequestBody TbEdu tbEdu);

    @RequestMapping(value="/tbEdu/delTbEdu", method = RequestMethod.DELETE, headers={"Content-Type=application/json"})
    public Object delTbEdu(@RequestParam("eduId") Long eduId,  @RequestParam("userId") Long userId);

    @RequestMapping(value="/tbEdu/getTbEdu", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getTbEdu(@RequestParam("eduId") Long eduId);

    @RequestMapping(value="/tbEdu/getTbEduPage", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getTbEduPage(@RequestParam("personnelId") Long personnelId, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);
}


