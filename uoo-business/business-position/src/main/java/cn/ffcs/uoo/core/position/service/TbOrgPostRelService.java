package cn.ffcs.uoo.core.position.service;

import cn.ffcs.uoo.core.position.entity.TbOrgPostRel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 组织职位关系
 * 职位，不同组织树具有不同的职位 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-30
 */
public interface TbOrgPostRelService extends IService<TbOrgPostRel> {
    /**
     * 新增组织职位关系
     * @param tbOrgPostRel
     * @return
     */
    int save(TbOrgPostRel tbOrgPostRel);

    /**
     * 删除组织职位关系
     * @param orgPostId
     * @param updateUser
     */
    void remove(Long orgPostId, Long updateUser);
}
