package cn.ffcs.uoo.core.dictionary.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionary;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryItemService;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryService;
import cn.ffcs.uoo.core.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典定义 前端控制器
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-22
 */
@Api(description = "字典定义",value = "Dictionary")
@RestController
@RequestMapping("/tbDictionary")
public class TbDictionaryController extends BaseController {
    @Autowired
    TbDictionaryService tbDictionaryService;
    @Autowired
    TbDictionaryItemService tbDictionaryItemService;

    @ApiOperation(value = "修改字典定义",notes = "字典定义修改")
    @ApiImplicitParam(name = "tbDictionary", value = "字典定义", required = true, dataType = "TbDictionary")
    @UooLog(value = "修改字典定义", key = "updateTbDictionary")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbDictionary> updateTbDictionary(@RequestBody TbDictionary tbDictionary) {
        ResponseResult<TbDictionary> responseResult = new ResponseResult<TbDictionary>();
        tbDictionaryService.updateById(tbDictionary);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("成功");
        return responseResult;
    }

    @ApiOperation(value = "新增字典定义",notes = "字典定义新增")
    @ApiImplicitParam(name = "tbDictionary", value = "字典定义", required = true, dataType = "TbDictionary")
    @UooLog(value = "新增字典定义", key = "addTbDictionary")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbDictionary> addTbDictionary(@RequestBody TbDictionary tbDictionary) {
        ResponseResult<TbDictionary> responseResult = new ResponseResult<TbDictionary>();
        // 校验字典定义是否存在
        List<TbDictionary> tbDictionaryList = tbDictionaryService.selectDicList(tbDictionary);
        if (tbDictionaryList.size() > 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该字典定义已存在");
            return responseResult;
        }

        tbDictionaryService.saveDictionary(tbDictionary);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("成功");
        responseResult.setData(tbDictionary);
        return responseResult;
    }

    @ApiOperation(value = "删除字典定义",notes = "删除字典定义")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictionaryName", value = "列名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "String")
    })
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbDictionary> removeTbDictionary(@RequestParam("dictionaryName") String dictionaryName, @RequestParam("updateUser") Long updateUser) {
        ResponseResult<TbDictionary> responseResult = new ResponseResult<TbDictionary>();

        // 查询出该字典列名对应的字典
        TbDictionary queryTbDictionary = new TbDictionary();
        queryTbDictionary.setDictionaryName(dictionaryName);
        List<TbDictionary> tbDictionaryList = tbDictionaryService.selectDicList(queryTbDictionary);

        // 该列名对应的字典不存在
        if(tbDictionaryList == null || tbDictionaryList.size() == 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("该列名对应的字典不存在");
            return responseResult;
        }

        // 先失效关联的字典项目
        tbDictionaryItemService.removeBatchByDicId(tbDictionaryList.get(0).getDictionaryId(),updateUser);

        // 失效字典定义
        tbDictionaryService.remove(tbDictionaryList.get(0).getDictionaryId(),updateUser);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("删除成功");
        return responseResult;
    }
}

