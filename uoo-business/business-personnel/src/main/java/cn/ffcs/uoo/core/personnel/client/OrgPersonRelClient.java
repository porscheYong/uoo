package cn.ffcs.uoo.core.personnel.client;

import cn.ffcs.uoo.core.personnel.util.ResponseResult;
import cn.ffcs.uoo.core.personnel.vo.PsonOrgVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "business-organization")
public interface OrgPersonRelClient {

    @RequestMapping(value = "/orgPersonRel/addOrgPsn", method = RequestMethod.POST, headers = {"Content-Type=application/json"})
    public Object addOrgPsn(@RequestBody List<PsonOrgVo> psonOrgVo);

    @RequestMapping(value = "/orgPersonRel/getPerOrgRelPage", method = RequestMethod.GET, headers = {"Content-Type=application/json"})
    public ResponseResult<Page<PsonOrgVo>> getPerOrgRelPage(@RequestParam("orgId") Integer orgId, @RequestParam("orgTreeId") Long orgTreeId,
                                                            @RequestParam("personnelId") Integer personnelId, @RequestParam("search") String search,
                                                            @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNo") Integer pageNo);



}
