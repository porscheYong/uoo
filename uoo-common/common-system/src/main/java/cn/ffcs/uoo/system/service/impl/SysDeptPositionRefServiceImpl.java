package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.dao.SysDeptPositionRefMapper;
import cn.ffcs.uoo.system.entity.SysDeptPositionRef;
import cn.ffcs.uoo.system.service.SysDeptPositionRefService;
import cn.ffcs.uoo.system.util.StrUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 对部门可选岗位的限定 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@Service
public class SysDeptPositionRefServiceImpl extends ServiceImpl<SysDeptPositionRefMapper, SysDeptPositionRef> implements SysDeptPositionRefService {
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
     * @param sysDeptPositionRef
     */
    @Override
    public void delete(SysDeptPositionRef sysDeptPositionRef){
        sysDeptPositionRef.setStatusCd("1100");
        sysDeptPositionRef.setStatusDate(new Date());
        sysDeptPositionRef.setUpdateDate(new Date());
        sysDeptPositionRef.setUpdateUser(StrUtil.isNullOrEmpty(sysDeptPositionRef.getUpdateUser())?0L:sysDeptPositionRef.getUpdateUser());
        updateById(sysDeptPositionRef);
    }



    /**
     * 新增
     */
    @Override
    public void add(SysDeptPositionRef sysDeptPositionRef){
        sysDeptPositionRef.setCreateDate(new Date());
        sysDeptPositionRef.setCreateUser(StrUtil.isNullOrEmpty(sysDeptPositionRef.getCreateUser())?0L:sysDeptPositionRef.getCreateUser());
        sysDeptPositionRef.setStatusCd("1000");
        sysDeptPositionRef.setStatusDate(new Date());
        insert(sysDeptPositionRef);
    }

    /**
     * 更新
     */
    @Override
    public void update(SysDeptPositionRef sysDeptPositionRef){
        sysDeptPositionRef.setUpdateDate(new Date());
        sysDeptPositionRef.setUpdateUser(StrUtil.isNullOrEmpty(sysDeptPositionRef.getUpdateUser())?0L:sysDeptPositionRef.getUpdateUser());
        sysDeptPositionRef.setStatusDate(new Date());
        updateById(sysDeptPositionRef);
    }

    @Override
    public List<SysDeptPositionRef> getDeptPositionRelList(String orgCode){
        return baseMapper.getDeptPositionRelList(orgCode);
    }
}
