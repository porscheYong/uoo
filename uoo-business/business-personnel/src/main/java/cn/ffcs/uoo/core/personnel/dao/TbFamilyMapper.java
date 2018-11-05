package cn.ffcs.uoo.core.personnel.dao;

import cn.ffcs.uoo.core.personnel.entity.TbFamily;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
public interface TbFamilyMapper extends BaseMapper<TbFamily> {

    public Long getId();

    public void save(TbFamily tbFamily);

    public void delete(TbFamily tbFamily);
}
