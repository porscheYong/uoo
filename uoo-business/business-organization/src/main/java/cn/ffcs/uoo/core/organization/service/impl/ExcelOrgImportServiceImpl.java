package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.ExcelOrgImport;
import cn.ffcs.uoo.core.organization.dao.ExcelOrgImportMapper;
import cn.ffcs.uoo.core.organization.service.ExcelOrgImportService;
import cn.ffcs.uoo.core.organization.service.OrgService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import cn.ffcs.uoo.core.organization.vo.ExcelOrgImportVo;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2019-01-25
 */
@Service
public class ExcelOrgImportServiceImpl extends ServiceImpl<ExcelOrgImportMapper, ExcelOrgImport> implements ExcelOrgImportService {

    @Autowired
    private OrgService orgService;

    @Override
    public Long getId(){
        return baseMapper.getId();
    }


    @Override
    public void delete(ExcelOrgImport excelOrgImport){
        excelOrgImport.setStatusCd("1100");
        excelOrgImport.setStatusDate(new Date());
        excelOrgImport.setUpdateDate(new Date());
        excelOrgImport.setUpdateUser(StrUtil.isNullOrEmpty(excelOrgImport.getUpdateUser())?0L:excelOrgImport.getUpdateUser());
        updateById(excelOrgImport);
    }

    @Override
    public void add(ExcelOrgImport excelOrgImport){
        excelOrgImport.setCreateDate(new Date());
        excelOrgImport.setCreateUser(StrUtil.isNullOrEmpty(excelOrgImport.getCreateUser())?0L:excelOrgImport.getCreateUser());
        excelOrgImport.setStatusCd("1000");
        excelOrgImport.setStatusDate(new Date());
        insert(excelOrgImport);
    }


    @Override
    public void update(ExcelOrgImport excelOrgImport){
        excelOrgImport.setUpdateDate(new Date());
        excelOrgImport.setUpdateUser(StrUtil.isNullOrEmpty(excelOrgImport.getUpdateUser())?0L:excelOrgImport.getUpdateUser());
        excelOrgImport.setStatusDate(new Date());
        updateById(excelOrgImport);
    }

    @Override
    public String addExcelOrg(List<ExcelOrgImportVo> excelOrgImportVos,String orgTreeId){
        String ret = "";

        if(excelOrgImportVos!=null && excelOrgImportVos.size()>0){
            for(ExcelOrgImportVo exp : excelOrgImportVos){
                Long seq = getId();
                ExcelOrgImport orgImport = new ExcelOrgImport();
                BeanUtils.copyProperties(exp,orgImport);
                orgImport.setExcelOrgImportId(seq);
                String error = JudgeOrgParams(exp,orgTreeId);
                if(StrUtil.isNullOrEmpty(error)){
                    orgImport.setContent(error);
                }
                add(orgImport);
            }
        }
        return ret;
    }

    public String JudgeOrgParams(ExcelOrgImportVo excelOrgImportVo,String orgTreeId){
        if(StrUtil.isNullOrEmpty(excelOrgImportVo.getOrgId())){
            return "组织标识不能为空";
        }
        if(StrUtil.isNullOrEmpty(excelOrgImportVo.getParentOrgId())){
            return "父节点标示不能为空";
        }
        if(StrUtil.isNullOrEmpty(excelOrgImportVo.getOrgName())){
            return "组织名称不能为空";
        }
        OrgVo org = orgService.selectOrgByOrgId(excelOrgImportVo.getOrgId().toString(),orgTreeId);
        if(StrUtil.isNullOrEmpty(org)){
            return "组织树上不存在组织["+excelOrgImportVo.getOrgId()+"]";
        }
        if(!excelOrgImportVo.getOrgName().equals(org.getOrgName())){
            return "组织名称错误";
        }
        OrgVo supOrg = orgService.selectOrgByOrgId(excelOrgImportVo.getParentOrgId().toString(),orgTreeId);
        if(StrUtil.isNullOrEmpty(supOrg)){
            return "组织树上不存在父组织["+excelOrgImportVo.getParentOrgId()+"]";
        }
        return "";
    }
}
