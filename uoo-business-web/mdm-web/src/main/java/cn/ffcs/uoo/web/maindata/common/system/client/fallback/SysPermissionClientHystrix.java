package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.SysPermissionClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermissionDTO;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysPermissionEditDTO;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
@Component
public class SysPermissionClientHystrix implements SysPermissionClient {

    @Override
    public ResponseResult<SysPermissionEditDTO> get(Long id) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Page<SysPermissionDTO>> listPage(Integer pageNo, Integer pageSize, String keyWord) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> add(SysPermissionEditDTO sysPermissionEditDTO) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> update(SysPermissionEditDTO sysPermissionEditDTO) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> delete(Long id) {
        return ResponseResult.createErrorResult("服务不可用");
    }

}
