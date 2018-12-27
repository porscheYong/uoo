package cn.ffcs.uoo.core.organization.Api.service;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-12-26
 */

import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.DataRuleRequestVO;
import cn.ffcs.uoo.core.organization.vo.SysDataRule;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/12/26
 */
@Service
@FeignClient(value = "common-system")
public interface SystemService {

    @RequestMapping(value = "/system/sysDataRule/getDataRuleByAccout", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<List<SysDataRule>> getDataRuleByAccout(@RequestBody DataRuleRequestVO requestVo);

}
