package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.PrivFuncRelMapper;
import cn.ffcs.uoo.core.permission.entity.PrivFuncRel;
import cn.ffcs.uoo.core.permission.service.PrivFuncRelService;

/**
 * <p>
 * 定义权限关联的功能菜单、功能组件，一个权限可包含多个功能菜单或功能组件。 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@Service
public class PrivFuncRelServiceImpl extends ServiceImpl<PrivFuncRelMapper, PrivFuncRel> implements PrivFuncRelService {
    @Autowired
    PrivFuncRelMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }

}
