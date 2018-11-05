package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgType;
import cn.ffcs.uoo.core.organization.dao.OrgTypeMapper;
import cn.ffcs.uoo.core.organization.service.OrgTypeService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class OrgTypeServiceImpl extends ServiceImpl<OrgTypeMapper, OrgType> implements OrgTypeService {

    @Autowired
    private OrgTypeMapper orgTypeMapper;

    @Override
    public List<OrgType> getOrgTypeByOrgId(Long orgId){
        return baseMapper.getOrgTypeByOrgId(orgId);
    }

    @Override
    public List<TreeNodeVo> selectOrgTypeTree(String orgTypeId,String orgTypeCode){
        List<TreeNodeVo> volist = new ArrayList<>();
        if(StrUtil.isNullOrEmpty(orgTypeId)){orgTypeId="0";}
        volist = baseMapper.selectOrgTypeTree(orgTypeId,orgTypeCode);
        for(TreeNodeVo vo : volist){
            isLeaf(vo);
        }
        return volist;
    }

    @Override
    public boolean isLeaf(TreeNodeVo treeNodeVo){
        List<TreeNodeVo> li = baseMapper.isLeaf(Integer.valueOf(treeNodeVo.getId()).longValue());
        if(li==null || li.size()==0){
            treeNodeVo.setParent(false);
            return false;
        }
        treeNodeVo.setParent(true);
        return true;
    }
}
