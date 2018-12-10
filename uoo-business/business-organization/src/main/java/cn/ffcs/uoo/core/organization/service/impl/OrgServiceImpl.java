package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.dao.OrgMapper;
import cn.ffcs.uoo.core.organization.entity.OrgRelType;
import cn.ffcs.uoo.core.organization.entity.OrgType;
import cn.ffcs.uoo.core.organization.entity.PoliticalLocation;
import cn.ffcs.uoo.core.organization.service.OrgService;
import cn.ffcs.uoo.core.organization.service.OrgOrgtypeRelService;
import cn.ffcs.uoo.core.organization.service.OrgTypeService;
import cn.ffcs.uoo.core.organization.util.OrgConstant;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.AreaCodeVo;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import cn.ffcs.uoo.core.organization.vo.PageVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Service("orgService")
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {


    @Autowired
    private OrgTypeService orgTypeService;



    @Override
    public Long getId(){
        return baseMapper.getId();
    }


    @Override
    public void delete(Org org){
        org.setStatusCd("1100");
        org.setStatusDate(new Date());
        org.setUpdateDate(new Date());
        org.setUpdateUser(0L);
        updateById(org);
    }

    @Override
    public void add(Org org){
        org.setCreateDate(new Date());
        org.setCreateUser(0L);
        org.setStatusCd("1000");
        org.setStatusDate(new Date());
        insert(org);
    }


    @Override
    public void update(Org org){
        org.setUpdateDate(new Date());
        org.setUpdateUser(0L);
        org.setStatusDate(new Date());
        updateById(org);
    }
    /**
     * 查询组织列表
     * @param org
     * @return
     */
    @Override
    public List<Org> getOrgList(Org org){
        return baseMapper.getOrgList(org);
    }

    @Override
    public String getGenerateOrgCode(){
        Long seq = baseMapper.getOrgCodeSeq();
        DecimalFormat decimalFormat = new DecimalFormat("000000000");
        String orgCodeEnd = decimalFormat.format(seq);
        return OrgConstant.ORG_CODE_N + orgCodeEnd;
    }

    /**
     * 查询组织关系分页
     * @param orgVo
     * @return
     */
    @Override
    public Page<OrgVo> selectOrgRelPage(OrgVo orgVo){

        Page<OrgVo> page = new Page<OrgVo>(orgVo.getPageNo()==0?1:orgVo.getPageNo(),
                orgVo.getPageSize()==0?10:orgVo.getPageSize());
        List<OrgVo> orgVolist = baseMapper.selectOrgRelPage(page,orgVo);
//        for(OrgVo o : orgVolist){
//            List<OrgType> orgTypeList = orgTypeService.getOrgTypeByOrgId(o.getOrgId());
//            String orgTypeSplit = "";
//            if(orgTypeList!=null && orgTypeList.size()>0){
//                for(OrgType ot:orgTypeList){
//                    orgTypeSplit +=ot.getOrgTypeName()+",";
//                }
//            }
//            orgTypeSplit = orgTypeSplit.substring(0,orgTypeSplit.length()-1);
//            o.setOrgTypeSplit(orgTypeSplit);
//            //o.setOrgTypeList(orgTypeList);
//        }
        page.setRecords(orgVolist);
        return page;
    }

    /**
     * 组织分页
     * @param orgVo
     * @return
     */
    @Override
    public Page<OrgVo> selectOrgPage(OrgVo orgVo){
        Page<OrgVo> page = new Page<OrgVo>(orgVo.getPageNo()==0?1:orgVo.getPageNo(),
                orgVo.getPageSize()==0?10:orgVo.getPageSize());
        List<OrgVo> orgVolist = baseMapper.selectOrgPage(page,orgVo);
//        for(OrgVo o : orgVolist){
//            List<OrgType> orgTypeList = orgTypeService.getOrgTypeByOrgId(o.getOrgId());
//            String orgTypeSplit = "";
//            if(orgTypeList!=null && orgTypeList.size()>0){
//                for(OrgType ot:orgTypeList){
//                    orgTypeSplit +=ot.getOrgTypeName()+",";
//                }
//            }
//            orgTypeSplit = orgTypeSplit.substring(0,orgTypeSplit.length()-1);
//            o.setOrgTypeSplit(orgTypeSplit);
//            //o.setOrgTypeList(orgTypeList);
//        }
        page.setRecords(orgVolist);
        return page;
    }
    @Override
    public String JudgeOrgParams(OrgVo org){
        if(StrUtil.isNullOrEmpty(org.getStatusCd())){
            return "组织状态不能为空";
        }
        if(StrUtil.isNullOrEmpty(org.getOrgTreeId())){
            return "组织树标识不能为空";
        }
        if(org.getOrgTypeList() == null || org.getOrgTypeList().size() < 0){
            return "组织类别不能为空";
        }
//        if(org.getPositionList() == null || org.getPositionList().size() <0){
//            return "组织岗位不能为空";
//        }
//        if(org.getPostList() == null || org.getPostList().size() <0){
//            return "组织职位不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getOrgRootId())){
//            return "组织树根节点不能为空";
//        }
        if(StrUtil.isNullOrEmpty(org.getOrgName())){
            return "组织名称不能为空";
        }
//        if(StrUtil.isNullOrEmpty(org.getLocId())){
//            return "组织行政区域不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getSupOrgId())){
//            return "组织父节点不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getShortName())){
//            return "组织简称不能为空";
//        }
//        if(StrUtil.isNullOrEmpty(org.getCityTown())){
//            return "城乡属性不能为空";
//        }
        return "";
    }

//    @Override
//    public void insertByObj(OrgVo org){
//        baseMapper.insertByObj(org);
//    }


    @Override
    public String getSysFullName(String orgTreeId,String orgId){
        return baseMapper.getSysFullName(orgTreeId,orgId);
    }

    @Override
    public OrgVo selectOrgByOrgId(String orgId,String orgTreeId){
        return baseMapper.selectOrgByOrgId(orgId,orgTreeId);
    }
    @Override
    public List<PoliticalLocation> getOrgLoc(String orgId){
        return baseMapper.getOrgLoc(orgId);
    }

    @Override
    public List<AreaCodeVo> getOrgAreaCode(String orgId){
        return baseMapper.getOrgAreaCode(orgId);
    }
}
