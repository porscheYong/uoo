package cn.ffcs.uoo.web.maindata.organization.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.organization.dto.OrgTree;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgTreeSynchRule;
import cn.ffcs.uoo.web.maindata.organization.dto.OrgTreeSynchRuleVO;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.OrgTreeSynchRuleService;
@Component
public class OrgTreeSynchRuleServiceHystrix implements OrgTreeSynchRuleService {

    @Override
    public ResponseResult<OrgTreeSynchRule> get(Long id) {
        ResponseResult<OrgTreeSynchRule> responseResult = new ResponseResult<>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<List<OrgTree>> getTree(Long id) {
        ResponseResult<List<OrgTree>> responseResult = new ResponseResult<>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Void> add(OrgTreeSynchRule orgTreeSynchRule) {
        ResponseResult<Void> responseResult = new ResponseResult<>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Void> update(OrgTreeSynchRule orgTreeSynchRule) {
        ResponseResult<Void> responseResult = new ResponseResult<>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Void> delete(OrgTreeSynchRule orgTreeSynchRule) {
        ResponseResult<Void> responseResult = new ResponseResult<>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<List<OrgTreeSynchRuleVO>> list(Long orgTreeId) {
        ResponseResult<List<OrgTreeSynchRuleVO>> responseResult = new ResponseResult<>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

}
