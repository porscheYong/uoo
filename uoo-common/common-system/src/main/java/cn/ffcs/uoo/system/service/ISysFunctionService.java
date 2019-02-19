package cn.ffcs.uoo.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.system.entity.SysFunction;
import cn.ffcs.uoo.system.vo.PermFunction;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface ISysFunctionService extends IService<SysFunction> {
    Long getId();
    List<SysFunction> getFunctionByAccout(String accout);
    public List<PermFunction> listByPermissionId(Long permId);
}
