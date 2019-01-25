package cn.ffcs.uoo.core.dictionary.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.constant.StatusEnum;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionary;
import cn.ffcs.uoo.core.dictionary.entity.TbDictionaryItem;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryItemService;
import cn.ffcs.uoo.core.dictionary.service.TbDictionaryService;
import cn.ffcs.uoo.core.vo.DictionaryItemVo;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<DictionaryItemVo> list = tbDictionaryItemService.selectAllDicitemVoList();

        // 按照字典名称，给list分组
        Map<String, List<DictionaryItemVo>> group = list.stream().collect(Collectors.groupingBy(DictionaryItemVo::getDictionaryName));

        dictionaryListVo.setCITY_VILLAGE(group.get("CITY_VILLAGE"));

        dictionaryListVo.setSCALE(group.get("SCALE"));

        dictionaryListVo.setORG_POST_LEVEL(group.get("ORG_POST_LEVEL"));

        dictionaryListVo.setCOLUM_TYPE(group.get("COLUM_TYPE"));

        dictionaryListVo.setCOL_NULLABLE(group.get("COL_NULLABLE"));

        dictionaryListVo.setEDITABLE(group.get("EDITABLE"));

        dictionaryListVo.setSTATUS_CD(group.get("STATUS_CD"));

        dictionaryListVo.setYES_NO(group.get("YES_NO"));

        dictionaryListVo.setRULE_OPERATOR(group.get("RULE_OPERATOR"));

        dictionaryListVo.setNATIONALITY(group.get("NATIONALITY"));

        dictionaryListVo.setGENDER(group.get("GENDER"));

        dictionaryListVo.setNATION(group.get("NATION"));

        dictionaryListVo.setMARRIAGE(group.get("MARRIAGE"));

        dictionaryListVo.setPLITICAL_STATUS(group.get("PLITICAL_STATUS"));

        dictionaryListVo.setSCHOOL_TYPE(group.get("SCHOOL_TYPE"));

        dictionaryListVo.setMEM_RELATION(group.get("MEM_RELATION"));

        dictionaryListVo.setCERT_TYPE(group.get("CERT_TYPE"));

        dictionaryListVo.setACCT_TYPE(group.get("ACCT_TYPE"));

        dictionaryListVo.setUSER_HOST_TYPE(group.get("USER_HOST_TYPE"));

        dictionaryListVo.setORG_TREE_TYPE(group.get("ORG_TREE_TYPE"));

        dictionaryListVo.setRELA_TYPE(group.get("RELA_TYPE"));

        dictionaryListVo.setPROPERTY(group.get("PROPERTY"));

        dictionaryListVo.setREF_TYPE(group.get("REF_TYPE"));

        dictionaryListVo.setROLE_TYPE(group.get("ROLE_TYPE"));

        dictionaryListVo.setGRANT_OBJ_TYPE(group.get("GRANT_OBJ_TYPE"));

        dictionaryListVo.setPRIV_TYPE(group.get("PRIV_TYPE"));

        dictionaryListVo.setPRIV_REF_TYPE(group.get("PRIV_REF_TYPE"));

        dictionaryListVo.setCOMP_TYPE(group.get("COMP_TYPE"));

        dictionaryListVo.setINTF_TYPE(group.get("INTF_TYPE"));

        dictionaryListVo.setCONNECT_TYPE(group.get("CONNECT_TYPE"));

        dictionaryListVo.setREGION_TYPE(group.get("REGION_TYPE"));

        dictionaryListVo.setLOC_TYPE(group.get("LOC_TYPE"));

        dictionaryListVo.setPOST_TYPE(group.get("POST_TYPE"));

        dictionaryListVo.setPOSITION_TYPE(group.get("POSITION_TYPE"));

        dictionaryListVo.setPOSITION_TYPE(group.get("POSITION_TYPE"));

        dictionaryListVo.setNodeType(group.get("nodeType"));

        dictionaryListVo.setAreaType(group.get("areaType"));

        dictionaryListVo.setCountType(group.get("countType"));

        dictionaryListVo.setContractType(group.get("contractType"));

        dictionaryListVo.setACCT_LEVEL(group.get("ACCT_LEVEL"));

        dictionaryListVo.setREL_TYPE(group.get("REL_TYPE"));

        dictionaryListVo.setVipRuleFlg(group.get("vipRuleFlg"));

        responseResult.setState(ResponseResult.STATE_OK);
        responseResult.setMessage("请求成功");
        responseResult.setData(dictionaryListVo);
        return responseResult;
    }
}
