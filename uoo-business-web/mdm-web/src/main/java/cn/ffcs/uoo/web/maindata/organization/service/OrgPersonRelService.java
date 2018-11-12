package cn.ffcs.uoo.web.maindata.organization.service;


import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgContactRelServiceHystrix;
import cn.ffcs.uoo.web.maindata.organization.service.fallback.OrgPersonRelServiceHystrix;
import com.baomidou.mybatisplus.plugins.Page;
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
 * @since 2018-10-21
 */
@FeignClient(value = "uoo-organization",configuration = {PersonnelServiceConfiguration.class},fallback = OrgPersonRelServiceHystrix.class)
public interface OrgPersonRelService{

    @RequestMapping(value="/orgPersonRel/addOrgPsn",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> addOrgPsn(PsonOrgVo psonOrgVo);

    @RequestMapping(value="/orgPersonRel/updateOrgPsn",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> updateOrgPsn(PsonOrgVo psonOrgVo);

    @RequestMapping(value="/orgPersonRel/deleteOrgPsn",method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> deleteOrgPsn(PsonOrgVo psonOrgVo);

    @RequestMapping(value="/orgPersonRel/getPerOrgRelList",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<List<PsonOrgVo>> getPerOrgRelList(PsonOrgVo psonOrgVo);

    @RequestMapping(value="/orgPersonRel/getPerOrgRelPage",method = RequestMethod.GET,headers={"Content-Type=application/json"})
    public ResponseResult<Page<PsonOrgVo>> getPerOrgRelPage(PsonOrgVo psonOrgVo);
}
