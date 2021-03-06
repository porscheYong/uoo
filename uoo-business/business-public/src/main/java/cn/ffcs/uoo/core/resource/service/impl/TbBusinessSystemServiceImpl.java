package cn.ffcs.uoo.core.resource.service.impl;

import cn.ffcs.uoo.core.resource.entity.TbBusinessSystem;
import cn.ffcs.uoo.core.resource.dao.TbBusinessSystemMapper;
import cn.ffcs.uoo.core.resource.service.TbBusinessSystemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 接入系统表 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-11-16
 */
@Service
public class TbBusinessSystemServiceImpl extends ServiceImpl<TbBusinessSystemMapper, TbBusinessSystem> implements TbBusinessSystemService {
//
//    @Override
//    public List<TbBusinessSystem> getBusinessSystemList(){
//        return baseMapper.getBusinessSystemList();
//    }
}
