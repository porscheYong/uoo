package cn.ffcs.uoo.web.maindata.permission.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.web.maindata.permission.dto.BusinessSystem;
import cn.ffcs.uoo.web.maindata.permission.service.fallback.BusinessSystemHystrix;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@FeignClient(value = "business-permission",configuration = {FeignClientConfiguration.class}, fallback = BusinessSystemHystrix.class)
public interface BusinessSystemService {
    @RequestMapping(value = "/permission/businessSystem/listBusinessSystem/{treeId}", method = RequestMethod.GET)
    public ResponseResult<List<BusinessSystem>> listBusinessSystemByOrgTree(@PathVariable(value = "treeId")  Long treeId);
}
