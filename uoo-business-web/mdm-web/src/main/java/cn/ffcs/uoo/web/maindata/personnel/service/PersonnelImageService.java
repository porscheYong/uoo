package cn.ffcs.uoo.web.maindata.personnel.service;

import cn.ffcs.uoo.web.maindata.personnel.service.fallback.EduServiceHystrix;
import cn.ffcs.uoo.web.maindata.personnel.service.fallback.PersonnelImageHystrix;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName PersonnelImageService
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@FeignClient(value = "personnel-service",configuration = {PersonnelServiceConfiguration.class},fallback = PersonnelImageHystrix.class)
public interface PersonnelImageService {

    @RequestMapping(value="/tbPersonnelImage/uploadImg", method = RequestMethod.PUT, headers={"Content-Type=application/json"})
    public Object uploadImg(@RequestParam("editormd-image-file") MultipartFile multipartFile, @RequestParam("psnImageId") Long psnImageId );
}
