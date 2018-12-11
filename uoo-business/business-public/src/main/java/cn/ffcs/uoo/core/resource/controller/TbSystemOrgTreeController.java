package cn.ffcs.uoo.core.resource.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.resource.entity.TbSystemIndividuationRule;
import cn.ffcs.uoo.core.resource.entity.TbSystemOrgTree;
import cn.ffcs.uoo.core.resource.service.TbSystemIndividuationRuleService;
import cn.ffcs.uoo.core.resource.service.TbSystemOrgTreeService;
import cn.ffcs.uoo.core.resource.vo.SystemIndividuaRuleVo;
import cn.ffcs.uoo.core.resource.vo.SystemOrgTreeRuleVo;
import cn.ffcs.uoo.core.vo.ResponseResult;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 接入系统组织树引用配置 前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-11-16
 */
@Api(description = "下发规则",value = "Dictionary")
@RestController
@RequestMapping("/tbSystemOrgTree")
public class TbSystemOrgTreeController extends BaseController {

    @Autowired
    private TbSystemOrgTreeService tbSystemOrgTreeService;

    private TbSystemIndividuationRuleService tbSystemIndividuationRuleService;


    @ApiOperation(value = "查询下发规则翻页", notes = "查询下发规则翻页")
    @UooLog(value = "查询下发规则翻页", key = "selectSystemOrgTreeRulePage")
    @RequestMapping(value = "/selectSystemOrgTreeRulePage", method = RequestMethod.GET)
    public ResponseResult<Page<SystemOrgTreeRuleVo>> selectSystemOrgTreeRulePage(@RequestBody SystemOrgTreeRuleVo systemOrgTreeRuleVo) {
        ResponseResult<Page<SystemOrgTreeRuleVo>> ret = new ResponseResult<Page<SystemOrgTreeRuleVo>>();
        Page<SystemOrgTreeRuleVo> page = tbSystemOrgTreeService.selectSystemOrgTreeRulePage(systemOrgTreeRuleVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        ret.setData(page);
        return ret;
    }


    @ApiOperation(value = "查询规则明细", notes = "查询规则明细")
    @UooLog(value = "查询规则明细", key = "getIndividuationRule")
    @RequestMapping(value = "/getIndividuationRule", method = RequestMethod.GET)
    public ResponseResult<List<SystemOrgTreeRuleVo>> getIndividuationRule(SystemOrgTreeRuleVo systemOrgTreeRuleVo) {
        ResponseResult<List<SystemOrgTreeRuleVo>> ret = new ResponseResult<List<SystemOrgTreeRuleVo>>();
        List<SystemOrgTreeRuleVo> list = tbSystemOrgTreeService.getIndividuationRule(systemOrgTreeRuleVo);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        ret.setData(list);
        return ret;
    }


    @ApiOperation(value = "规则保存", notes = "规则保存")
    @UooLog(value = "规则保存", key = "addSystemIndividuaRule")
    @RequestMapping(value = "/addSystemIndividuaRule", method = RequestMethod.GET)
    public ResponseResult<Void> addSystemIndividuaRule(SystemOrgTreeRuleVo systemOrgTreeRuleVo) {
        ResponseResult<Void> ret = new ResponseResult<Void>();
        TbSystemOrgTree tbSystemOrgTree = new TbSystemOrgTree();
        Long systemOrgTreeId = tbSystemOrgTreeService.getId();
        tbSystemOrgTree.setSystemOrgTreeId(systemOrgTreeId);
        tbSystemOrgTree.setBusinessSystemId(systemOrgTreeRuleVo.getBusinessSystemId());
        tbSystemOrgTree.setOrgTreeId(systemOrgTreeRuleVo.getOrgTreeId());
        tbSystemOrgTree.setIncludePsn(Integer.valueOf(systemOrgTreeRuleVo.getIncludePsn()));
        tbSystemOrgTree.setIncludeSlaveAcct(Integer.valueOf(systemOrgTreeRuleVo.getIncludeSlaveAcct()));
        tbSystemOrgTreeService.add(tbSystemOrgTree);

        List<SystemIndividuaRuleVo> list = systemOrgTreeRuleVo.getSystemIndividuaRuleList();
        if(list!=null){
            for(SystemIndividuaRuleVo vo : list){
                TbSystemIndividuationRule tbSystemIndividuationRule = new TbSystemIndividuationRule();
                Long sysIndiviId = tbSystemIndividuationRuleService.getId();
                tbSystemIndividuationRule.setIndividuationRuleId(sysIndiviId);
                tbSystemIndividuationRule.setSystemOrgTreeId(systemOrgTreeId);
                tbSystemIndividuationRule.setAttrValue(vo.getAttrValue());
                tbSystemIndividuationRule.setBusiObjAttrId(new Long(vo.getBusiObjAttrId()));
                tbSystemIndividuationRule.setBusiObjId(new Long(vo.getBusiObjId()));
                tbSystemIndividuationRule.setRuleOperator(vo.getRuleOperator());
                tbSystemIndividuationRuleService.add(tbSystemIndividuationRule);
            }
        }
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        return ret;
    }
}

