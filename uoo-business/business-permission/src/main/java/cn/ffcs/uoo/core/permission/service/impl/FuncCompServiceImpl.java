package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.FuncCompMapper;
import cn.ffcs.uoo.core.permission.entity.FuncComp;
import cn.ffcs.uoo.core.permission.service.FuncCompService;

/**
 * <p>
 * 指系统内的系统功能菜单的最小功能单元及组件。 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@Service
public class FuncCompServiceImpl extends ServiceImpl<FuncCompMapper, FuncComp> implements FuncCompService {
    @Autowired
    FuncCompMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }

}
