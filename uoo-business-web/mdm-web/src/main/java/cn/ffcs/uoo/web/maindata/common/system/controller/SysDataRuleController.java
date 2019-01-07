package cn.ffcs.uoo.web.maindata.common.system.controller;

import cn.ffcs.uoo.web.maindata.common.system.client.SysDataRuleClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysDataRule;
import cn.ffcs.uoo.web.maindata.common.system.vo.DataRuleRequestVO;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysDataRuleVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 记录权限下相关联的规则，包括横向、纵向的数据维度。
只有需要权限控制的表才进行登记 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
@RestController
@RequestMapping("/system/sysDataRule")
public class SysDataRuleController {

    @Resource
    private SysDataRuleClient sysDataRuleClient;



    @ApiOperation(value = "获取数据权限翻页", notes = "获取数据权限翻页")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getDataRulePage", method = RequestMethod.GET)
    public ResponseResult<Page<SysDataRuleVo>> getDataRulePage(@RequestParam(value = "search",required = false)String search,
                                                               @RequestParam(value = "pageSize",required = false)Integer pageSize,
                                                               @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                               @RequestParam(value = "userId",required = false)Long userId,
                                                               @RequestParam(value = "accout",required = false)String accout){

        return sysDataRuleClient.getDataRulePage(search,pageSize,pageNo,userId,accout);

    }



    @ApiOperation(value = "获取数据权限", notes = "获取数据权限")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getDataRule", method = RequestMethod.GET)
    public ResponseResult<SysDataRuleVo> getDataRule(@RequestParam(value = "id",required = false)String id,
                                                     @RequestParam(value = "userId",required = false)Long userId,
                                                     @RequestParam(value = "accout",required = false)String accout){
        return  sysDataRuleClient.getDataRule(id,userId,accout);
    }

    @ApiOperation(value = "新增数据权限", notes = "新增数据权限")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/addDataRule", method = RequestMethod.POST)
    public ResponseResult<String> addDataRule(@RequestBody SysDataRuleVo sysDataRuleVo){
       return sysDataRuleClient.addDataRule(sysDataRuleVo);
    }


    @ApiOperation(value = "编辑数据权限", notes = "编辑数据权限")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/updateDataRule", method = RequestMethod.POST)
    public ResponseResult<String> updateDataRule(@RequestBody SysDataRuleVo sysDataRuleVo){
        return sysDataRuleClient.updateDataRule(sysDataRuleVo);
    }

}

