package cn.ffcs.uoo.web.maindata.sysuser.client.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.ffcs.uoo.web.maindata.sysuser.client.SysMenuClient;
import cn.ffcs.uoo.web.maindata.sysuser.dto.SysMenu;
import cn.ffcs.uoo.web.maindata.sysuser.vo.ResponseResult;
@Component
public class SysMenuClientHystrix implements SysMenuClient{

    @Override
    public ResponseResult<List<SysMenu>> listPage(Integer pageNo, Integer pageSize) {
        ResponseResult<List<SysMenu>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<List<SysMenu>> getMenuByAccout(String accout) {
        ResponseResult<List<SysMenu>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
