package cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbSystemtable;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.TbSystemtableClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import org.springframework.stereotype.Component;

@Component
public class TbSystemtableClientHystrix implements TbSystemtableClient {
    @Override
    public ResponseResult<TbSystemtable> addTbSystemtable(TbSystemtable tbSystemtable) {
        ResponseResult<TbSystemtable> responseResult = new ResponseResult<TbSystemtable>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbSystemtable> updateTbSystemtable(TbSystemtable tbSystemtable) {
        ResponseResult<TbSystemtable> responseResult = new ResponseResult<TbSystemtable>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbSystemtable> removeTbSystemtable(Long tableId, Long updateUser) {
        ResponseResult<TbSystemtable> responseResult = new ResponseResult<TbSystemtable>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
