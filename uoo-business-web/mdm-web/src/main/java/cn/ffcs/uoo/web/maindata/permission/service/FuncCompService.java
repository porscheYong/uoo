package cn.ffcs.uoo.web.maindata.permission.service;

import cn.ffcs.uoo.core.permission.entity.FuncComp;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 指系统内的系统功能菜单的最小功能单元及组件。 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
public interface FuncCompService extends IService<FuncComp> {
    Long getId();
}
