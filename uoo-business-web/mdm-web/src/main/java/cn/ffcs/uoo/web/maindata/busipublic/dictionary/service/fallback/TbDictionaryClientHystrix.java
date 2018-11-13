package cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.fallback;

import cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto.TbDictionary;
import cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.TbDictionaryClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import org.springframework.stereotype.Component;

/**
 * 字典客户端断路器
 * @author zhanglu
 * @date 2018-11-08
 */
@Component
public class TbDictionaryClientHystrix implements TbDictionaryClient {
    @Override
    public ResponseResult<TbDictionary> updateTbDictionary(TbDictionary tbDictionary) {
        ResponseResult<TbDictionary> responseResult = new ResponseResult<TbDictionary>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbDictionary> addTbDictionary(TbDictionary tbDictionary) {
        ResponseResult<TbDictionary> responseResult = new ResponseResult<TbDictionary>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbDictionary> removeTbDictionary(String dictionaryName, Long updateUser) {
        ResponseResult<TbDictionary> responseResult = new ResponseResult<TbDictionary>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
