package cn.ffcs.uoo.core.personnel.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "business-user-test")
public interface UserClient {

    @RequestMapping(value = "/tbAcct/deleteTbAcct", method = RequestMethod.DELETE, headers = {"Content-Type=application/json"})
    public Object removeAcct(@RequestParam("acctId") Long acctId);
}
