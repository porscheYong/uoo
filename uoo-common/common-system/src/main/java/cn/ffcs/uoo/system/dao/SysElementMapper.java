package cn.ffcs.uoo.system.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.system.entity.SysElement;
import cn.ffcs.uoo.system.vo.PermElement;

/**
 * <p>
 * 只有需要权限控制的元素才进行登记 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysElementMapper extends BaseMapper<SysElement> {
    List<SysElement> getElementByAccout(String accout);
    Integer getId();
    public List<PermElement> listByPermissionId(Long permId);
}
