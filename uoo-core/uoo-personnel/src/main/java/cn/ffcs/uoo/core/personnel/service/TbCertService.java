package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbCert;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 证件 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbCertService extends IService<TbCert> {
    public Long getId();

    public void delete(TbCert tbCert);

    /**
     *  根据人员标识 获取证件信息
     * @param personnelId
     * @return
     */
    public TbCert getTbCertByPersonnelId(Integer personnelId);
}
