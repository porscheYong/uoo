package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgOrgtreeRel;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
public interface OrgOrgtreeRelService extends IService<OrgOrgtreeRel> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param orgOrgtreeRel
     */
    public void delete(OrgOrgtreeRel orgOrgtreeRel);

    /**
     *
     * @param orgOrgtreeRef
     */
    public void add(OrgOrgtreeRel orgOrgtreeRef);
    /**
     *
     * @param orgOrgtreeRef
     */
    public void update(OrgOrgtreeRel orgOrgtreeRef);

}
