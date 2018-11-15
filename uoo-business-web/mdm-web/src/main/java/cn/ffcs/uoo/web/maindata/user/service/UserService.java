package cn.ffcs.uoo.web.maindata.user.service;

import cn.ffcs.uoo.web.maindata.user.service.fallback.SlaveAcctServiceHystrix;
import cn.ffcs.uoo.web.maindata.user.service.fallback.UserServiceHystrix;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName SlaveAcctService
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@FeignClient(value = "uoo-user",configuration = {PersonnelServiceConfiguration.class},fallback = UserServiceHystrix.class)
public interface UserService {

    @RequestMapping(value = "/tbUser/getUserList", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getUserList(@RequestParam("personnelId") Long personnelId);

    @RequestMapping(value = "/tbUser/getFormAcct", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getFormAcct(@RequestParam("acctId") Long acctId);

    @RequestMapping(value = "/tbUser/getFormSlaveAcct", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getFormSlaveAcct(@RequestParam("acctId") Long acctId);
}
