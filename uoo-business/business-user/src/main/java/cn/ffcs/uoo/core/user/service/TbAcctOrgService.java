package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbAcctOrg;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 主账号与组织关系 服务类
 * </p>
 *
 * @author wudj
 * @since 2018-10-14
 */
public interface TbAcctOrgService extends IService<TbAcctOrg> {

    /**
     * 新增 主账号与组织关系
     * @param tbAcctOrg
     * @return
     */
    long saveAcctOrg(TbAcctOrg tbAcctOrg);

    /**
     * 删除 主账号与组织关系
     * @param tbAcctOrg
     */
    void removeAcctOrg(TbAcctOrg tbAcctOrg);
}
