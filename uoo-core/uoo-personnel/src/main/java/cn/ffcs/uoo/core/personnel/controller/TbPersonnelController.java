package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.vo.TbPersonnelVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-09-06
 */
@Api(description="人员",value="Personnel")
@RestController
@RequestMapping("/personnel")
public class TbPersonnelController extends BaseController {

    @Autowired
    private TbPersonnelService tbPersonnelService;

    @ApiOperation(value = "人员查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页的序号", required = true, dataType = "Integer",paramType="path"),
            @ApiImplicitParam(name = "pageSize", value = "每页的大小", dataType = "Integer",paramType="path",defaultValue = "12")
    })
    @UooLog(value = "人员查询", key = "getPersonnel")
    @RequestMapping(value = "/getPage/pageNo={pageNo}&pageSize={pageSize}",method = RequestMethod.GET)
    public Page<TbPersonnel> getPersonnel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize) {

        pageNo = pageNo==null?0:pageNo;
        pageSize = pageSize==null?20:pageSize;
        return tbPersonnelService.selectPage(new Page<TbPersonnel>(pageNo, pageSize));
    }

    @ApiOperation(value = "人员条件查询", notes = "条件分页查询")
    @ApiImplicitParam(name = "tbPersonnelVo", value = "人员条件VO", required = true, dataType = "TbPersonnelVo")
    @UooLog(value = "人员条件查询", key = "getPersonnelCondition")
    @RequestMapping(value = "/getPage/",method = RequestMethod.POST)
    public Page<TbPersonnel> getPersonnelCondition(@RequestBody TbPersonnelVo tbPersonnelVo) {
        int pageSize = tbPersonnelVo.getPageSize()==0?12:tbPersonnelVo.getPageSize();

        Wrapper wrapper= Condition.create().eq(StringUtils.isNotEmpty(tbPersonnelVo.getPsnNbr()),"PSN_NBR",tbPersonnelVo.getPsnNbr());

        return tbPersonnelService.selectPage(new Page<TbPersonnel>(tbPersonnelVo.getPageNo(), pageSize),wrapper);
    }

    @UooLog(value = "测试条件查询", key = "testPersonnel")
    @RequestMapping(value = "/testPage",method = RequestMethod.GET)
    public Page<TbPersonnel> testPersonnel() {
        return tbPersonnelService.selectPage(new Page<TbPersonnel>(0, 12));
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test() {
        return "Hello Uoo";
    }
}

