package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgContactRelServiceHystrix;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import common.config.PersonnelServiceConfiguration;
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
 * @since 2018-10-21
 */
//@FeignClient(name = "business-organization", url = "http://134.96.253.222:11100",configuration = {PersonnelServiceConfiguration.class},fallback = OrgContactRelServiceHystrix.class)
@FeignClient(value = "business-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgContactRelServiceHystrix.class)
public interface OrgContactRelService {


    /**
     * 获取组织联系人翻页
     * @param orgId
     * @param pageSize
     * @param pageNo
     * @return
     */
    @RequestMapping(value="/orgContactRel/getOrgContactPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<Page<PsonOrgVo>> getOrgContactPage(@RequestParam(value = "orgId",required = false)String orgId,
                                                             @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                             @RequestParam(value = "pageNo",required = false)Integer pageNo);

}
