package cn.ffcs.uoo.web.maindata.personnel.service.fallback;

import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.personnel.service.CertService;
import org.springframework.stereotype.Component;
/**
 * @ClassName CertServiceHystrix
 * @Description
 * @author wudj
 * @date 2018/11/19 16:28
 * @Version 1.0.0
 */
@Component
public class CertServiceHystrix implements CertService {
    @Override
    public Object getCertInfo(String keyWord, Integer pageNo, Integer pageSize) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
