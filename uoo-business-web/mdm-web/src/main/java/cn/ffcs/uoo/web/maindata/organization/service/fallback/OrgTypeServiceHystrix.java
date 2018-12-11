package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.organization.dto.Org;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgType;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.OrgService;
import cn.ffcs.uoo.web.maindata.organization.service.OrgTypeService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/12
 */
@Component
public class OrgTypeServiceHystrix implements OrgTypeService {

    @Override
    public ResponseResult<List<OrgType>> getOrgTypeList(String orgTypeCode){
        ResponseResult<List<OrgType>> responseResult = new ResponseResult<List<OrgType>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<List<TreeNodeVo>> getOrgTypeTree(String id, String orgTypeCode){
        ResponseResult<List<TreeNodeVo>> responseResult = new ResponseResult<List<TreeNodeVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<List<TreeNodeVo>> getFullOrgTypeTree(String id,String orgTypeCode,String orgId){
        ResponseResult<List<TreeNodeVo>> responseResult = new ResponseResult<List<TreeNodeVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }


}
