package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.core.personnel.entity.TbFamily;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.service.TbFamilyService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.vo.TbFamilyVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.ffcs.uoo.base.controller.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
@RestController
@RequestMapping("/tbFamily")
public class TbFamilyController extends BaseController {

    @Autowired
    private TbFamilyService tbFamilyService;

    @ApiOperation(value = "新增家庭成员信息",notes = "家庭成员信息新增")
    @ApiImplicitParam(name = "tbFamilyVo",value = "新增家庭成员信息",required = true,dataType = "TbFamilyVo")
    @UooLog(value = "新增家庭成员信息",key = "addTbFamily")
    @RequestMapping(value = "/addTbFamily",method = RequestMethod.POST)
    public Object addTbFamily(@RequestBody TbFamilyVo tbFamilyVo){
        TbFamily tbFamily = new TbFamily();
        BeanUtils.copyProperties(tbFamilyVo, tbFamily);
        tbFamilyService.save(tbFamily);
        int pageSize = tbFamilyVo.getPageSize() == 0 ? 12 : tbFamilyVo.getPageSize();
        Wrapper wrapper= Condition.create().eq(true,"PERSONNEL_ID", tbFamilyVo.getPersonnelId());
        Page<TbFamilyVo> page = tbFamilyService.selectPage(new Page<TbFamily>(tbFamilyVo.getPageNo(), pageSize), wrapper);

        return ResultUtils.success(page);
    }
}

