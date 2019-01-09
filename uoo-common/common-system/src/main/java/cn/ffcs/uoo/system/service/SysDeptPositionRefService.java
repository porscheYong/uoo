package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.SysDeptPositionRef;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 对部门可选岗位的限定 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
public interface SysDeptPositionRefService extends IService<SysDeptPositionRef> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param sysDeptPositionRef
     */
    public void delete(SysDeptPositionRef sysDeptPositionRef);

    /**
     * 新增
     * @param sysDeptPositionRef
     */
    public void add(SysDeptPositionRef sysDeptPositionRef);

    /**
     * 更新
     * @param sysDeptPositionRef
     */
    public void update(SysDeptPositionRef sysDeptPositionRef);


    public List<SysDeptPositionRef> getDeptPositionRelList(String orgCode);


}
