package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.dao.SysPositionMapper;
import cn.ffcs.uoo.system.service.SysPositionService;
import cn.ffcs.uoo.system.dao.SysPositionMapper;
import cn.ffcs.uoo.system.entity.SysPosition;
import cn.ffcs.uoo.system.service.SysPositionService;
import cn.ffcs.uoo.system.vo.SysPositionVo;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 根据不同部门的工作性质、责任轻重、难易程度和所需资格条件等进行分类，在平台上，不对职位进行过细的区分 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@Service
public class SysPositionServiceImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService {

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
     * @param sysPosition
     */
    @Override
    public void delete(SysPosition sysPosition){
        sysPosition.setStatusCd("1100");
        sysPosition.setStatusDate(new Date());
        sysPosition.setUpdateDate(new Date());
        sysPosition.setUpdateUser(0L);
        updateById(sysPosition);
    }



    /**
     * 新增
     */
    @Override
    public void add(SysPosition sysPosition){
        sysPosition.setCreateDate(new Date());
        sysPosition.setCreateUser(0L);
        sysPosition.setStatusCd("1000");
        sysPosition.setStatusDate(new Date());
        insert(sysPosition);
    }

    /**
     * 更新
     */
    @Override
    public void update(SysPosition sysPosition){
        sysPosition.setUpdateDate(new Date());
        sysPosition.setUpdateUser(0L);
        sysPosition.setStatusDate(new Date());
        updateById(sysPosition);
    }

    @Override
    public List<TreeNodeVo> getPositionTree(String positionId){
        return baseMapper.getPositionTree(positionId);
    }

    @Override
    public List<SysPositionVo> getPositionRel(String positionId, String isSearchlower){
        return baseMapper.getPositionRel(positionId,isSearchlower);
    }

    @Override
    public String getRolesByPositionId(String positionId){
        return baseMapper.getRolesByPositionId(positionId);
    }
}
