package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.organization.dto.OrgRelType;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.TreeNodeVo;
import cn.ffcs.uoo.web.maindata.organization.service.OrgRelTypeService;
import cn.ffcs.uoo.web.maindata.organization.service.OrgService;
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
public class OrgRelTypeServiceHystrix implements OrgRelTypeService {

    @Override
    public ResponseResult<List<OrgRelType>> getOrgRelTypeList(String orgRelCode){
        ResponseResult<List<OrgRelType>> responseResult = new ResponseResult<List<OrgRelType>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<List<TreeNodeVo>> getOrgRelTypeTree(String refCode){
        ResponseResult<List<TreeNodeVo>> responseResult = new ResponseResult<List<TreeNodeVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
