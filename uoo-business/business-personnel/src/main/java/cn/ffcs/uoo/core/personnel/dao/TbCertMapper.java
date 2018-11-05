package cn.ffcs.uoo.core.personnel.dao;

import cn.ffcs.uoo.base.common.annotion.MyBatisDao;
import cn.ffcs.uoo.core.personnel.entity.TbCert;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 证件 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@MyBatisDao
public interface TbCertMapper extends BaseMapper<TbCert> {
    public Long getId();

    public void delete(TbCert tbCert);

    public TbCert getTbCertByPersonnelId(Integer personnelId);
}
