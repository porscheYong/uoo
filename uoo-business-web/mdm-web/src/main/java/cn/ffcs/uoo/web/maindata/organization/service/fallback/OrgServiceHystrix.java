package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.dto.Org;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.service.OrgContactRelService;
import cn.ffcs.uoo.web.maindata.organization.service.OrgService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;

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
    public ResponseResult<Void> addOrg(Org org){
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
    @Override
    public ResponseResult<Void> updateOrg(Org org){
        ResponseResult<Void> responseResult = new ResponseResult<Void>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Org> getOrg(String orgId){
        ResponseResult<Org> responseResult = new ResponseResult<Org>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }


    @Override
    public ResponseResult<Page<OrgVo>> getOrgRelPage(OrgVo orgVo){
        ResponseResult<Page<OrgVo>> responseResult = new ResponseResult<Page<OrgVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Page<OrgVo>> getOrgPage(OrgVo orgVo){
        ResponseResult<Page<OrgVo>> responseResult = new ResponseResult<Page<OrgVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

}
