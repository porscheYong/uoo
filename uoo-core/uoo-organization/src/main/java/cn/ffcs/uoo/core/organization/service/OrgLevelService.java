package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgLevel;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 * 组织在组织树中的层级 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
public interface OrgLevelService extends IService<OrgLevel> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param orgLevel
     */
    public void delete(OrgLevel orgLevel);

    /**
     * 新增
     */
    public void add(OrgLevel orgLevel);

    /**
     * 更新
     */
    public void update(OrgLevel orgLevel);
}
