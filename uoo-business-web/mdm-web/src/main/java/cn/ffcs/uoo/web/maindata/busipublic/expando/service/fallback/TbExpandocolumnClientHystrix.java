package cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandocolumn;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.TbExpandocolumnClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 扩展列客户端断路器
 * @author zhanglu
 * @date 2018-11-09
 */
@Component
public class TbExpandocolumnClientHystrix implements TbExpandocolumnClient {
    @Override
    public ResponseResult<TbExpandocolumn> updateTbExpandocolumn(TbExpandocolumn tbExpandocolumn) {
        ResponseResult<TbExpandocolumn> responseResult = new ResponseResult<TbExpandocolumn>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbExpandocolumn> addTbExpandocolumn(TbExpandocolumn tbExpandocolumn) {
        ResponseResult<TbExpandocolumn> responseResult = new ResponseResult<TbExpandocolumn>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbExpandocolumn> removeTbExpandocolumn(Long columnId, Long updateUser) {
        ResponseResult<TbExpandocolumn> responseResult = new ResponseResult<TbExpandocolumn>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public List<TbExpandocolumn> queryListByTableId(Long tableId) {
        return null;
    }

    @Override
    public List<TbExpandocolumn> queryColumnList(Long tableId, String resourceId) {
        return null;
    }
}
