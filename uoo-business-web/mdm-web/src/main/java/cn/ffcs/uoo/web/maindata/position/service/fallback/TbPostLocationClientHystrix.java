package cn.ffcs.uoo.web.maindata.position.service.fallback;

import cn.ffcs.uoo.web.maindata.position.dto.TbPostLocation;
import cn.ffcs.uoo.web.maindata.position.service.TbPostLocationClient;
import cn.ffcs.uoo.web.maindata.position.vo.ResponseResult;
import org.springframework.stereotype.Component;

@Component
public class TbPostLocationClientHystrix implements TbPostLocationClient {
    @Override
    public ResponseResult<TbPostLocation> addTbPostLocation(TbPostLocation tbPostLocation) {
        ResponseResult<TbPostLocation> responseResult = new ResponseResult<TbPostLocation>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbPostLocation> removeTbPostLocation(Long postLocationId, Long updateUser) {
        ResponseResult<TbPostLocation> responseResult = new ResponseResult<TbPostLocation>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
