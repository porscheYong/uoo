package cn.ffcs.uoo.core.region.service;

import cn.ffcs.uoo.core.region.entity.TbAreaCode;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 区号 服务类
 * </p>
 *
 * @author zxs
 * @since 2018-10-30
 */
public interface ITbAreaCodeService extends IService<TbAreaCode> {
    public Long getId();
}
