package cn.ffcs.uoo.web.maindata.personnel.service;

import cn.ffcs.uoo.web.maindata.personnel.service.fallback.PersonnelImageHystrix;
import common.config.PersonnelImageServiceConfiguration;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName PersonnelImageService
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@FeignClient(value = "business-personnel", configuration = {PersonnelServiceConfiguration.class}, fallback = PersonnelImageHystrix.class)
public interface PersonnelImageService {

    @RequestMapping(value="/tbPersonnelImage/uploadImg", method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object uploadImg(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestParam("psnImageId") Long psnImageId );

    @RequestMapping(value = "/tbPersonnelImage/getTbPsnImageByPsnId",method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getTbPsnImageByPsnId(@RequestParam("personnelId") Long personnelId);
}
