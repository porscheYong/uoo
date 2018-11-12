package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.SolrReqVo;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.SolrService;
import org.apache.solr.common.SolrDocument;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/12
 */
public class SolrServiceHystrix implements SolrService {


    @Override
    public ResponseResult<List<SolrDocument>> getOrgSolrIndex(SolrReqVo solrReqVo){
        ResponseResult<List<SolrDocument>> responseResult = new ResponseResult<List<SolrDocument>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<List<SolrDocument>> getPsnOrgSolrIndex(SolrReqVo solrReqVo){
        ResponseResult<List<SolrDocument>> responseResult = new ResponseResult<List<SolrDocument>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }


}
