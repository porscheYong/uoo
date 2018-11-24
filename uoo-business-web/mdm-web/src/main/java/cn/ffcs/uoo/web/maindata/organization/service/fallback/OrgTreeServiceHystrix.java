package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.organization.dto.OrgTree;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgType;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgTreeService;
import cn.ffcs.uoo.web.maindata.organization.service.OrgTypeService;
import org.springframework.stereotype.Component;

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
public class OrgTreeServiceHystrix implements OrgTreeService {

    @Override
    public ResponseResult<String> addOrgTree(OrgTree orgTree){
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<String> updateOrgTree(OrgTree orgTree){
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<List<OrgTree>> getOrgTreeList(OrgTree orgTree){
        ResponseResult<List<OrgTree>> responseResult = new ResponseResult<List<OrgTree>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }


}
