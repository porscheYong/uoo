package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.PrivDataRelMapper;
import cn.ffcs.uoo.core.permission.entity.PrivDataRel;
import cn.ffcs.uoo.core.permission.service.IPrivDataRelService;

/**
 * <p>
 * 记录权限与业务对象之间多对多的关系 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@Service
public class PrivDataRelServiceImpl extends ServiceImpl<PrivDataRelMapper, PrivDataRel> implements IPrivDataRelService {
    @Autowired
    private PrivDataRelMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
}
