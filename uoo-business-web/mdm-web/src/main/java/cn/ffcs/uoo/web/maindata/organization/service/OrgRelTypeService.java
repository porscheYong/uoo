package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.organization.dto.OrgRelType;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgRelTypeServiceHystrix;
import com.baomidou.mybatisplus.service.IService;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@FeignClient(value = "uoo-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgRelTypeServiceHystrix.class)
public interface OrgRelTypeService{

    @RequestMapping(value="/orgRelType/getOrgRelTypeList",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<List<OrgRelType>> getOrgRelTypeList(String orgRelCode);


}
