package cn.ffcs.uoo.core.resource.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.resource.entity.TbBusinessSystem;
import cn.ffcs.uoo.core.resource.service.TbBusinessSystemService;
import cn.ffcs.uoo.core.vo.ResponseResult;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 接入系统表 前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-11-16
 */
@RestController
@RequestMapping("/tbBusinessSystem")
public class TbBusinessSystemController extends BaseController {

    @Autowired
    private TbBusinessSystemService tbBusinessSystemService;

    @ApiOperation(value = "查询目标系统列表", notes = "查询目标系统列表")
    @UooLog(value = "查询目标系统列表", key = "getBusinessSystemList")
    @RequestMapping(value = "/getBusinessSystemList", method = RequestMethod.GET)
    public ResponseResult<List<TbBusinessSystem>> getBusinessSystemList() {
        ResponseResult<List<TbBusinessSystem>> ret = new ResponseResult<List<TbBusinessSystem>>();
        Wrapper busiSys = Condition.create().eq("STATUS_CD","1000");
        List<TbBusinessSystem> list = tbBusinessSystemService.selectList(busiSys);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("查询成功");
        ret.setData(list);
        return ret;
    }
}

