package cn.ffcs.uoo.web.maindata.personnel.service.fallback;

import cn.ffcs.uoo.web.maindata.personnel.service.PersonnelImageService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PersonnelImageHystrix implements PersonnelImageService {
    @Override
    public Object uploadImg(MultipartFile multipartFile, Long psnImageId) {
        return null;
    }
}
