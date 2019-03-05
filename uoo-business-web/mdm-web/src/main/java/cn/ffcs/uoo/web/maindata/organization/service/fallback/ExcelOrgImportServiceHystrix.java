package cn.ffcs.uoo.web.maindata.organization.service.fallback;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-12
 */

import cn.ffcs.uoo.web.maindata.organization.dto.ExcelOrgImport;
import cn.ffcs.uoo.web.maindata.organization.dto.PsonOrgVo;
import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.organization.service.ExcelOrgImportService;
import cn.ffcs.uoo.web.maindata.organization.service.OrgContactRelService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/12
 */
@Component
public class ExcelOrgImportServiceHystrix implements ExcelOrgImportService {


    @Override
    public ResponseResult<String> importExcelFileData(MultipartFile fileInfo,
                                                      String orgTreeId,
                                                      Long userId,
                                                      String accout,
                                                      String impType){
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<String> addExcelFileData(String fileSign,Long userId,String accout){
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public ResponseResult<Page<ExcelOrgImport>> getExcelFileData(String fileSign,
                                                                 String dataSign,
                                                                 Integer pageSize,
                                                                 Integer pageNo,
                                                                 Long userId,
                                                                 String accout){
        ResponseResult<Page<ExcelOrgImport>> responseResult = new ResponseResult<Page<ExcelOrgImport>>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
