package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgRelType;
import cn.ffcs.uoo.core.organization.dao.OrgRelTypeMapper;
import cn.ffcs.uoo.core.organization.service.OrgRelTypeService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Service
public class OrgRelTypeServiceImpl extends ServiceImpl<OrgRelTypeMapper, OrgRelType> implements OrgRelTypeService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public List<OrgRelType> getOrgRelType(String orgTreeId){
        return baseMapper.getOrgRelType(orgTreeId);
    }

    @Override
    public List<TreeNodeVo> selectOrgRelTypeTree(String refCode){
        if(StrUtil.isNullOrEmpty(refCode)){
            return baseMapper.selectOrgRelTypeTree();
        }
        return baseMapper.selectTarOrgRelTypeTree(refCode);
    }

}
