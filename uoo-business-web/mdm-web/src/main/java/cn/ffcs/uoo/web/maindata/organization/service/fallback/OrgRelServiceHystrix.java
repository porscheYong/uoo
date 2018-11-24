package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.organization.dto.*;
import cn.ffcs.uoo.web.maindata.organization.service.OrgRelService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
public class OrgRelServiceHystrix implements OrgRelService {

    @Override
    public ResponseResult<List<TreeNodeVo>> getOrgRelTree(String id, String orgRootId, String relCode, boolean isOpen,
                                                          boolean isAsync, boolean isRoot){
        ResponseResult<List<TreeNodeVo>> responseResult = new ResponseResult<List<TreeNodeVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
    @Override
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(String id,String orgTreeId,String orgRootId,boolean isFull){
        ResponseResult<List<TreeNodeVo>> responseResult = new ResponseResult<List<TreeNodeVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
    @Override
    public ResponseResult<List<TreeNodeVo>> getTarOrgRelTreeAndLv(String orgRootId,String orgTreeId,String lv,String curOrgid,boolean isFull){
        ResponseResult<List<TreeNodeVo>> responseResult = new ResponseResult<List<TreeNodeVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<String> addOrgRel(Org org){
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Page<OrgVo>> getFuzzyOrgRelPage(String search,String orgRootId,String orgTreeId,Integer pageSize,Integer pageNo){
        ResponseResult<Page<OrgVo>> responseResult = new ResponseResult<Page<OrgVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Page<OrgRefTypeVo>> getOrgRelTypePage(String orgId,Integer pageSize,Integer pageNo){
        ResponseResult<Page<OrgRefTypeVo>> responseResult = new ResponseResult<Page<OrgRefTypeVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }



}
