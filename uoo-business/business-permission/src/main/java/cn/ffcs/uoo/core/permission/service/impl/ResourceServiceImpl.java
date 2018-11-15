package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.ResourceMapper;
import cn.ffcs.uoo.core.permission.entity.Resource;
import cn.ffcs.uoo.core.permission.service.ResourceService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}
