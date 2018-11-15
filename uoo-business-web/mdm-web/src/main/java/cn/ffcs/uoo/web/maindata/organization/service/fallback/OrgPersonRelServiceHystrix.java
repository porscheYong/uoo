package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.organization.dto.OrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgContactRelService;
import cn.ffcs.uoo.web.maindata.organization.service.OrgPersonRelService;
import com.baomidou.mybatisplus.plugins.Page;
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
public class OrgPersonRelServiceHystrix implements OrgPersonRelService {

    @Override
    public ResponseResult<String> addOrgPsn(PsonOrgVo psonOrgVo){
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<String> updateOrgPsn(PsonOrgVo psonOrgVo){
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<String> deleteOrgPsn(PsonOrgVo psonOrgVo){
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
    @Override
    public ResponseResult<List<PsonOrgVo>> getPerOrgRelList(PsonOrgVo psonOrgVo){
        ResponseResult<List<PsonOrgVo>> responseResult = new ResponseResult<List<PsonOrgVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Page<PsonOrgVo>> getPerOrgRelPage(PsonOrgVo psonOrgVo){
        ResponseResult<Page<PsonOrgVo>> responseResult = new ResponseResult<Page<PsonOrgVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
