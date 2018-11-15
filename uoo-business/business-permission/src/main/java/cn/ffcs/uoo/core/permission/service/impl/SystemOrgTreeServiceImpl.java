package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.SystemOrgTreeMapper;
import cn.ffcs.uoo.core.permission.entity.SystemOrgTree;
import cn.ffcs.uoo.core.permission.service.SystemOrgTreeService;

/**
 * <p>
 * 接入系统组织树引用配置 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@Service
public class SystemOrgTreeServiceImpl extends ServiceImpl<SystemOrgTreeMapper, SystemOrgTree> implements SystemOrgTreeService {

}
