package cn.ffcs.uoo.web.maindata.common.system.client;


import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysFileClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysOrganizationClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysFileVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysOrganizationVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@FeignClient(value = "common-system",configuration = {FeignClientProperties.FeignClientConfiguration.class},fallback = SysFileClientHystrix.class)
public interface SysFileClient {


    @RequestMapping(value = "/sysFile/getSysFilePage", method = RequestMethod.GET)
    public ResponseResult<Page<SysFileVo>> getSysFilePage(@RequestParam(value = "search",required = false)String search,
                                                          @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                          @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                          @RequestParam(value = "userId",required = false)Long userId,
                                                          @RequestParam(value = "accout",required = false)String accout);


    @RequestMapping(value = "/sysFile/getSysFile", method = RequestMethod.GET)
    public ResponseResult<SysFileVo> getSysFile(@RequestParam(value = "id",required = false)String id,
                                                @RequestParam(value = "userId",required = false)Long userId,
                                                @RequestParam(value = "accout",required = false)String accout);


    @RequestMapping(value = "/sysFile/addSysFile", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> addSysFile(@RequestBody SysFileVo sysFileVo);



    @RequestMapping(value = "/sysFile/updateSysFile", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> updateSysFile(@RequestBody SysFileVo sysFileVo);

    @RequestMapping(value = "/sysFile/deleteSysFile", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> deleteSysFile(@RequestBody SysFileVo sysFileVo);

}

