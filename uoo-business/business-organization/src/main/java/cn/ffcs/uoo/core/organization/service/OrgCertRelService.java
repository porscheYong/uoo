package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgCertRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 解决智慧BSS组织机构接入过程中的法人信息问题 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
public interface OrgCertRelService extends IService<OrgCertRel> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param orgCertRel
     */
    public void delete(OrgCertRel orgCertRel);

    /**
     * 新增
     */
    public void add(OrgCertRel orgCertRel);

    /**
     * 更新
     */
    public void update(OrgCertRel orgCertRel);


//    /**
//     * 获取组织信息
//     */
//    public void selectOrgCerRelByOrgId();
}
