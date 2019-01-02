package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.ffcs.uoo.web.maindata.common.system.client.SysElementClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysElement;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysElementVO;
@Component
public class SysElementClientHystrix implements SysElementClient {

    @Override
    public ResponseResult<List<SysElement>> list(Integer pageNo, Integer pageSize, String keyWord) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<List<SysElement>> getElementByAccout(String accout) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<SysElementVO> get(Long id) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> add(SysElement ele) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> update(SysElement ele) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> delete(SysElement fun) {
        return ResponseResult.createErrorResult("服务不可用");
    }

}
