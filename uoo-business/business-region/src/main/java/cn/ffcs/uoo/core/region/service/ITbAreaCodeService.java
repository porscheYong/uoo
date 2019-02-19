package cn.ffcs.uoo.core.region.service;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.core.region.entity.TbAreaCode;
import cn.ffcs.uoo.core.region.vo.AreaCodeVO;

/**
 * <p>
 * 区号 服务类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public interface ITbAreaCodeService extends IService<TbAreaCode> {
    public Long getId();
    public List<AreaCodeVO> selectListAreaCode(Page<AreaCodeVO> page,HashMap<String,Object> map);
    public Long countListAreaCode(HashMap<String,Object> map);
    public List<TbAreaCode> getAreaCodeByPollocId(Long id);
}
