package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.dao.OrgTreeMapper;
import cn.ffcs.uoo.core.organization.service.OrgTreeService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class OrgTreeServiceImpl extends ServiceImpl<OrgTreeMapper, OrgTree> implements OrgTreeService {


    @Override
    public Long getId(){
        return baseMapper.getId();
    }


    @Override
    public void delete(OrgTree orgTree){
        orgTree.setStatusCd("1100");
        orgTree.setStatusDate(new Date());
        orgTree.setUpdateDate(new Date());
        orgTree.setUpdateUser(0L);
        updateById(orgTree);
    }

    @Override
    public void add(OrgTree orgTree){
        orgTree.setCreateDate(new Date());
        orgTree.setCreateUser(0L);
        orgTree.setStatusCd("1000");
        orgTree.setStatusDate(new Date());
        insert(orgTree);
    }


    @Override
    public void update(OrgTree orgTree){
        orgTree.setUpdateDate(new Date());
        orgTree.setUpdateUser(0L);
        orgTree.setStatusDate(new Date());
        updateById(orgTree);
    }

    @Override
    public String judgeOrgTreeParams(OrgTree orgTree){

        if(orgTree.getOrgRelTypeList() == null || orgTree.getOrgRelTypeList().size()==0){
            return "组织关系类型不能为空";
        }
        if(orgTree.getOrgTypeList() == null || orgTree.getOrgTypeList().size()==0){
            return "组织类别不能为空";
        }
        if(StrUtil.isNullOrEmpty(orgTree.getOrgTreeName())){
            return "组织树名称不能为空";
        }
        if(StrUtil.isNullOrEmpty(orgTree.getOrgTreeType())){
            return "组织树类型不能为空";
        }
        if(StrUtil.isNullOrEmpty(orgTree.getSort())){
            return "组织树排序号不能为空";
        }
        if(orgTree.getUserTypeList() == null || orgTree.getUserTypeList().size()==0){
            return "用工性质不能为空";
        }
        return null;
    }

    @Override
    public String getOrgTreeNameByOrgId(String orgId){
        return baseMapper.getOrgTreeNameByOrgId(orgId);
    }
}
