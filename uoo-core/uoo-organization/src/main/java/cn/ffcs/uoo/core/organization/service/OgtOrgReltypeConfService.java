package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OgtOrgReltypeConf;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OgtOrgReltypeConfService extends IService<OgtOrgReltypeConf> {

    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 信息失效
     * @param ogtOrgReltypeConf
     */
    public void delete(OgtOrgReltypeConf ogtOrgReltypeConf);

    /**
     * 新增
     */
    public void add(OgtOrgReltypeConf ogtOrgReltypeConf);

    /**
     * 更新
     */
    public void update(OgtOrgReltypeConf ogtOrgReltypeConf);

}
