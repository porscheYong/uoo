package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.personnel.entity.TbContact;
import cn.ffcs.uoo.core.personnel.service.TbContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 联系方式 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-13
 */
@Api(description = "联系方式",value = "Contact")
@RestController
@RequestMapping("/contact")
public class TbContactController extends BaseController {
    @Autowired
    private TbContactService tbContactService;

    @ApiOperation(value = "新增联系方式",notes = "联系方式新增")
    @ApiImplicitParam(name = "tbContact", value = "联系方式", required = true, dataType = "TbContact")
    @UooLog(value = "新增联系方式", key = "addContact")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addContact(@RequestBody TbContact tbContact) {
        tbContactService.insert(tbContact);
    }

    @ApiOperation(value = "修改联系方式",notes = "联系方式修改")
    @ApiImplicitParam(name = "tbContact", value = "联系方式", required = true, dataType = "TbContact")
    @UooLog(value = "修改联系方式", key = "updateContact")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateContact(@RequestBody TbContact tbContact) {
        tbContactService.updateAllColumnById(tbContact);
    }

    @ApiOperation(value = "删除联系方式", notes = "联系方式删除")
    @ApiImplicitParam(name = "tbContact", value = "联系方式", required = true, dataType = "TbContact")
    @UooLog(value = "删除联系方式", key = "delContact")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public void delContact(@RequestBody TbContact tbContact) {
        tbContactService.deleteById(tbContact.getContactId());
    }
}

