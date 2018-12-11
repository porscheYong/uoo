package cn.ffcs.uoo.web.maindata.position.service.fallback;

import cn.ffcs.uoo.web.maindata.position.dto.TbOrgPositionRel;
import cn.ffcs.uoo.web.maindata.position.service.TbOrgPositionRelClient;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import org.springframework.stereotype.Component;

@Component
public class TbOrgPositionRelClientHystrix implements TbOrgPositionRelClient {
    @Override
    public ResponseResult<TbOrgPositionRel> addTbOrgPositionRel(TbOrgPositionRel tbOrgPositionRel) {
        ResponseResult<TbOrgPositionRel> responseResult = new ResponseResult<TbOrgPositionRel>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbOrgPositionRel> removeTbOrgPositionRel(Long orgPositionId, Long updateUser) {
        ResponseResult<TbOrgPositionRel> responseResult = new ResponseResult<TbOrgPositionRel>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
