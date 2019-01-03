package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.common.system.client.SysMenuClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysMenuVO;
@Component
public class SysMenuClientHystrix implements SysMenuClient{

    

    @Override
    public ResponseResult<List<SysMenu>> getMenuByAccout(String accout) {
        ResponseResult<List<SysMenu>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Page<SysMenu>> listPage(Integer pageNo, Integer pageSize, String keyWord) {
        ResponseResult<Page<SysMenu>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<SysMenuVO> get(Long id) {
        ResponseResult<SysMenuVO> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Void> update(SysMenu sysMenu) {
        ResponseResult<Void> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Void> add(SysMenu sysMenu) {
        ResponseResult<Void> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Void> deletePrivilege(SysMenu sysMenu) {
        ResponseResult<Void> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
