package cn.ffcs.uoo.web.maindata.organization.service;

import cn.ffcs.uoo.web.maindata.organization.dto.OrgOrgtreeRel;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgContactRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgOrgtreeRelServiceHystrix;
import com.baomidou.mybatisplus.service.IService;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-18
 */
@FeignClient(value = "business-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgOrgtreeRelServiceHystrix.class)
public interface OrgOrgtreeRelService{

    @RequestMapping(value="/orgOrgtreeRel/updateOrgOrgTreeRel",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> getFullBizOrgList(@RequestBody OrgOrgtreeRel orgOrgTreeRel);

}
