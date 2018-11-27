package cn.ffcs.uoo.web.maindata.permission.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.permission.dto.DataPrivRule;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.DataPrivRuleHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */

@FeignClient(value = "uoo-permission-provider", fallback = DataPrivRuleHystrix.class)
public interface DataPrivRuleService {
    @RequestMapping(value = "/permission/dataPrivRule/addDataPrivRule", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult addDataPrivRule(@RequestBody DataPrivRule dataPrivRule);

    @RequestMapping(value = "/permission/dataPrivRule/updateDataPrivRule", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult updateDataPrivRule(@RequestBody DataPrivRule dataPrivRule);

    @RequestMapping(value = "/permission/dataPrivRule/deleteDataPrivRule", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult deleteDataPrivRule(@RequestBody DataPrivRule dataPrivRule);
}
