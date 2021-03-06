package cn.ffcs.uoo.core.region.dao;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.base.common.annotion.MyBatisDao;
import cn.ffcs.uoo.core.region.entity.TbAreaCode;
import cn.ffcs.uoo.core.region.vo.AreaCodeVO;

/**
 * <p>
 * 区号 Mapper 接口
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
@MyBatisDao
public interface TbAreaCodeMapper extends BaseMapper<TbAreaCode> {
    public List<TbAreaCode> getAreaCodeByPollocId(Long id);
    public Long getId();
    public List<AreaCodeVO> selectListAreaCode(Page<AreaCodeVO> page,HashMap<String,Object> map);
    public Long countListAreaCode(HashMap<String,Object> map);
}
