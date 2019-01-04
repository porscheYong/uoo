package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysFunction;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

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

}
