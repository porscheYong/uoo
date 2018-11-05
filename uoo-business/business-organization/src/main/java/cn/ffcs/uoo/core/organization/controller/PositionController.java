package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.core.organization.entity.OrgType;
import cn.ffcs.uoo.core.organization.entity.Position;
import cn.ffcs.uoo.core.organization.service.PositionService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 前端控制器
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
@RestController
@RequestMapping("/position")
@Api(value = "/position", description = "组织岗位相关操作")
public class PositionController {

    @Autowired
    private PositionService positionService;


    @ApiOperation(value = "查询岗位树信息-web", notes = "查询岗位树信息")
    @ApiImplicitParams({
    })
    @UooLog(value = "查询岗位树信息", key = "getPositionTree")
    @RequestMapping(value = "/getPositionTree", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<List<TreeNodeVo>> getPositionTree(String id, String positiontRootId,
                                                            String positionType,String positionCode)
            throws IOException {
        ResponseResult<List<TreeNodeVo>> ret = new ResponseResult<>();
        List<TreeNodeVo> treeNodeVos = new ArrayList<>();
        treeNodeVos = positionService.selectPositionTree(id,positionType,positionCode,false);
        ret.setState(ResponseResult.STATE_OK);
        ret.setMessage("成功");
        ret.setData(treeNodeVos);
        return ret;
    }
}

