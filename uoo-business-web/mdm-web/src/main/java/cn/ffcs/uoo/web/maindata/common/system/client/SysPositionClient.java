package cn.ffcs.uoo.web.maindata.common.system.client;



import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysOrganizationClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.client.fallback.SysPositionClientHystrix;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysPositionVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientProperties;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 根据不同部门的工作性质、责任轻重、难易程度和所需资格条件等进行分类，在平台上，不对职位进行过细的区分 前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@FeignClient(value = "common-system",configuration = {FeignClientProperties.FeignClientConfiguration.class},fallback = SysPositionClientHystrix.class)
public interface SysPositionClient {

    @RequestMapping(value = "/sysPosition/getPositionTree", method = RequestMethod.GET)
    public ResponseResult<List<TreeNodeVo>> getPositionTree(@RequestParam(value = "id",required = false)String id,
                                                            @RequestParam(value = "isSync",required = false)boolean isSync,
                                                            @RequestParam(value = "userId",required = false)Long userId,
                                                            @RequestParam(value = "accout",required = false)String accout);

    @RequestMapping(value = "/sysPosition/getPositionRelPage", method = RequestMethod.GET)
    public ResponseResult<Page<SysPositionVo>> getPositionRelPage(@RequestParam(value = "positionId",required = false)String positionId,
                                                                  @RequestParam(value = "search",required = false)String search,
                                                                  @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                                  @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                                  @RequestParam(value = "isSearchlower",required = false)String isSearchlower,
                                                                  @RequestParam(value = "userId",required = false)Long userId,
                                                                  @RequestParam(value = "accout",required = false)String accout);


    @RequestMapping(value = "/sysPosition/getRolesByPositionId", method = RequestMethod.GET)
    public ResponseResult<HashMap<String,String>> getRolesByPositionId(@RequestParam(value = "positionId",required = false)String positionId);


    @RequestMapping(value = "/sysPosition/getPosition", method = RequestMethod.GET)
    public ResponseResult<SysPositionVo> getPosition(@RequestParam(value = "id",required = false)String id);



    @RequestMapping(value = "/sysPosition/updatePosition", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> updatePosition(@RequestBody SysPositionVo sysPositionVo);


    @RequestMapping(value = "/sysPosition/addPosition", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<TreeNodeVo> addPosition(@RequestBody SysPositionVo pos);

    @RequestMapping(value = "/sysPosition/deletePosition", method = RequestMethod.POST,headers={"Content-Type=application/json"})
    public ResponseResult<String> deletePosition(@RequestBody SysPositionVo pos);
}

