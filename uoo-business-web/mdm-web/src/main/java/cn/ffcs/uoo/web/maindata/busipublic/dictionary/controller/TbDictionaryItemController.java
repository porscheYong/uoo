package cn.ffcs.uoo.web.maindata.busipublic.dictionary.controller;

import cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto.TbDictionaryItem;
import cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.TbDictionaryItemClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典项目 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-11-08
 */
@Api(description = "字典项目", value = "DictionaryItem")
@RestController
@RequestMapping("/tbDictionaryItem")
public class TbDictionaryItemController {
    @Resource
    TbDictionaryItemClient tbDictionaryItemClient;

    @ApiOperation(value = "修改字典项目", notes = "字典项目修改")
    @ApiImplicitParam(name = "tbDictionaryItem", value = "字典项目", required = true, dataType = "TbDictionaryItem")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbDictionaryItem> updateTbDictionaryItem(@RequestBody TbDictionaryItem tbDictionaryItem) {
        return tbDictionaryItemClient.updateTbDictionaryItem(tbDictionaryItem);
    }

    @ApiOperation(value = "新增字典项目", notes = "字典项目新增")
    @ApiImplicitParam(name = "tbDictionaryItem", value = "字典项目", required = true, dataType = "TbDictionaryItem")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbDictionaryItem> addTbDictionaryItem(@RequestBody TbDictionaryItem tbDictionaryItem) {
        return tbDictionaryItemClient.addTbDictionaryItem(tbDictionaryItem);
    }

    @ApiOperation(value = "删除字典项目", notes = "字典项目删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId", value = "字典项标识", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbDictionaryItem> removeTbDictionaryItem(@RequestBody Long itemId, @RequestBody Long updateUser) {
        return tbDictionaryItemClient.removeTbDictionaryItem(itemId, updateUser);
    }

    @ApiOperation(value = "查询字典项目列表", notes = "查询字典项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictionaryName", value = "列名",required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/getList/{dictionaryName}", method = RequestMethod.GET)
    public List<TbDictionaryItem> queryListByDictionaryName(@PathVariable String dictionaryName) {
        return tbDictionaryItemClient.queryListByDictionaryName(dictionaryName);
    }
}
