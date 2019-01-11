package cn.ffcs.interfaces.cpc.service;

import cn.ffcs.interfaces.cpc.pojo.Systemtable;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 所有业务表都要登记，扩展表需要登记,非扩展表无须定义 服务类
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
public interface SystemtableService extends IService<Systemtable> {

}
