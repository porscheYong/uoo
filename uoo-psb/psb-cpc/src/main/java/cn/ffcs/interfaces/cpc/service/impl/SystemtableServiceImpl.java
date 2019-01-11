package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.pojo.Systemtable;
import cn.ffcs.interfaces.cpc.dao.SystemtableMapper;
import cn.ffcs.interfaces.cpc.service.SystemtableService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 所有业务表都要登记，扩展表需要登记,非扩展表无须定义 服务实现类
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
@Service
public class SystemtableServiceImpl extends ServiceImpl<SystemtableMapper, Systemtable> implements SystemtableService {
}
