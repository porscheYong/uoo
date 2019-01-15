package cn.ffcs.interfaces.cpc.service;

import cn.ffcs.interfaces.cpc.pojo.Expandovalue;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 扩展值 服务类
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
public interface ExpandovalueService extends IService<Expandovalue> {

    Expandovalue selectValueByData(Expandovalue expandovalue);
}
