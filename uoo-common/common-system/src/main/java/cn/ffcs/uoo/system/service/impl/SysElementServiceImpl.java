package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysElement;
import cn.ffcs.uoo.system.dao.SysElementMapper;
import cn.ffcs.uoo.system.service.ISysElementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 只有需要权限控制的元素才进行登记 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@Service
public class SysElementServiceImpl extends ServiceImpl<SysElementMapper, SysElement> implements ISysElementService {

    @Override
    public List<SysElement> getElementByAccout(String accout) {
        return baseMapper.getElementByAccout(accout);
    }

    @Override
    public Integer getId() {
        return baseMapper.getId();
    }

}
