package cn.ffcs.uoo.web.maindata.personnel.service;

import cn.ffcs.uoo.web.maindata.personnel.dto.TbFamily;
import cn.ffcs.uoo.web.maindata.personnel.service.fallback.FamilyServiceHystrix;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName FamilyService
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@FeignClient(value = "business-personnel",configuration = {PersonnelServiceConfiguration.class},fallback = FamilyServiceHystrix.class)
public interface FamilyService {

    @RequestMapping(value = "/family/saveTbFamily", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object saveTbFamily(@RequestBody TbFamily tbFamily);

    @RequestMapping(value = "/family/updateTbFamily", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object updateTbFamily(@RequestBody TbFamily tbFamily);

    @RequestMapping(value="/family/delTbFamily",method = RequestMethod.DELETE, headers={"Content-Type=application/json"})
    public Object delTbFamily(@RequestParam("familyId") Long familyId );

    @RequestMapping(value="/family/getTbFamily",method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getTbFamily(@RequestParam("familyId") Long familyId );

    @RequestMapping(value="/family/getTbFamilyPage",method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getTbFamilyPage(@RequestParam("personnelId") Long personnelId, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);
}
