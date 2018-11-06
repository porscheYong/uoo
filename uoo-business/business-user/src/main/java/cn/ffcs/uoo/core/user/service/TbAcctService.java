package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbAcct;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 主账号 服务类
 * </p>
 *
 * @author wudj
 * @since 2018-10-24
 */
public interface TbAcctService extends IService<TbAcct> {

    public Long getId();

    /**
     * 保存主账号
     * @param tbAcct
     */
    long saveAcct(TbAcct tbAcct);

    /**
     * 失效一条数据
     * @param tbAcct
     */
    void removeAcct(TbAcct tbAcct);

    List<TbAcct> selectAcctList(TbAcct tbAcct);
}