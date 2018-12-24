package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysPrivFileRel;
import cn.ffcs.uoo.system.dao.SysPrivFileRelMapper;
import cn.ffcs.uoo.system.service.ISysPrivFileRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定义权限关联的文件，一个权限可包含多个文件。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@Service
public class SysPrivFileRelServiceImpl extends ServiceImpl<SysPrivFileRelMapper, SysPrivFileRel> implements ISysPrivFileRelService {
    @Override
    public Long getId() {
        return baseMapper.getId();
    }
}
