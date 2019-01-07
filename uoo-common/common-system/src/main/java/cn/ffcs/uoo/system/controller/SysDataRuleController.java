package cn.ffcs.uoo.system.controller;


import java.util.HashMap;
import java.util.List;

import cn.ffcs.uoo.system.entity.SysFile;
import cn.ffcs.uoo.system.entity.SysTable;
import cn.ffcs.uoo.system.entity.SysTableColumn;
import cn.ffcs.uoo.system.service.ISysTableColumnService;
import cn.ffcs.uoo.system.service.ISysTableService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.SysDataRuleVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.entity.SysDataRule;
import cn.ffcs.uoo.system.service.ISysDataRuleService;
import cn.ffcs.uoo.system.vo.DataRuleRequestVO;
import cn.ffcs.uoo.system.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

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
    ISysDataRuleService dataRuleSvc;

    @Resource
    private ISysTableService iSysTableService;

    @Resource
    private ISysTableColumnService iSysTableColumnService;
    
    @ApiOperation(value = "获取单个用户的数据权限", notes = "获取单个用户的数据权限")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "requestVo", value = "requestVo", required = true, dataType = "DataRuleRequestVO"  ),
    })
    @UooLog(key="getDataRuleByAccout",value="获取单个用户的数据权限")
    @Transactional
    @RequestMapping(value = "/getDataRuleByAccout", method = RequestMethod.POST)
    public ResponseResult<List<SysDataRule>> getDataRuleByAccout(@RequestBody DataRuleRequestVO requestVo){
        if(requestVo.getTableNames()==null||requestVo.getTableNames().isEmpty()){
            return ResponseResult.createErrorResult("表名不能为空");
        }
        if(StringUtils.isBlank(requestVo.getAccout())){
            return ResponseResult.createErrorResult("账号名不能为空");
        }
        HashMap<String, Object> map=new HashMap<>();
        map.put("accout", requestVo.getAccout());
        map.put("tableNames", requestVo.getTableNames());
        return ResponseResult.createSuccessResult(dataRuleSvc.listByAccout(map), "");
    }

    @ApiOperation(value = "获取数据权限翻页", notes = "获取数据权限翻页")
    @ApiImplicitParams({
    })
    @UooLog(key="getDataRulePage",value="获取数据权限翻页")
    @Transactional
    @RequestMapping(value = "/getDataRulePage", method = RequestMethod.GET)
    public ResponseResult<Page<SysDataRuleVo>> getDataRulePage(String search,Integer pageSize,Integer pageNo,
                                                               Long userId, String accout){
        ResponseResult<Page<SysDataRuleVo>> ret = new ResponseResult<Page<SysDataRuleVo>>();

        Page<SysDataRuleVo> list = dataRuleSvc.getDataRulePage(search,pageSize,pageNo);
        ret.setData(list);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }



    @ApiOperation(value = "获取数据权限", notes = "获取数据权限")
    @ApiImplicitParams({
    })
    @UooLog(key="getDataRule",value="获取数据权限")
    @Transactional
    @RequestMapping(value = "/getDataRule", method = RequestMethod.GET)
    public ResponseResult<SysDataRuleVo> getDataRule(String id,
                                                               Long userId, String accout){
        ResponseResult<SysDataRuleVo> ret = new ResponseResult<SysDataRuleVo>();
        Wrapper sysDataWrapper = Condition.create()
                .eq("DATA_RULE_ID",id)
                .eq("STATUS_CD","1000");
        SysDataRule sysDataRule = dataRuleSvc.selectOne(sysDataWrapper);
        SysDataRuleVo vo = new SysDataRuleVo();
        BeanUtils.copyProperties(sysDataRule, vo);
        ret.setMessage("系统文件查询成功");
        ret.setData(vo);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }

    @ApiOperation(value = "新增数据权限", notes = "新增数据权限")
    @ApiImplicitParams({
    })
    @UooLog(key="addDataRule",value="新增数据权限")
    @Transactional
    @RequestMapping(value = "/addDataRule", method = RequestMethod.POST)
    public ResponseResult<String> addDataRule(SysDataRuleVo sysDataRuleVo){
        ResponseResult<String> ret = new ResponseResult<String>();

        SysDataRule vo = new SysDataRule();
        BeanUtils.copyProperties(sysDataRuleVo, vo);
        Long id = dataRuleSvc.getId();
        vo.setDataRuleId(id);
        dataRuleSvc.add(vo);
        ret.setMessage("新增成功");
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }


    @ApiOperation(value = "编辑数据权限", notes = "编辑数据权限")
    @ApiImplicitParams({
    })
    @UooLog(key="updateDataRule",value="编辑数据权限")
    @Transactional
    @RequestMapping(value = "/updateDataRule", method = RequestMethod.POST)
    public ResponseResult<String> updateDataRule(SysDataRuleVo sysDataRuleVo){
        ResponseResult<String> ret = new ResponseResult<String>();

        SysDataRule vo = new SysDataRule();
        BeanUtils.copyProperties(sysDataRuleVo, vo);
        dataRuleSvc.update(vo);
        ret.setMessage("编辑成功");
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }



    @ApiOperation(value = " ", notes = "获取表名")
    @ApiImplicitParams({
    })
    @UooLog(key="getTabName",value="获取表名")
    @Transactional
    @RequestMapping(value = "/getTab", method = RequestMethod.GET)
    public ResponseResult<List<SysTable>> getTab(){
        ResponseResult<List<SysTable>> ret = new ResponseResult<List<SysTable>>();
        Wrapper sysTabWrapper = Condition.create().eq("STATUS_CD","1000");
        List<SysTable> systabList = iSysTableService.selectList(sysTabWrapper);
        ret.setData(systabList);
        ret.setMessage("查询成功");
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }

    @ApiOperation(value = " ", notes = "获取列名")
    @ApiImplicitParams({
    })
    @UooLog(key="getTabColumn",value="获取列名")
    @Transactional
    @RequestMapping(value = "/getTabColumn", method = RequestMethod.GET)
    public ResponseResult<List<SysTableColumn>> getTabColumn(String tabId){
        ResponseResult<List<SysTableColumn>> ret = new ResponseResult<List<SysTableColumn>>();
        if(StrUtil.isNullOrEmpty(tabId)){
            ret.setState(ResponseResult.STATE_ERROR);
            ret.setMessage("表标识不能为空");
            return ret;
        }
        Wrapper sysTabCulWrapper = Condition.create()
                .eq("TAB_ID",tabId)
                .eq("STATUS_CD","1000");
        List<SysTableColumn> systabculList = iSysTableColumnService.selectList(sysTabCulWrapper);
        ret.setData(systabculList);
        ret.setMessage("查询成功");
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }

}

