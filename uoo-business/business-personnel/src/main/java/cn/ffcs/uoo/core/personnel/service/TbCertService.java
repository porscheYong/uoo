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
    public TbCert getTbCertByPersonnelId(Long personnelId);

    /**
     * 根据personnelId 删除 证件信息
     * @param personnelId
     * @return
     */
    public Object delTbCertByPsnId(Long personnelId);

    /**
     * 证件查询
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Object getCertInfo(String keyWord, Integer pageNo, Integer pageSize);

    /**
     * 根据id  删除证件
     * @param certId
     * @return
     */
    public Object delTbCertById(Long certId);

    /**
     * 身份证新增或更新
     * @param personnelId
     * @param certType
     * @param certNo
     * @param certName
     * @param address
     * @return
     */
    public Object insertOrUpdateTbCert(Long personnelId, String certType, String certNo, String certName, String address);
}
