package cn.ffcs.uoo.core.personnel.dao;

import cn.ffcs.uoo.core.personnel.entity.TbPsnjob;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
public interface TbPsnjobMapper extends BaseMapper<TbPsnjob> {

    public Long getId();

    public void delete(TbPsnjob tbPsnjob);

}
