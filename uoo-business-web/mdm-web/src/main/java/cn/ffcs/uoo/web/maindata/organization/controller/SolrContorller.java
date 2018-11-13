package cn.ffcs.uoo.web.maindata.organization.controller;


import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.SolrReqVo;
import cn.ffcs.uoo.web.maindata.organization.service.SolrService;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.common.SolrDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
public class SolrContorller {

    @Autowired
    private SolrService solrService;

    @ApiOperation(value = "获取组织索引", notes = "获取组织索引")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getOrgSolrIndex", method = RequestMethod.GET)
    public ResponseResult<List<SolrDocument>> getOrgSolrIndex(SolrReqVo solrReqVo){
        return solrService.getOrgSolrIndex(solrReqVo);
    }

    @ApiOperation(value = "获取人员组织索引", notes = "获取组织索引")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/getPsnOrgSolrIndex", method = RequestMethod.GET)
    public ResponseResult<List<SolrDocument>> getPsnOrgSolrIndex(SolrReqVo solrReqVo){
        return solrService.getPsnOrgSolrIndex(solrReqVo);
    }

}
