package cn.ffcs.uoo.web.maindata.busipublic.dictionary.controller;

import cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto.TbDictionary;
import cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.TbDictionaryClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateLog;
import cn.ffcs.uoo.web.maindata.mdm.logs.OperateType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
public class TbDictionaryController {
    @Resource
    TbDictionaryClient tbDictionaryClient;

    @ApiOperation(value = "修改字典定义",notes = "字典定义修改")
    @ApiImplicitParam(name = "tbDictionary", value = "字典定义", required = true, dataType = "TbDictionary")
    @OperateLog(type= OperateType.UPDATE,module="字典模块",methods="更新字典",desc="更新字典")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<TbDictionary> updateTbDictionary(@RequestBody TbDictionary tbDictionary) {
        return tbDictionaryClient.updateTbDictionary(tbDictionary);
    }

    @ApiOperation(value = "新增字典定义",notes = "字典定义新增")
    @ApiImplicitParam(name = "tbDictionary", value = "字典定义", required = true, dataType = "TbDictionary")
    @OperateLog(type=OperateType.ADD,module="字典模块",methods="新增字典定义",desc="新增字典定义")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult<TbDictionary> addTbDictionary(@RequestBody TbDictionary tbDictionary) {
        return tbDictionaryClient.addTbDictionary(tbDictionary);
    }

    @ApiOperation(value = "删除字典定义",notes = "删除字典定义")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictionaryName", value = "列名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "updateUser", value = "修改人", required = true, dataType = "String")
    })
    @OperateLog(type=OperateType.DELETE,module="字典模块",methods="删除字典定义",desc="删除字典定义")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public ResponseResult<TbDictionary> removeTbDictionary(@RequestParam("dictionaryName") String dictionaryName, @RequestParam("updateUser") Long updateUser) {
        return tbDictionaryClient.removeTbDictionary(dictionaryName, updateUser);
    }
}

