package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OrgOrgtreeRel;
import cn.ffcs.uoo.core.organization.dao.OrgOrgtreeRelMapper;
import cn.ffcs.uoo.core.organization.service.OrgOrgtreeRelService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        orgOrgtreeRef.setUpdateUser(StrUtil.isNullOrEmpty(orgOrgtreeRef.getUpdateUser())?0L:orgOrgtreeRef.getUpdateUser());
        updateById(orgOrgtreeRef);
    }

    /**
     * 新增
     * @param orgOrgtreeRef
     */
    @Override
    public void add(OrgOrgtreeRel orgOrgtreeRef){
        orgOrgtreeRef.setCreateDate(new Date());
        orgOrgtreeRef.setCreateUser(StrUtil.isNullOrEmpty(orgOrgtreeRef.getCreateUser())?0L:orgOrgtreeRef.getCreateUser());
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
        orgOrgtreeRef.setUpdateUser(StrUtil.isNullOrEmpty(orgOrgtreeRef.getUpdateUser())?0L:orgOrgtreeRef.getUpdateUser());
        orgOrgtreeRef.setStatusDate(new Date());
        updateById(orgOrgtreeRef);
    }


    /**
     * 获取组织全路径列表
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public List<OrgOrgtreeRel> getFullBizOrgList(String orgTreeId, String orgId){
        return baseMapper.getFullBizOrgList(orgTreeId,orgId);
    }


    /**
     * 获取组织名称全路径
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public String getFullBizOrgNameList(String orgTreeId,String orgId,String split){
        String fullName = "";
        List<OrgOrgtreeRel> ootrList = baseMapper.getFullBizOrgList(orgTreeId,orgId);
        if(ootrList!=null && ootrList.size()>0){
            for(int i=0;i<ootrList.size();i++){
                fullName += ootrList.get(i).getOrgBizName();
                if(!StrUtil.isNullOrEmpty(split)){
                    fullName += split;
                }
            }
            if(!StrUtil.isNullOrEmpty(fullName) && !StrUtil.isNullOrEmpty(split)){
                fullName = fullName.substring(0,fullName.length()-split.length());
            }
        }
        return fullName;
    }

    /**
     * 获取组织Id全路
     * @param orgTreeId
     * @param orgId
     * @return
     */
    @Override
    public String getFullBizOrgIdList(String orgTreeId,String orgId,String split){
        String fullNameId = "";
        List<OrgOrgtreeRel> ootrList = baseMapper.getFullBizOrgList(orgTreeId,orgId);
        if(ootrList!=null && ootrList.size()>0){
            for(int i=0;i<ootrList.size();i++){
                fullNameId += ootrList.get(i).getOrgId();
                if(!StrUtil.isNullOrEmpty(split)){
                    fullNameId += split;
                }
            }
            if(!StrUtil.isNullOrEmpty(fullNameId) && !StrUtil.isNullOrEmpty(split)){
                fullNameId = fullNameId.substring(0,fullNameId.length()-split.length());
            }
        }
        return fullNameId;
    }

}
