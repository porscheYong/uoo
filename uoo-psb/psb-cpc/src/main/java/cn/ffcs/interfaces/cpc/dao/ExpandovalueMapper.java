package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.Expandovalue;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 扩展值 Mapper 接口
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
public interface ExpandovalueMapper extends BaseMapper<Expandovalue> {

    Expandovalue selectValueByData(Expandovalue expandovalue);
}
