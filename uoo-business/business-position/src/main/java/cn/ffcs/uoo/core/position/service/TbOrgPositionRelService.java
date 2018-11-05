package cn.ffcs.uoo.core.position.service;

import cn.ffcs.uoo.core.position.entity.TbOrgPositionRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 岗位组织关系，不同组织树具有不同的岗位 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-11-01
 */
public interface TbOrgPositionRelService extends IService<TbOrgPositionRel> {
    /**
     * 新增岗位组织关系
     * @param tbOrgPositionRel
     * @return
     */
    int save(TbOrgPositionRel tbOrgPositionRel);

    /**
     * 删除岗位组织关系
     * @param orgPositionId
     * @param updateUser
     */
    void remove(Long orgPositionId, Long updateUser);
}
