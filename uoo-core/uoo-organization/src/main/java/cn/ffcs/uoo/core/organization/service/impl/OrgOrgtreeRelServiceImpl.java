package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgOrgtreeRel;
import cn.ffcs.uoo.core.organization.dao.OrgOrgtreeRelMapper;
import cn.ffcs.uoo.core.organization.service.OrgOrgtreeRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
@Service
public class OrgOrgtreeRelServiceImpl extends ServiceImpl<OrgOrgtreeRelMapper, OrgOrgtreeRel> implements OrgOrgtreeRelService {
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
     * @param orgOrgtreeRef
     */
    @Override
    public void delete(OrgOrgtreeRel orgOrgtreeRef){
        orgOrgtreeRef.setStatusCd("1100");
        orgOrgtreeRef.setStatusDate(new Date());
        orgOrgtreeRef.setUpdateDate(new Date());
        orgOrgtreeRef.setUpdateUser(0L);
        updateById(orgOrgtreeRef);
    }

    /**
     * 新增
     * @param orgOrgtreeRef
     */
    @Override
    public void add(OrgOrgtreeRel orgOrgtreeRef){
        orgOrgtreeRef.setCreateDate(new Date());
        orgOrgtreeRef.setCreateUser(0L);
        orgOrgtreeRef.setStatusCd("1000");
        orgOrgtreeRef.setStatusDate(new Date());
        insert(orgOrgtreeRef);
    }

    /**
     * 更新
     */
    @Override
    public void update(OrgOrgtreeRel orgOrgtreeRef){
        orgOrgtreeRef.setUpdateDate(new Date());
        orgOrgtreeRef.setUpdateUser(0L);
        orgOrgtreeRef.setStatusDate(new Date());
        updateById(orgOrgtreeRef);
    }
}
