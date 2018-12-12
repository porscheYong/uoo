package cn.ffcs.uoo.web.maindata.position.service.fallback;

import cn.ffcs.uoo.web.maindata.position.dto.TbOrgPostRel;
import cn.ffcs.uoo.web.maindata.position.service.TbOrgPostRelClient;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import org.springframework.stereotype.Component;

@Component
public class TbOrgPostRelClientHystrix implements TbOrgPostRelClient {
    @Override
    public ResponseResult<TbOrgPostRel> addTbOrgPostRel(TbOrgPostRel tbOrgPostRel) {
        ResponseResult<TbOrgPostRel> responseResult = new ResponseResult<TbOrgPostRel>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbOrgPostRel> removeTbOrgPostRel(Long orgPostId, Long updateUser) {
        ResponseResult<TbOrgPostRel> responseResult = new ResponseResult<TbOrgPostRel>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
