package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.organization.dto.OrgOrgtreeRel;
import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgContactRelService;
import cn.ffcs.uoo.web.maindata.organization.service.OrgOrgtreeRelService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/12/18
 */
@Component
public class OrgOrgtreeRelServiceHystrix implements OrgOrgtreeRelService {


    @Override
    public ResponseResult<String> updateOrgOrgTreeRel(OrgOrgtreeRel orgOrgTreeRel){
        ResponseResult<String> responseResult = new ResponseResult<>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
