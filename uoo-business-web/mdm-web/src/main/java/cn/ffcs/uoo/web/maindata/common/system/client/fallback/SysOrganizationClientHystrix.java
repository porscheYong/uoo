package cn.ffcs.uoo.web.maindata.common.system.client.fallback;

import cn.ffcs.uoo.web.maindata.common.system.client.SysMenuClient;
import cn.ffcs.uoo.web.maindata.common.system.client.SysOrganizationClient;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysMenu;
import cn.ffcs.uoo.web.maindata.common.system.vo.ResponseResult;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysMenuVO;
import cn.ffcs.uoo.web.maindata.common.system.vo.SysOrganizationVo;
import cn.ffcs.uoo.web.maindata.common.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SysOrganizationClientHystrix implements SysOrganizationClient {

    @Override
    public ResponseResult<List<TreeNodeVo>> getOrgRelTree(String id, boolean isSync,
                                                          Long userId, String accout){
        ResponseResult<List<TreeNodeVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<Page<SysOrganizationVo>> getFuzzyOrgRelPage(String search, Integer pageSize, Integer pageNo,
                                                                      Long userId, String accout){
        ResponseResult<Page<SysOrganizationVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
    @Override
    public ResponseResult<List<TreeNodeVo>> getRestructOrgRelTree(String id,Long userId, String accout){
        ResponseResult<List<TreeNodeVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }
    @Override
    public ResponseResult<Page<SysOrganizationVo>> getOrgRelPage(String id,
                                                                 String search,
                                                                 Integer pageSize,
                                                                 Integer pageNo,
                                                                 String isSearchlower,
                                                                 Long userId, String accout){
        ResponseResult<Page<SysOrganizationVo>> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<TreeNodeVo> addOrg(SysOrganizationVo vo){
        ResponseResult<TreeNodeVo> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<String> updateOrg(SysOrganizationVo vo){
        ResponseResult<String> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

    @Override
    public ResponseResult<SysOrganizationVo> getOrg(String id){
        ResponseResult<SysOrganizationVo> rr=new ResponseResult<>();
        rr.setState(ResponseResult.STATE_SERVICE_ERROR);
        rr.setMessage("服务不可用");
        return rr;
    }

}
