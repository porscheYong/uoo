package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.organization.dto.OrgRelType;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgRelTypeServiceHystrix;
import com.baomidou.mybatisplus.service.IService;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
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
@FeignClient(name = "business-organization", url = "http://134.96.253.222:11100",configuration = {PersonnelServiceConfiguration.class},fallback = OrgRelTypeServiceHystrix.class)
public interface OrgRelTypeService{

    @RequestMapping(value="/orgRelType/getOrgRelTypeList",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<List<OrgRelType>> getOrgRelTypeList(@RequestParam(value = "orgRelCode",required = false)String orgRelCode);

    @RequestMapping(value="/orgRelType/getOrgRelTypeTree",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<List<TreeNodeVo>> getOrgRelTypeTree(@RequestParam(value = "refCode",required = false)String refCode);
}
