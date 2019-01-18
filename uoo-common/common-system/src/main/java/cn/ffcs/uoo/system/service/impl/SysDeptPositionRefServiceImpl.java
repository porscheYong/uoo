package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.dao.SysDeptPositionRefMapper;
import cn.ffcs.uoo.system.entity.SysDeptPositionRef;
import cn.ffcs.uoo.system.service.ModifyHistoryService;
import cn.ffcs.uoo.system.service.SysDeptPositionRefService;
import cn.ffcs.uoo.system.util.StrUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ModifyHistoryService modifyHistoryService;

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
        modifyHistoryService.addModifyHistory(sysDeptPositionRef,null,sysDeptPositionRef.getUpdateUser(),sysDeptPositionRef.getBatchNumber());
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
        modifyHistoryService.addModifyHistory(null,sysDeptPositionRef,sysDeptPositionRef.getCreateUser(),sysDeptPositionRef.getBatchNumber());
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
        Wrapper depPositionWrapper = Condition.create()
                .eq("DEPT_POSITION_REF_ID",sysDeptPositionRef.getDeptPositionRefId())
                .eq("STATUS_CD","1000");
        SysDeptPositionRef sysDeptPositionRefold = selectOne(depPositionWrapper);
        modifyHistoryService.addModifyHistory(sysDeptPositionRefold,sysDeptPositionRef,sysDeptPositionRef.getUpdateUser(),sysDeptPositionRef.getBatchNumber());
    }

    @Override
    public List<SysDeptPositionRef> getDeptPositionRelList(String orgCode){
        return baseMapper.getDeptPositionRelList(orgCode);
    }
}
