package cn.ffcs.uoo.core.organization.Api.service.impl;

import cn.ffcs.uoo.core.organization.Api.service.ExpandovalueService;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.ExpandovalueVo;
import cn.ffcs.uoo.core.organization.vo.TbExpandovalue;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 *
 */
@Component
public class ExpandovalueServiceHystrix implements ExpandovalueService {

    @Override
    public ResponseResult<List<ExpandovalueVo>> queryExpandovalueVoList(String tableName, String recordId){
        ResponseResult<List<ExpandovalueVo>> ret = new ResponseResult<List<ExpandovalueVo>>();
        ret.setMessage("调用公共管理接口[queryExpandovalueVoList]报错");
        ret.setState(ResponseResult.PARAMETER_ERROR);
        return ret;
    }
    @Override
    public ResponseResult<TbExpandovalue> updateTbExpandovalue(TbExpandovalue tbExpandovalue){
        ResponseResult<TbExpandovalue> ret = new ResponseResult<TbExpandovalue>();
        ret.setMessage("调用公共管理接口[updateTbExpandovalue]报错");
        ret.setState(ResponseResult.PARAMETER_ERROR);
        return ret;
    }
    @Override
    public ResponseResult<ExpandovalueVo> addExpandoInfo(ExpandovalueVo expandovalueVo){
        ResponseResult<ExpandovalueVo> ret = new ResponseResult<ExpandovalueVo>();
        ret.setMessage("调用公共管理接口[addExpandoInfo]报错");
        ret.setState(ResponseResult.PARAMETER_ERROR);
        return ret;
    }

//    @Override
//    public ResponseResult<TbExpandovalue> removeTbExpandovalue(Long valueId,Long updateUser){
//        ResponseResult<TbExpandovalue> ret = new ResponseResult<TbExpandovalue>();
//        ret.setMessage("调用公共管理接口[removeTbExpandovalue]报错");
//        ret.setState(ResponseResult.PARAMETER_ERROR);
//        return ret;
//    }

}
