package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.TreeStaffTypeRel;
import cn.ffcs.uoo.core.organization.dao.TreeStaffTypeRelMapper;
import cn.ffcs.uoo.core.organization.service.TreeStaffTypeRelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-28
 */
@Service
public class TreeStaffTypeRelServiceImpl extends ServiceImpl<TreeStaffTypeRelMapper, TreeStaffTypeRel> implements TreeStaffTypeRelService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }


    @Override
    public void delete(TreeStaffTypeRel treeStaffTypeRel){
        treeStaffTypeRel.setStatusCd("1100");
        treeStaffTypeRel.setStatusDate(new Date());
        treeStaffTypeRel.setUpdateDate(new Date());
        treeStaffTypeRel.setUpdateUser(0L);
        updateById(treeStaffTypeRel);
    }

    @Override
    public void add(TreeStaffTypeRel treeStaffTypeRel){
        treeStaffTypeRel.setCreateDate(new Date());
        treeStaffTypeRel.setCreateUser(0L);
        treeStaffTypeRel.setStatusCd("1000");
        treeStaffTypeRel.setStatusDate(new Date());
        insert(treeStaffTypeRel);
    }

    @Override
    public void update(TreeStaffTypeRel treeStaffTypeRel){
        treeStaffTypeRel.setUpdateDate(new Date());
        treeStaffTypeRel.setUpdateUser(0L);
        treeStaffTypeRel.setStatusDate(new Date());
        updateById(treeStaffTypeRel);
    }
}
