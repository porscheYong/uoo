package cn.ffcs.uoo.core.region.service;

import cn.ffcs.uoo.core.region.entity.TbExch;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 描述信令所指的方向,如到某个局(每局对应一个DPC)的信令,可称到某局的局向。 服务类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public interface ITbExchService extends IService<TbExch> {
    public Long getId();
}
