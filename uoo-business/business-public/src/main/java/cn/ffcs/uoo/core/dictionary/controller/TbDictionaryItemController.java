package cn.ffcs.uoo.core.dictionary.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionary;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionaryItem;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryItemService;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryService;
import cn.ffcs.uoo.core.vo.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典项目 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
@Api(description = "字典项目", value = "DictionaryItem")
@RestController
@RequestMapping("/tbDictionaryItem")
public class TbDictionaryItemController extends BaseController {
    @Autowired
    TbDictionaryItemService tbDictionaryItemService;
    @Autowired
    TbDictionaryService tbDictionaryService;

    @ApiOperation(value = "修改字典项目", notes = "字典项目修改")
    @ApiImplicitParam(name = "tbDictionaryItem", value = "字典项目", required = true, dataType = "TbDictionaryItem")
    @UooLog(value = "修改字典项目", key = "updateTbDictionaryItem")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbDictionaryItem> updateTbDictionaryItem(@RequestBody TbDictionaryItem tbDictionaryItem) {
        ResponseResult<TbDictionaryItem> responseResult = new ResponseResult<TbDictionaryItem>();
        tbDictionaryItemService.updateById(tbDictionaryItem);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("修改成功");
        return responseResult;
    }

    @ApiOperation(value = "新增字典项目", notes = "字典项目新增")
    @ApiImplicitParam(name = "tbDictionaryItem", value = "字典项目", required = true, dataType = "TbDictionaryItem")
    @UooLog(value = "新增字典项目", key = "addTbDictionaryItem")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbDictionaryItem> addTbDictionaryItem(@RequestBody TbDictionaryItem tbDictionaryItem) {
        ResponseResult<TbDictionaryItem> responseResult = new ResponseResult<TbDictionaryItem>();
        tbDictionaryItemService.save(tbDictionaryItem);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("成功");
        return responseResult;
    }

    @ApiOperation(value = "删除字典项目", notes = "字典项目删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId", value = "字典项标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @UooLog(value = "删除字典项目", key = "removeTbDictionaryItem")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbDictionaryItem> removeTbDictionaryItem(@RequestBody Long itemId, @RequestBody Long updateUser) {
        ResponseResult<TbDictionaryItem> responseResult = new ResponseResult<TbDictionaryItem>();

        // 判断是否有下级字典项目
        TbDictionaryItem tbDictionaryItem = new TbDictionaryItem();
        tbDictionaryItem.setParItemId(itemId);
        List<TbDictionaryItem> tbDictionaryItems = tbDictionaryItemService.selectDicItemList(tbDictionaryItem);
        if (tbDictionaryItems.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("存在下级字典项目，不允许删除");
            return responseResult;
        }

        tbDictionaryItemService.remove(itemId, updateUser);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("成功");
        return responseResult;
    }

    @ApiOperation(value = "查询字典项目列表", notes = "查询字典项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictionaryName", value = "列名",required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "查询字典项目列表", key = "queryListByDictionaryName")
    @RequestMapping(value = "/getList/{dictionaryName}", method = RequestMethod.GET)
    public List<TbDictionaryItem> queryListByDictionaryName(@PathVariable String dictionaryName) {
        // 校验必填项
        if (dictionaryName == null) {
            return null;
        }

        // 根据dictionaryName查询出id
        Wrapper<TbDictionary> tbDictionaryWrapper = new EntityWrapper<TbDictionary>();
        tbDictionaryWrapper.eq("DICTIONARY_NAME", dictionaryName);
        tbDictionaryWrapper.eq("STATUS_CD", "1000");
        List<TbDictionary> tbDictionaryList = tbDictionaryService.selectList(tbDictionaryWrapper);

        if (tbDictionaryList == null || tbDictionaryList.size() == 0) {
            return null;
        }

        // 根据dictionaryId查询出dictionaryItemList
        Wrapper<TbDictionaryItem> tbDictionaryItemWrapper = new EntityWrapper<TbDictionaryItem>();
        tbDictionaryItemWrapper.eq("DICTIONARY_ID", tbDictionaryList.get(0).getDictionaryId());
        tbDictionaryItemWrapper.eq("STATUS_CD", "1000");
        return tbDictionaryItemService.selectList(tbDictionaryItemWrapper);
    }
}
