package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.Expandorow;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 保留，如通讯号码就对应多个 Mapper 接口
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
public interface ExpandorowMapper extends BaseMapper<Expandorow> {

    /**
     * 重写selectOne方法
     * @param expandorow
     * @return
     */
    Expandorow selectOneExpandRow(Expandorow expandorow);
}
