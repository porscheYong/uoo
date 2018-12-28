package cn.ffcs.uoo.core.personnel.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "business-user")
public interface UserClient {
    /**
     * 关联 删除 主（从）账号信息
     * @param personnelId
     * @return
     */
    @RequestMapping(value = "/tbAcct/delTbAcctByPsnId", method = RequestMethod.DELETE, headers = {"Content-Type=application/json"})
    public Object removeAcct(@RequestParam("personnelId") Long personnelId, @RequestParam("userId") Long userId);
}
