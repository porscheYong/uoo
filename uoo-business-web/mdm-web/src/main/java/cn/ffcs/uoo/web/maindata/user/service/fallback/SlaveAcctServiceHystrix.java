package cn.ffcs.uoo.web.maindata.user.service.fallback;

import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.user.service.SlaveAcctService;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormSlaveAcctVo;
import org.springframework.stereotype.Component;

@Component
public class SlaveAcctServiceHystrix implements SlaveAcctService {
    @Override
    public Object saveSlaveAcct(EditFormSlaveAcctVo editFormSlaveAcctVo) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object delTbSlaveAcct(Long slaveAcctId) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object updateTbSlaveAcct(EditFormSlaveAcctVo editFormSlaveAcctVo) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
