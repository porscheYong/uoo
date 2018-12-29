package cn.ffcs.uoo.core.user.client;

import cn.ffcs.uoo.core.user.util.ResponseResult;
import cn.ffcs.uoo.core.user.vo.DataRuleRequestVO;
import cn.ffcs.uoo.core.user.vo.SysDataRule;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wudj
 * @since 2018/12/28
 */
@Service
@FeignClient(value = "common-system")
public interface SystemClient {

    @RequestMapping(value = "/system/sysDataRule/getDataRuleByAccout", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<List<SysDataRule>> getDataRuleByAccout(@RequestBody DataRuleRequestVO requestVo);

}
