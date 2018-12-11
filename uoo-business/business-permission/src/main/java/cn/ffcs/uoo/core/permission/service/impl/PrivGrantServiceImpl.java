package cn.ffcs.uoo.core.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.PrivGrantMapper;
import cn.ffcs.uoo.core.permission.entity.PrivGrant;
import cn.ffcs.uoo.core.permission.service.IPrivGrantService;

/**
 * <p>
 * 描述将权限授权给不同的对象，包括系统用户、系统岗位、角色；
一个系统用户也可以拥有多个私有的权限，一个权限可以分配给多个系统用户。
一个系统岗位除了拥有角色所带的权限，也可以拥有私有的多个权限，一个权限可以分配给多个系统岗位。
一个角色可以包含多个权限，一个权限也可以分配给多个角色。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@Service
public class PrivGrantServiceImpl extends ServiceImpl<PrivGrantMapper, PrivGrant> implements IPrivGrantService {
    @Autowired
    private PrivGrantMapper mapper;
    @Override
    public Long getId() {
        return mapper.getId();
    }
    @Override
    public List<Map> selectPrivGrantByGranObj(HashMap<String, Object> params) {
        return mapper.selectPrivGrantByGranObj(params);
    }
}
