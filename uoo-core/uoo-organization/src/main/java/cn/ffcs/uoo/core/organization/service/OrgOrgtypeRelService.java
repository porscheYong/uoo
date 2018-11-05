package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgOrgtypeRel;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OrgOrgtypeRelService extends IService<OrgOrgtypeRel> {

    public Long getId();

    public void delete(OrgOrgtypeRel orgOrgtypeRel);

    /**
     * 新增
     * @param orgOrgtypeRel
     */
    public void add(OrgOrgtypeRel orgOrgtypeRel);
    /**
     *
     * @param orgOrgtypeRel
     */
    public void update(OrgOrgtypeRel orgOrgtypeRel);

}
