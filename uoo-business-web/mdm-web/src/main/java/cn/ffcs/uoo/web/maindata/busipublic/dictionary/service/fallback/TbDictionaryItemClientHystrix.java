package cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.fallback;

import cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto.DictionaryListVo;
import cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto.TbDictionaryItem;
import cn.ffcs.uoo.web.maindata.busipublic.dictionary.service.TbDictionaryItemClient;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 字典项客户端断路器
 * @author zhanglu
 * @date 2018-11-08
 */
@Component
public class TbDictionaryItemClientHystrix implements TbDictionaryItemClient {
    @Override
    public ResponseResult<TbDictionaryItem> updateTbDictionaryItem(TbDictionaryItem tbDictionaryItem) {
        ResponseResult<TbDictionaryItem> responseResult = new ResponseResult<TbDictionaryItem>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbDictionaryItem> addTbDictionaryItem(TbDictionaryItem tbDictionaryItem) {
        ResponseResult<TbDictionaryItem> responseResult = new ResponseResult<TbDictionaryItem>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<TbDictionaryItem> removeTbDictionaryItem(Long itemId, Long updateUser) {
        ResponseResult<TbDictionaryItem> responseResult = new ResponseResult<TbDictionaryItem>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<List<TbDictionaryItem>> queryListByDictionaryName(String dictionaryName) {
        return null;
    }

    @Override
    public ResponseResult<DictionaryListVo> queryAllList() {
        ResponseResult<DictionaryListVo> responseResult = new ResponseResult<>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
