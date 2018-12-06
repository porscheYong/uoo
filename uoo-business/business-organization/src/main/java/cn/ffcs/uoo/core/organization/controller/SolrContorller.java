package cn.ffcs.uoo.core.organization.controller;

import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.core.organization.entity.OgtOrgReltypeConf;
import cn.ffcs.uoo.core.organization.entity.OrgRelType;
import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.service.OgtOrgReltypeConfService;
import cn.ffcs.uoo.core.organization.service.OrgRelTypeService;
import cn.ffcs.uoo.core.organization.service.OrgTreeService;
import cn.ffcs.uoo.core.organization.service.SolrService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.SolrReqVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/21
 */
@RestController
@RequestMapping("/solr")
@Api(value = "/solr", description = "组织人员搜索引擎")
public class SolrContorller extends BaseController {

//    @Autowired
//    private SolrService solrService;
//
//    @Autowired
//    private OrgTreeService orgTreeService;
//
//    @Autowired
//    private OrgRelTypeService orgRelTypeService;
//
//
//    /**
//     * 按条件查询搜索引擎
//     */
//    @ApiOperation(value = "查询组织树信息-web", notes = "查询组织树信息")
//    @ApiImplicitParams({
//    })
//    @UooLog(value = "查询组织树信息", key = "getOrgSolrIndex")
//    @RequestMapping(value = "/getOrgSolrIndex" , method = RequestMethod.GET)
//    @Transactional(rollbackFor = Exception.class)
//    public ResponseResult<List<SolrDocument>> getOrgSolrIndex(SolrReqVo solrReqVo){
//        ResponseResult<List<SolrDocument>> ret = new ResponseResult<List<SolrDocument>>();
//        if(StrUtil.isNullOrEmpty(solrReqVo.getOrgRootId())){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织树根节点不能为空");
//            return ret;
//        }
//        Wrapper orgTreeWrapper = Condition.create().eq("ORG_ID",solrReqVo.getOrgRootId()).eq("STATUS_CD","1000");
//        OrgTree orgTree = orgTreeService.selectOne(orgTreeWrapper);
//        if(orgTree==null){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织树为空");
//        }
//        List<OrgRelType> orgRelType = orgRelTypeService.getOrgRelType(orgTree.getOrgTreeId().toString());
//        if(orgRelType==null){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织关系类型为空");
//        }
//        //solrReqVo.setOrgRelTypeId(orgRelType.getOrgRelTypeId().toString());
//        List<SolrDocument> list = solrService.getOrgSolrIndex(solrReqVo);
//        ret.setState(ResponseResult.STATE_OK);
//        ret.setData(list);
//        return ret;
//    }
//    /**
//     * 按条件查询人员搜索引擎
//     */
//    @ApiOperation(value = "查询人员组织信息-web", notes = "查询人员组织信息")
//    @ApiImplicitParams({
//    })
//    @UooLog(value = "查询人员组织信息", key = "getPsnOrgSolrIndex")
//    @RequestMapping(value = "/getPsnOrgSolrIndex" , method = RequestMethod.GET)
//    @Transactional(rollbackFor = Exception.class)
//    public ResponseResult<List<SolrDocument>> getPsnOrgSolrIndex(SolrReqVo solrReqVo){
//        ResponseResult<List<SolrDocument>> ret = new ResponseResult<List<SolrDocument>>();
//        if(StrUtil.isNullOrEmpty(solrReqVo.getOrgRootId())){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织树根节点不能为空");
//            return ret;
//        }
//        Wrapper orgTreeWrapper = Condition.create().eq("ORG_ID",solrReqVo.getOrgRootId()).eq("STATUS_CD","1000");
//        OrgTree orgTree = orgTreeService.selectOne(orgTreeWrapper);
//        if(orgTree==null){
//            ret.setState(ResponseResult.PARAMETER_ERROR);
//            ret.setMessage("组织树为空");
//        }
////        OrgRelType orgRelType = orgRelTypeService.getOrgRelType(orgTree.getOrgTreeId().toString());
////        if(orgRelType==null){
////            ret.setState(ResponseResult.PARAMETER_ERROR);
////            ret.setMessage("组织关系类型为空");
////        }
////        solrReqVo.setOrgRelTypeId(orgRelType.getOrgRelTypeId().toString());
//        List<SolrDocument> list = solrService.getPsonOrgSolrIndex(solrReqVo);
//        ret.setState(ResponseResult.STATE_OK);
//        ret.setData(list);
//        return ret;
//    }
////    /**
////    * 添加入solr索引
////     */
////    @RequestMapping(value = "/addDataIntoSolr" , method = RequestMethod.GET)
////    public void addDataIntoSolr(){
////        SolrInputDocument input = new SolrInputDocument();
//////        input.addField("id", "123213111");
//////        input.addField("uooOrgId", "1000");
//////        input.addField("uooOrgFullName", "heeloo");
////        solrService.addDataIntoSolr("mycore",input);
////    }
//
//    //    /**
////     * 添加入solr索引
////     */
////    @RequestMapping(value = "/pushDataIntoSolr" , method = RequestMethod.POST)
////    public BaseResponse pushDataIntoSolr(@RequestBody PushDataIntoSolrRequest request){
////        BaseResponse ress = new BaseResponse();
////        SolrInputDocument input = new SolrInputDocument();
////        for (Map.Entry<String, Object> entry : request.getInput().entrySet()) {
////            input.addField(entry.getKey(), entry.getValue());
////        }
////        if (!solrService.pushDataIntoSolr(request.getCoreName(), input)){
////            ress.setErrorCode("500");
////        } else {
////            ress.setErrorMsg("插入失败，请稍候重试。");
//////            ress.setSubMsg("请求成功，数据插入成功。");
////        }
////        return ress;
////    }
}
