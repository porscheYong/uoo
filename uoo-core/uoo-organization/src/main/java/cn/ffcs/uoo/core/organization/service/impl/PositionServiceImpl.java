package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.Position;
import cn.ffcs.uoo.core.organization.dao.PositionMapper;
import cn.ffcs.uoo.core.organization.service.PositionService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 岗位，不同组织树具有不同的岗位 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-10
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {


    /**
     * 获取seq
     * @return
     */
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    /**
     * 失效
     * @param position
     */
    @Override
    public void delete(Position position){
        position.setStatusCd("1100");
        position.setStatusDate(new Date());
        position.setUpdateDate(new Date());
        position.setUpdateUser(0L);
        updateById(position);
    }

    @Override
    public void add(Position position){
        position.setCreateDate(new Date());
        position.setCreateUser(0L);
        position.setStatusCd("1000");
        position.setStatusDate(new Date());
        insert(position);
    }


    @Override
    public void update(Position position){
        position.setUpdateDate(new Date());
        position.setUpdateUser(0L);
        position.setStatusDate(new Date());
        updateById(position);
    }

    /**
     * 获取组织岗位
     * @param orgId
     * @return
     */
    @Override
    public List<Position> getOrgPositionByOrgId(Long orgId){
        return baseMapper.getOrgPositionByOrgId(orgId);
    }

    /**
     * 获取岗位树
     * @param pid
     * @param positionType
     * @param positionCode
     * @param isRoot
     * @return
     */
    @Override
    public List<TreeNodeVo> selectPositionTree(String pid,String positionType,String positionCode, boolean isRoot){

        List<TreeNodeVo> volist = new ArrayList<>();
        if(StrUtil.isNullOrEmpty(pid)){ pid="0"; }
        volist = baseMapper.selectPositionTree(pid,positionType,positionCode);
        for(TreeNodeVo vo : volist){
            List<TreeNodeVo> leafList = isleaf(vo.getId(),positionType,positionCode);
            if(leafList!=null && leafList.size()>0){
                vo.setParent(true);
            }else{
                vo.setParent(false);
            }
        }
        return volist;
    }
    @Override
    public List<TreeNodeVo> isleaf(String pid,String positionType,String positionCode){
        List<TreeNodeVo> volist = new ArrayList<>();
        volist = baseMapper.selectPositionTree(pid,positionType,positionCode);
        return volist;
    }
}
