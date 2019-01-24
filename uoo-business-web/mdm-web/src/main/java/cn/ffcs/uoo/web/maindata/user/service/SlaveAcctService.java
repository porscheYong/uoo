package cn.ffcs.uoo.web.maindata.user.service;

import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import cn.ffcs.uoo.web.maindata.personnel.service.fallback.PsnjobServiceHystrix;
import cn.ffcs.uoo.web.maindata.user.dto.TbAcctExt;
import cn.ffcs.uoo.web.maindata.user.service.fallback.SlaveAcctServiceHystrix;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormSlaveAcctVo;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
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
@FeignClient(value = "business-user", configuration = {PersonnelServiceConfiguration.class},fallback = SlaveAcctServiceHystrix.class)
public interface SlaveAcctService {

    @RequestMapping(value = "/tbSlaveAcct/addTbSlaveAcct", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object saveSlaveAcct(@RequestBody EditFormSlaveAcctVo editFormSlaveAcctVo);

    @RequestMapping(value = "/tbSlaveAcct/delTbSlaveAcct", method = RequestMethod.DELETE, headers={"Content-Type=application/json"})
    public Object delTbSlaveAcct(@RequestParam("slaveAcctId") Long slaveAcctId, @RequestParam("userId") Long userId);

    @RequestMapping(value = "/tbSlaveAcct/updateTbSlaveAcct", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object updateTbSlaveAcct(@RequestBody EditFormSlaveAcctVo editFormSlaveAcctVo);

    @RequestMapping(value = "tbAcctExt/addOrUpdateTbAcctExt", method = RequestMethod.POST, headers={"Content-Type=application/json"})
    public Object addOrUpdateTbAcctExt(@RequestBody TbAcctExt tbAcctExt);

    @RequestMapping(value = "/tbAcctExt/delTbAcctExt", method = RequestMethod.DELETE, headers={"Content-Type=application/json"})
    public Object delTbAcctExt(@RequestBody TbAcctExt tbAcctExt);





}
