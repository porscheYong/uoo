package cn.ffcs.uoo.core.dictionary.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.constant.StatusEnum;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionary;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionaryItem;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryItemService;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryService;
import cn.ffcs.uoo.core.vo.DictionaryListVo;
import cn.ffcs.uoo.core.vo.ResponseResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
        tbDictionaryItem.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbDictionaryItem.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
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
        tbDictionaryItem.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbDictionaryItem.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbDictionaryItem.setCreateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbDictionaryItem.setStatusCd(StatusEnum.VALID.getValue());
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
    public ResponseResult<TbDictionaryItem> removeTbDictionaryItem(@RequestParam("itemId") Long itemId, @RequestParam("updateUser") Long updateUser) {
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
            @ApiImplicitParam(name = "dictionaryName", value = "列名", required = true, dataType = "String", paramType = "path")
    })
    @UooLog(value = "查询字典项目列表", key = "queryListByDictionaryName")
    @RequestMapping(value = "/getList/{dictionaryName}", method = RequestMethod.GET)
    public ResponseResult<List<TbDictionaryItem>> queryListByDictionaryName(@PathVariable String dictionaryName) {
        ResponseResult<List<TbDictionaryItem>> responseResult = new ResponseResult<List<TbDictionaryItem>>();

        // 校验必填项
        if (dictionaryName == null) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("必填项校验失败");
            return responseResult;
        }

        // 根据dictionaryName查询出id
        Wrapper<TbDictionary> tbDictionaryWrapper = new EntityWrapper<TbDictionary>();
        tbDictionaryWrapper.eq("DICTIONARY_NAME", dictionaryName);
        tbDictionaryWrapper.eq("STATUS_CD", "1000");
        List<TbDictionary> tbDictionaryList = tbDictionaryService.selectList(tbDictionaryWrapper);

        if (tbDictionaryList == null || tbDictionaryList.size() == 0) {
            responseResult.setState(ResponseResult.STATE_ERROR);
            responseResult.setMessage("字典值不存在");
            return responseResult;
        }

        // 根据dictionaryId查询出dictionaryItemList
        Wrapper<TbDictionaryItem> tbDictionaryItemWrapper = new EntityWrapper<TbDictionaryItem>();
        tbDictionaryItemWrapper.eq("DICTIONARY_ID", tbDictionaryList.get(0).getDictionaryId());
        tbDictionaryItemWrapper.eq("STATUS_CD", "1000");
        tbDictionaryItemWrapper.orderBy("SORT");
        List<TbDictionaryItem> list = tbDictionaryItemService.selectList(tbDictionaryItemWrapper);
        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("请求成功");
        responseResult.setData(list);
        return responseResult;
    }

    @ApiOperation(value = "查询字典所有字典项目列表", notes = "查询字典所有字典项目列表")
    @UooLog(value = "查询字典所有字典项目列表", key = "queryAllList")
    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    public ResponseResult<DictionaryListVo> queryAllList() {
        ResponseResult<DictionaryListVo> responseResult = new ResponseResult<DictionaryListVo>();
        DictionaryListVo dictionaryListVo = new DictionaryListVo();
        List<TbDictionaryItem> list = new ArrayList<TbDictionaryItem>();

        list = tbDictionaryItemService.selectDicItemListByDicName("CITY_VILLAGE");
        dictionaryListVo.setCITY_VILLAGE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("SCALE");
        dictionaryListVo.setSCALE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("ORG_POST_LEVEL");
        dictionaryListVo.setORG_POST_LEVEL(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("COLUM_TYPE");
        dictionaryListVo.setCOLUM_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("COL_NULLABLE");
        dictionaryListVo.setCOL_NULLABLE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("EDITABLE");
        dictionaryListVo.setEDITABLE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("STATUS_CD");
        dictionaryListVo.setSTATUS_CD(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("YES_NO");
        dictionaryListVo.setYES_NO(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("RULE_OPERATOR");
        dictionaryListVo.setRULE_OPERATOR(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("NATIONALITY");
        dictionaryListVo.setNATIONALITY(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("GENDER");
        dictionaryListVo.setGENDER(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("NATION");
        dictionaryListVo.setNATION(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("MARRIAGE");
        dictionaryListVo.setMARRIAGE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("PLITICAL_STATUS");
        dictionaryListVo.setPLITICAL_STATUS(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("SCHOOL_TYPE");
        dictionaryListVo.setSCHOOL_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("MEM_RELATION");
        dictionaryListVo.setMEM_RELATION(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("CERT_TYPE");
        dictionaryListVo.setCERT_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("ACCT_TYPE");
        dictionaryListVo.setACCT_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("USER_HOST_TYPE");
        dictionaryListVo.setUSER_HOST_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("ORG_TREE_TYPE");
        dictionaryListVo.setORG_TREE_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("RELA_TYPE");
        dictionaryListVo.setRELA_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("PROPERTY");
        dictionaryListVo.setPROPERTY(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("REF_TYPE");
        dictionaryListVo.setREF_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("ROLE_TYPE");
        dictionaryListVo.setROLE_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("GRANT_OBJ_TYPE");
        dictionaryListVo.setGRANT_OBJ_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("PRIV_TYPE");
        dictionaryListVo.setPRIV_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("PRIV_REF_TYPE");
        dictionaryListVo.setPRIV_REF_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("COMP_TYPE");
        dictionaryListVo.setCOMP_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("INTF_TYPE");
        dictionaryListVo.setINTF_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("CONNECT_TYPE");
        dictionaryListVo.setCONNECT_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("REGION_TYPE");
        dictionaryListVo.setREGION_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("LOC_TYPE");
        dictionaryListVo.setLOC_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("POST_TYPE");
        dictionaryListVo.setPOST_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("POSITION_TYPE");
        dictionaryListVo.setPOSITION_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("POSITION_TYPE");
        dictionaryListVo.setPOSITION_TYPE(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("nodeType");
        dictionaryListVo.setNodeType(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("areaType");
        dictionaryListVo.setAreaType(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("countType");
        dictionaryListVo.setCountType(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("contractType");
        dictionaryListVo.setContractType(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("ACCT_LEVEL");
        dictionaryListVo.setACCT_LEVEL(list);

        list = tbDictionaryItemService.selectDicItemListByDicName("REL_TYPE");
        dictionaryListVo.setREL_TYPE(list);

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("请求成功");
        responseResult.setData(dictionaryListVo);
        return responseResult;
    }
}
