package cn.ffcs.uoo.web.maindata.user.service.fallback;

import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.user.dto.TbAccountOrgRel;
import cn.ffcs.uoo.web.maindata.user.service.AcctService;
import cn.ffcs.uoo.web.maindata.user.vo.AcctOrgVo;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormAcctVo;
import cn.ffcs.uoo.web.maindata.user.vo.SlaveAcctOrgVo;
import org.springframework.stereotype.Component;

@Component
public class AcctServiceHystrix implements AcctService {
    @Override
    public Object saveAcct(EditFormAcctVo editFormAcctVo) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object removeAcct(Long acctId, Long userId) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object updateAcct(EditFormAcctVo editFormAcctVo) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object removeAcctOrg(Long personnelId, Long acctId, Long orgId, Long orgTreeId, Long userId) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object addAcctOrg(TbAccountOrgRel tbAccountOrgRel) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object getAcctOrgRelPage(Long acctId, Integer pageNo, Integer pageSize, String account) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object updateAcctOrg(AcctOrgVo acctOrgVo) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object updateSlaveAcctOrg(SlaveAcctOrgVo acctOrgVo) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }


    @Override
    public Object getTbAcct(String acct) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
