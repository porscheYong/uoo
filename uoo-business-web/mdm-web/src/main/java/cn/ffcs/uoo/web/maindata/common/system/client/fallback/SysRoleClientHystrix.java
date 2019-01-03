package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.SysRoleClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysRole;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysRoleDTO;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
@Component
public class SysRoleClientHystrix implements SysRoleClient{

    

    @Override
    public ResponseResult<List<TreeNodeVo>> treeRole(String parentRoleCode) {
        ResponseResult<List<TreeNodeVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Page<SysRoleDTO>> listPage(Integer pageNo, Integer pageSize, String keyWord,
            String parentRoleCode, Integer includChild) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> update(SysRoleDTO sysRole) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> add(SysRoleDTO sysRole) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<Void> deleteRole(SysRole sysRole) {
        return ResponseResult.createErrorResult("服务不可用");
    }

    @Override
    public ResponseResult<SysRoleDTO> get(Long id) {
        return ResponseResult.createErrorResult("服务不可用");
    }

}
