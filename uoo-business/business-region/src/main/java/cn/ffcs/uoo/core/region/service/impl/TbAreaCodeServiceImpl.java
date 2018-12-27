package cn.ffcs.uoo.core.region.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.region.dao.TbAreaCodeMapper;
import cn.ffcs.uoo.core.region.entity.TbAreaCode;
import cn.ffcs.uoo.core.region.service.ITbAreaCodeService;
import cn.ffcs.uoo.core.region.vo.AreaCodeVO;

/**
 * <p>
 * 区号 服务实现类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@Service
public class TbAreaCodeServiceImpl extends ServiceImpl<TbAreaCodeMapper, TbAreaCode> implements ITbAreaCodeService {
    @Override
    public Long getId() {
        return baseMapper.getId();
    }
    @Override
    public List<AreaCodeVO> selectListAreaCode(HashMap<String, Object> map) {
        return baseMapper.selectListAreaCode(map);
    }
    @Override
    public Long countListAreaCode(HashMap<String,Object> map) {
        return baseMapper.countListAreaCode(map);
    }
    @Override
    public List<TbAreaCode> getAreaCodeByPollocId(Long id) {
        return baseMapper.getAreaCodeByPollocId(id);
    }
    

}
