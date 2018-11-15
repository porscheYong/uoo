package cn.ffcs.uoo.web.maindata.user.service;

import cn.ffcs.uoo.web.maindata.user.service.fallback.AcctServiceHystrix;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormAcctVo;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName AcctService
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@FeignClient(value = "uoo-user",configuration = {PersonnelServiceConfiguration.class},fallback = AcctServiceHystrix.class)
public interface AcctService {

    @RequestMapping(value = "/tbAcct/addTbAcct", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object saveAcct(@RequestBody EditFormAcctVo editFormAcctVo);

    @RequestMapping(value = "/tbAcct/deleteTbAcct", method = RequestMethod.DELETE, headers={"Content-Type=application/json"})
    public Object removeAcct(@RequestParam("acctId") Long acctId);

    @RequestMapping(value = "/tbAcct/updateAcct", method = RequestMethod.PUT, headers={"Content-Type=application/json"})
    public Object updateAcct(@RequestBody EditFormAcctVo editFormAcctVo);
}
