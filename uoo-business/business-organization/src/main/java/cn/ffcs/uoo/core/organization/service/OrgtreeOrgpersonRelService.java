package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgtreeOrgpersonRel;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 * 描述该员工挂着到哪些专业树上 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-28
 */
public interface OrgtreeOrgpersonRelService extends IService<OrgtreeOrgpersonRel> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效数据
     * @param orgtreeOrgpersonRel
     */
    public void delete(OrgtreeOrgpersonRel orgtreeOrgpersonRel);


    public void add(OrgtreeOrgpersonRel orgtreeOrgpersonRel);


    public void update(OrgtreeOrgpersonRel orgtreeOrgpersonRel);
}
