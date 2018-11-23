package cn.ffcs.uoo.core.organization.Api.service;


import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@FeignClient(value = "business-organization")
public interface TestService {

    @RequestMapping(value="/orgTree/getOrgTreeList", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public ResponseResult<List<OrgTree>> getOrgTreeList(@RequestParam("orgId")String orgId);

    @RequestMapping(value="/orgPersonRel/addOrgPsn",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> addOrgPsn(@RequestBody List<PsonOrgVo> psonOrgVo);
}
