package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 主账号扩展 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbAcctExtService extends IService<TbAcctExt> {

    long saveAcctExt(TbAcctExt tbAcctExt);

    void removeAcctExt(TbAcctExt tbAcctExt);
}
