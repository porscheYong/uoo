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
@FeignClient(value = "business-user", configuration = {PersonnelServiceConfiguration.class},fallback = UserServiceHystrix.class)
public interface UserService {

    @RequestMapping(value = "/tbUser/getUserList", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getUserList(@RequestParam("personnelId") Long personnelId, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize, @RequestParam("account") String account);

    @RequestMapping(value = "/tbUser/getFormAcct", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getFormAcct(@RequestParam("acctId") Long acctId, @RequestParam("account") String account);

    @RequestMapping(value = "/tbUser/getFormSlaveAcct", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getFormSlaveAcct(@RequestParam("acctId") Long acctId);

    @RequestMapping(value = "/tbUser/getPsnUser", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object addUser(@RequestParam("userType") String userType, @RequestParam("personnelId") Long personnelId, @RequestParam("account") String account);

    @RequestMapping(value = "/tbUser/getAcctOrgByPsnId", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getAcctOrgByPsnId(@RequestParam("personnelId") Long personnelId, @RequestParam("resourceObjId") Long resourceObjId, @RequestParam("account") String account);
}
