package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OgtOrgtypeConf;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OgtOrgtypeConfService extends IService<OgtOrgtypeConf> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效数据
     * @param ogtOrgtypeConf
     */
    public void delete(OgtOrgtypeConf ogtOrgtypeConf);

    /**
     * 新增
     */
    public void add(OgtOrgtypeConf ogtOrgtypeConf);

    /**
     * 更新
     */
    public void update(OgtOrgtypeConf ogtOrgtypeConf);


}
