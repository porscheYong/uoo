package cn.ffcs.uoo.core.personnel.dao;

import cn.ffcs.uoo.base.common.annotion.MyBatisDao;
import cn.ffcs.uoo.core.personnel.entity.TbContact;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 联系方式 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-13
 */
@MyBatisDao
public interface TbContactMapper extends BaseMapper<TbContact> {

    public Long getId();

    public void delete(TbContact tbContact);

}
