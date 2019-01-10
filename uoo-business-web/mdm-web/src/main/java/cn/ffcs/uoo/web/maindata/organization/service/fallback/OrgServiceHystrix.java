package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.organization.dto.*;
import cn.ffcs.uoo.web.maindata.organization.service.OrgContactRelService;
import cn.ffcs.uoo.web.maindata.organization.service.OrgService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
public class OrgServiceHystrix implements OrgService {


    @Override
    public ResponseResult<TreeNodeVo> addOrg(OrgVo org){
        ResponseResult<TreeNodeVo> responseResult = new ResponseResult<TreeNodeVo>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
    @Override
    public ResponseResult<Void> updateOrg(OrgVo org){
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<OrgVo> getOrg(String orgTreeId,String orgId){
        ResponseResult<OrgVo> responseResult = new ResponseResult<OrgVo>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }


    @Override
    public ResponseResult<Page<OrgVo>> getOrgRelPage(String orgRootId,
                                                     String orgTreeId,
                                                     String orgId,
                                                     String sortField,
                                                     String sortOrder,
                                                     Integer pageSize,
                                                     Integer pageNo,
                                                     Long userId,
                                                     String accout){
        ResponseResult<Page<OrgVo>> responseResult = new ResponseResult<Page<OrgVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Page<OrgVo>> getOrgPage(String search,
                                                  String orgTreeId,
                                                  Integer pageSize,
                                                  Integer pageNo){
        ResponseResult<Page<OrgVo>> responseResult = new ResponseResult<Page<OrgVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }


    @Override
    public ResponseResult<HashMap<String,String>> getOrgExtByOrgId(String orgTreeId, String orgRootId,String orgId){
        ResponseResult<HashMap<String,String>> responseResult = new ResponseResult<HashMap<String,String>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }


    @Override
    public ResponseResult<String> deleteOrg(String orgTreeId, String orgId,String supOrgId,Long userId){
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }


}
