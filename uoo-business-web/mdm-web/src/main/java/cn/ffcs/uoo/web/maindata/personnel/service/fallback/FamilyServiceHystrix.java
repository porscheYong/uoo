package cn.ffcs.uoo.web.maindata.personnel.service.fallback;

import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.personnel.dto.TbFamily;
import cn.ffcs.uoo.web.maindata.personnel.service.FamilyService;
import org.springframework.stereotype.Component;

@Component
public class FamilyServiceHystrix implements FamilyService {
    @Override
    public Object saveTbFamily(TbFamily tbFamily) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object updateTbFamily(TbFamily tbFamily) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object delTbFamily(Long familyId) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object getTbFamily(Long familyId) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object getTbFamilyPage(Long personnelId, Integer pageNo, Integer pageSize) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
