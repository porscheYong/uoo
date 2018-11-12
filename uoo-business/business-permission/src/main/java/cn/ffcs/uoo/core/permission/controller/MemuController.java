package cn.ffcs.uoo.core.permission.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.permission.service.MemuService;
import cn.ffcs.uoo.core.permission.vo.ResponseResult;
import cn.ffcs.uoo.core.permission.vo.TreeNodeVo;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@Controller
@RequestMapping("/memu")
public class MemuController {

    @Autowired
    private MemuService memuService;

    @ApiOperation(value = "查询组织分页-web", notes = "查询组织分页")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询组织分页", key = "getOrgRelPage")
    @RequestMapping(value = "/getOrgPage", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult getMenuTree(String id){
        ResponseResult ret = new ResponseResult();
        //List<TreeNodeVo> list = memuService.selectMenuTree(id);
       // ret.setData(list);
        ret.setState(ResponseResult.STATE_OK);
        return ret;
    }
}

