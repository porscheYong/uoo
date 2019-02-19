package cn.ffcs.uoo.system.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import cn.ffcs.uoo.system.entity.SysFunction;
import cn.ffcs.uoo.system.vo.PermFunction;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysFunctionMapper extends BaseMapper<SysFunction> {
    List<SysFunction> getFunctionByAccout(String accout);
    Long getId();
    public List<PermFunction> listByPermissionId(Long permId);
}
