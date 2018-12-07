package cn.ffcs.uoo.web.maindata.realm;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class WebClientHystrix implements WebClient {

    @Override
    public ResponseResult<Void> testReloadUrlPermission() {
        return ResponseResult.createErrorResult("");
    }
    @Override
    public ResponseResult<Void> reloadUrlPermission() {
        return ResponseResult.createErrorResult("");
    }

}
