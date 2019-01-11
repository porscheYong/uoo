package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.Systemtable;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 所有业务表都要登记，扩展表需要登记,非扩展表无须定义 Mapper 接口
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
public interface SystemtableMapper extends BaseMapper<Systemtable> {

}
