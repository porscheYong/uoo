package cn.ffcs.uoo.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.dao.SysFunctionMapper;
import cn.ffcs.uoo.system.entity.SysFunction;
import cn.ffcs.uoo.system.service.ISysFunctionService;
import cn.ffcs.uoo.system.vo.PermFunction;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@Service
public class SysFunctionServiceImpl extends ServiceImpl<SysFunctionMapper, SysFunction> implements ISysFunctionService {

    @Override
    public List<SysFunction> getFunctionByAccout(String accout) {
        return baseMapper.getFunctionByAccout(accout);
    }

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public List<PermFunction> listByPermissionId(Long permId) {
        return baseMapper.listByPermissionId(permId);
    }

}
