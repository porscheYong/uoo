package cn.ffcs.uoo.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.system.entity.SysElement;
import cn.ffcs.uoo.system.vo.PermElement;

/**
 * <p>
 * 只有需要权限控制的元素才进行登记 服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface ISysElementService extends IService<SysElement> {
    List<SysElement> getElementByAccout(String accout);
    Long getId();
    public List<PermElement> listByPermissionId(Long permId);
}
