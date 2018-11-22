package cn.ffcs.uoo.core.personnel.dao;

import cn.ffcs.uoo.core.personnel.entity.TbPersonnelImage;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wudj
 * @since 2018-11-07
 */
public interface TbPersonnelImageMapper extends BaseMapper<TbPersonnelImage> {

    public void save(TbPersonnelImage tbPersonnelImage);

    public Long getId();

}
