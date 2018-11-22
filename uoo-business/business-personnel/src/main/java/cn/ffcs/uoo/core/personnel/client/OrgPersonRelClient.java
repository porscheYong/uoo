package cn.ffcs.uoo.core.personnel.client;

import cn.ffcs.uoo.core.personnel.vo.PsonOrgVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "business-organization")
public interface OrgPersonRelClient {

    @RequestMapping(value = "/orgPersonRel/addOrgPsn", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
    public Object addOrgPsn(@RequestBody List<PsonOrgVo> psonOrgVo);



}
