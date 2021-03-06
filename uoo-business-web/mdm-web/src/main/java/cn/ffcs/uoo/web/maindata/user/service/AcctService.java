package cn.ffcs.uoo.web.maindata.user.service;

import cn.ffcs.uoo.web.maindata.user.dto.TbAccountOrgRel;
import cn.ffcs.uoo.web.maindata.user.service.fallback.AcctServiceHystrix;
import cn.ffcs.uoo.web.maindata.user.vo.AcctOrgVo;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormAcctVo;
import cn.ffcs.uoo.web.maindata.user.vo.SlaveAcctOrgVo;
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
@FeignClient(value = "business-user",configuration = {PersonnelServiceConfiguration.class},fallback = AcctServiceHystrix.class)
public interface AcctService {

    @RequestMapping(value = "/tbAcct/addTbAcct", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object saveAcct(@RequestBody EditFormAcctVo editFormAcctVo);

    @RequestMapping(value = "/tbAcct/deleteTbAcct", method = RequestMethod.DELETE, headers={"Content-Type=application/json"})
    public Object removeAcct(@RequestParam("acctId") Long acctId, @RequestParam("userId") Long userId);

    @RequestMapping(value = "/tbAcct/updateAcct", method = RequestMethod.PUT, headers={"Content-Type=application/json"})
    public Object updateAcct(@RequestBody EditFormAcctVo editFormAcctVo);

    @RequestMapping(value = "/tbAccountOrgRel/removeAcctOrg", method = RequestMethod.DELETE, headers={"Content-Type=application/json"})
    public Object removeAcctOrg(@RequestParam("personnelId") Long personnelId, @RequestParam("acctId") Long acctId, @RequestParam("orgId") Long orgId, @RequestParam("orgTreeId") Long orgTreeId, @RequestParam("userId") Long userId);

    @RequestMapping(value = "/tbAccountOrgRel/addAcctOrg", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object addAcctOrg(@RequestBody TbAccountOrgRel tbAccountOrgRel);

    @RequestMapping(value= "/tbAccountOrgRel/getAcctOrgRelPage", method = RequestMethod.GET, headers={"Content-Type=application/json"} )
    public Object getAcctOrgRelPage(@RequestParam("acctId") Long acctId, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize, @RequestParam("account") String account);

    @RequestMapping(value = "/tbAccountOrgRel/updateAcctOrg", method = RequestMethod.PUT, headers={"Content-Type=application/json"} )
    public Object updateAcctOrg(@RequestBody AcctOrgVo acctOrgVo);

    @RequestMapping(value = "/tbAccountOrgRel/updateAcctOrg", method = RequestMethod.PUT, headers={"Content-Type=application/json"} )
    public Object updateSlaveAcctOrg(@RequestBody SlaveAcctOrgVo acctOrgVo);

    @RequestMapping(value = "/tbAcct/getTbAcct", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getTbAcct(@RequestParam("acct") String acct);
}
