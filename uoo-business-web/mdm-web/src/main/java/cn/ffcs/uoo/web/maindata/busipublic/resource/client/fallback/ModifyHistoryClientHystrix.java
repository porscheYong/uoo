package cn.ffcs.uoo.web.maindata.busipublic.resource.client.fallback;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.busipublic.resource.client.ModifyHistoryClient;
import cn.ffcs.uoo.web.maindata.busipublic.resource.dto.ModifyHistory;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;

@Component
public class ModifyHistoryClientHystrix implements ModifyHistoryClient{

    @Override
    public ResponseResult<Page<ModifyHistory>> listByRecord(Integer pageNo, Integer pageSize, String tableName,
            String recordId) {
        ResponseResult<Page<ModifyHistory>> ret=new ResponseResult<>();
        ret.setState(1100);
        ret.setMessage("服务不可用");;
        return ret;
    }

}
