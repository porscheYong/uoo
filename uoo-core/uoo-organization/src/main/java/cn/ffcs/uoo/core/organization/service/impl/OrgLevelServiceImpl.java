package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgLevel;
import cn.ffcs.uoo.core.organization.dao.OrgLevelMapper;
import cn.ffcs.uoo.core.organization.service.OrgLevelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 组织在组织树中的层级 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@Service
public class OrgLevelServiceImpl extends ServiceImpl<OrgLevelMapper, OrgLevel> implements OrgLevelService {
    /**
     * 获取seq
     * @return
     */
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    /**
     * 失效状态
     * @param orgLevel
     */
    @Override
    public void delete(OrgLevel orgLevel){
        orgLevel.setStatusCd("1100");
        orgLevel.setStatusDate(new Date());
        orgLevel.setUpdateDate(new Date());
        orgLevel.setUpdateUser(0L);
        updateById(orgLevel);
    }



    /**
     * 新增
     */
    @Override
    public void add(OrgLevel orgLevel){
        orgLevel.setCreateDate(new Date());
        orgLevel.setCreateUser(0L);
        orgLevel.setStatusCd("1000");
        orgLevel.setStatusDate(new Date());
        insert(orgLevel);
    }

    /**
     * 更新
     */
    @Override
    public void update(OrgLevel orgLevel){
        orgLevel.setUpdateDate(new Date());
        orgLevel.setUpdateUser(0L);
        orgLevel.setStatusDate(new Date());
        updateById(orgLevel);
    }
}
