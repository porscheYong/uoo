package cn.ffcs.uoo.web.maindata.personnel.service.fallback;

import cn.ffcs.uoo.web.maindata.organization.dto.ResponseResult;
import cn.ffcs.uoo.web.maindata.personnel.service.PersonnelImageService;
import cn.ffcs.uoo.web.maindata.personnel.vo.PsnImageVo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PersonnelImageHystrix implements PersonnelImageService {
    @Override
    public Object uploadImg(MultipartFile multipartFile, Long psnImageId, Long userId) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }

    @Override
    public Object getTbPsnImageByPsnId(Long personnelId) {
        ResponseResult<String> responseResult = new ResponseResult<String>();
        responseResult.setState(ResponseResult.STATE_ERROR);
        responseResult.setMessage("系统暂时不可用");
        return responseResult;
    }
}
