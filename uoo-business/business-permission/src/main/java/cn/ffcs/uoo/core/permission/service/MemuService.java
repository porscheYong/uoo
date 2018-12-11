package cn.ffcs.uoo.core.permission.service;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.core.permission.entity.Memu;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
public interface MemuService extends IService<Memu> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 信息失效
     * @param memu
     */
    public void delete(Memu memu);

    /**
     * 新增
     */
    public void add(Memu memu);

    /**
     * 更新
     */
    public void update(Memu memu);

}
