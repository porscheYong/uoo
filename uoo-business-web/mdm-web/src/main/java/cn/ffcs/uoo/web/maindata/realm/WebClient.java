package cn.ffcs.uoo.web.maindata.realm;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;

@FeignClient(value = "mdm-web", fallback = WebClientHystrix.class)
public interface WebClient {
    @GetMapping("/testReloadUrlPermission")
    public ResponseResult<Void> testReloadUrlPermission();
    @GetMapping("/reloadUrlPermission")
    public ResponseResult<Void> reloadUrlPermission();
}
