package cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.ExpandovalueVo;
import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandovalue;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.TbExpandovalueClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TbExpandovalueClientHystrix implements TbExpandovalueClient {
    @Override
    public ResponseResult<TbExpandovalue> addTbExpandovalue(TbExpandovalue tbExpandovalue) {
        ResponseResult<TbExpandovalue> responseResult = new ResponseResult<TbExpandovalue>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbExpandovalue> removeTbExpandovalue(Long valueId, Long updateUser) {
        ResponseResult<TbExpandovalue> responseResult = new ResponseResult<TbExpandovalue>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbExpandovalue> updateTbExpandovalue(TbExpandovalue tbExpandovalue) {
        ResponseResult<TbExpandovalue> responseResult = new ResponseResult<TbExpandovalue>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public List<TbExpandovalue> queryValueList(String resourceId, Long tableId, Long columnId, String recordId) {
        return null;
    }

    @Override
    public ResponseResult<List<ExpandovalueVo>> queryExpandovalueVoList(String tableName, String recordId) {
        ResponseResult<List<ExpandovalueVo>> responseResult = new ResponseResult<>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
