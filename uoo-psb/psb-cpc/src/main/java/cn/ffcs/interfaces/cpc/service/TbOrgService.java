package cn.ffcs.interfaces.cpc.service;

import cn.ffcs.interfaces.cpc.pojo.TbOrg;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**@author lxd
 * @since 2019-01-10
 */
public interface TbOrgService extends IService<TbOrg> {
    /**
     * 渠道信息落到TB_ORG表中
     * @param tbOrg
     */
    void insertChannel(TbOrg tbOrg);
}
