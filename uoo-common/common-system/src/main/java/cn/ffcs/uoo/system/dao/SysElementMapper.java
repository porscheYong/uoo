package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysElement;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 只有需要权限控制的元素才进行登记 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysElementMapper extends BaseMapper<SysElement> {

}
