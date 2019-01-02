package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysFunction;
import cn.ffcs.uoo.system.dao.SysFunctionMapper;
import cn.ffcs.uoo.system.service.ISysFunctionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

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

}
