package cn.ffcs.uoo.web.maindata.position.service.fallback;

import cn.ffcs.uoo.web.maindata.position.dto.TbPosition;
import cn.ffcs.uoo.web.maindata.position.service.TbPositionClient;
import cn.ffcs.uoo.web.maindata.position.vo.OrgPositionInfoVo;
import cn.ffcs.uoo.web.maindata.position.vo.PositionNodeVo;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TbPositionClientHystrix implements TbPositionClient {
    @Override
    public ResponseResult<TbPosition> addTbPosition(TbPosition tbPosition) {
        ResponseResult<TbPosition> responseResult = new ResponseResult<TbPosition>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbPosition> updateTbPosition(TbPosition tbPosition) {
        ResponseResult<TbPosition> responseResult = new ResponseResult<TbPosition>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbPosition> removeTbPosition(Long positionId, Long updateUser) {
        ResponseResult<TbPosition> responseResult = new ResponseResult<TbPosition>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public List<OrgPositionInfoVo> queryOrgPositionInfoList(Long orgId) {
        return null;
    }

    @Override
    public List<TbPosition> queryPositionList(String positionName) {
        return null;
    }

    @Override
    public List<TbPosition> queryParentPositionList() {
        return null;
    }

    @Override
    public List<TbPosition> queryChildPositionList(Long parentPositionId) {
        return null;
    }

    @Override
    public ResponseResult<List<PositionNodeVo>> getPositionTree() {
        ResponseResult<List<PositionNodeVo>> responseResult = new ResponseResult<List<PositionNodeVo>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
