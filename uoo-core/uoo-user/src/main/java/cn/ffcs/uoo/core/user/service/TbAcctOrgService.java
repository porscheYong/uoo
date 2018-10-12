package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbAcctOrg;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 主账号与组织关系 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbAcctOrgService extends IService<TbAcctOrg> {

    long saveAcctOrg(TbAcctOrg tbAcctOrg);

    void removeAcctOrg(TbAcctOrg tbAcctOrg);
}
