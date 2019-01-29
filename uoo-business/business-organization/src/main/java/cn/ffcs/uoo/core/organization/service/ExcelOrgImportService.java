package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.ExcelOrgImport;
import cn.ffcs.uoo.core.organization.vo.ExcelOrgImportVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2019-01-25
 */
public interface ExcelOrgImportService extends IService<ExcelOrgImport> {

    public Long getId();

    public void delete(ExcelOrgImport excelOrgImport);

    public void add(ExcelOrgImport excelOrgImport);

    public void update(ExcelOrgImport excelOrgImport);

    public String addExcelOrg(List<ExcelOrgImportVo> excelOrgImportVos,String orgTreeId);
}
