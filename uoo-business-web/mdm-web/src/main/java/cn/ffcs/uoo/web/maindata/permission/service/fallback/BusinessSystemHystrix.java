package cn.ffcs.uoo.web.maindata.permission.service.fallback;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.ffcs.uoo.web.maindata.permission.dto.BusinessSystem;
import cn.ffcs.uoo.web.maindata.permission.service.BusinessSystemService;
import cn.ffcs.uoo.web.maindata.permission.vo.ResponseResult;
@Service
public class BusinessSystemHystrix implements BusinessSystemService {

    @Override
    public ResponseResult<List<BusinessSystem>> listBusinessSystemByOrgTree(Long treeId) {
        return ResponseResult.createErrorResult("服务不可用");
    }

}
