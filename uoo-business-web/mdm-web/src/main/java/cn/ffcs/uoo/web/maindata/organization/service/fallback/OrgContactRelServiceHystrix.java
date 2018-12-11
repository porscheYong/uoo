package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgContactRelService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/12
 */
@Component
public class OrgContactRelServiceHystrix implements OrgContactRelService {


    @Override
    public ResponseResult<Page<PsonOrgVo>> getOrgContactPage(String orgId,
                                                             Integer pageSize,
                                                             Integer pageNo){
        ResponseResult<Page<PsonOrgVo>> responseResult = new ResponseResult<Page<PsonOrgVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
