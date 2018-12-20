package cn.ffcs.uoo.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.entity.SysPositiontRoleRef;
import cn.ffcs.uoo.system.dao.SysPositiontRoleRefMapper;
import cn.ffcs.uoo.system.service.ISysPositiontRoleRefService;

/**
 * <p>
 * 对一定职位可以默认具备一些角色 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-20
 */
@Service
public class SysPositiontRoleRefServiceImpl extends ServiceImpl<SysPositiontRoleRefMapper, SysPositiontRoleRef> implements ISysPositiontRoleRefService {

}
