package cn.ffcs.uoo.system.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.system.service.SysPositionService;
import cn.ffcs.uoo.system.vo.ResponseResult;
import cn.ffcs.uoo.system.vo.SysPositionVo;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Wrapper;
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
@RestController
@RequestMapping("/sysPosition")
public class SysPositionController {

    @Autowired
    private SysPositionService sysPositionService;

    @ApiOperation(value = "查询职位信息", notes = "查询职位信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询职位信息", key = "getPositionTree")
    @RequestMapping(value = "/getPositionTree", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getPositionTree(){
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        List<TreeNodeVo> list = sysPositionService.getPositionTree("1");
        ret.setMessage("查询成功");
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(list);
        return ret;
    }

    @ApiOperation(value = "查询职位下级", notes = "查询职位下级")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询职位下级", key = "getPositionRel")
    @RequestMapping(value = "/getPositionRel", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<SysPositionVo>> getPositionRel(String positionId,String isSearchlower){
        ResponseResult<List<SysPositionVo>> ret = new ResponseResult<>();
        List<SysPositionVo> list = sysPositionService.getPositionRel(positionId,isSearchlower);
        ret.setMessage("查询成功");
        ret.setState(ResponseResult.STATE_OK);
        ret.setData(list);
        return ret;
    }
}

