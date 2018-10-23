package cn.ffcs.uoo.core.dictionary.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionaryItem;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryItemService;
import cn.ffcs.uoo.core.dictionary.vo.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典项目 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
@RestController
@RequestMapping("/tbDictionaryItem")
public class TbDictionaryItemController extends BaseController {
    @Autowired
    TbDictionaryItemService tbDictionaryItemService;

    @ApiOperation(value = "修改字典项目",notes = "字典项目修改")
    @ApiImplicitParam(name = "tbDictionaryItem", value = "字典项目", required = true, dataType = "TbDictionaryItem")
    @UooLog(value = "修改字典项目", key = "updateTbDictionaryItem")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbDictionaryItem> updateTbDictionaryItem(TbDictionaryItem tbDictionaryItem) {
        ResponseResult<TbDictionaryItem> responseResult = new ResponseResult<TbDictionaryItem>();
        tbDictionaryItemService.updateById(tbDictionaryItem);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("成功");
        return responseResult;
    }

    @ApiOperation(value = "新增字典项目",notes = "字典项目新增")
    @ApiImplicitParam(name = "tbDictionaryItem", value = "字典项目", required = true, dataType = "TbDictionaryItem")
    @UooLog(value = "新增字典项目", key = "addTbDictionaryItem")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbDictionaryItem> addTbDictionaryItem(TbDictionaryItem tbDictionaryItem) {
        ResponseResult<TbDictionaryItem> responseResult = new ResponseResult<TbDictionaryItem>();
        tbDictionaryItemService.save(tbDictionaryItem);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("成功");
        return responseResult;
    }

    @ApiOperation(value = "删除字典项目",notes = "字典项目删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId", value = "字典项标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除字典项目", key = "removeTbDictionaryItem")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbDictionaryItem> removeTbDictionaryItem(Long itemId, Long updateUser) {
        ResponseResult<TbDictionaryItem> responseResult = new ResponseResult<TbDictionaryItem>();

        // 判断是否有下级字典项目
        TbDictionaryItem tbDictionaryItem = new TbDictionaryItem();
        tbDictionaryItem.setParItemId(itemId);
        List<TbDictionaryItem> tbDictionaryItems = tbDictionaryItemService.selectDicItemList(tbDictionaryItem);
        if(tbDictionaryItems.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("存在下级字典项目，不允许删除");
            return responseResult;
        }

        tbDictionaryItemService.remove(itemId,updateUser);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("成功");
        return responseResult;
    }
}
