package cn.ffcs.uoo.core.personnel.dao;

import cn.ffcs.uoo.core.personnel.entity.TbEdu;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
public interface TbEduMapper extends BaseMapper<TbEdu> {

    public Long getId();

    public void delete(TbEdu tbEdu);

}
