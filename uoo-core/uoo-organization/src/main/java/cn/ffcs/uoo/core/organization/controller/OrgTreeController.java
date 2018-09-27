package cn.ffcs.uoo.core.organization.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.service.OrgTreeService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>
 *  组织树
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Controller
@RequestMapping("/orgTree")
public class OrgTreeController {

    @Autowired
    private OrgTreeService orgTreeService;

    @UooLog(value = "新增组织树信息",key = "addOrgTree")
    @RequestMapping(value = "/addOrgTree",method = RequestMethod.POST)
    public void addOrgTree(@RequestBody OrgTree orgTree){
        orgTreeService.insert(orgTree);
    }

    @UooLog(value = "修改组织树组织树信息",key = "updateOrgTree")
    @RequestMapping(value = "/updateOrgTree",method = RequestMethod.POST)
    public void updateOrgTree(@RequestBody OrgTree orgTree){
        orgTreeService.updateById(orgTree);
    }

    @UooLog(value = "查询组织树列表信息",key = "getOrgTreeList")
    @RequestMapping(value = "/updateOrgTree",method = RequestMethod.POST)
    public List<OrgTree> getOrgTreeList(@RequestBody OrgTree orgTree){
        Wrapper orgTreeWrapper = Condition.create().eq("STATUS_CD","1000");
        List<OrgTree> orgTreeList = orgTreeService.selectList(orgTreeWrapper);
        return orgTreeList;
    }

}

