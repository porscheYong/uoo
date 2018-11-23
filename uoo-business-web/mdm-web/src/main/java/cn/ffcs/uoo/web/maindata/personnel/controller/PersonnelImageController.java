package cn.ffcs.uoo.web.maindata.personnel.controller;

import cn.ffcs.uoo.web.maindata.personnel.service.PersonnelImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @ClassName PersonnelImageController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@RestController
@RequestMapping(value = "/psnImage", produces = {"application/json;charset=UTF-8"})
@Api(value = "/psnImage", description = "图片上传相关操作")
public class PersonnelImageController {

    @Resource
    private PersonnelImageService personnelImageService;

    @ApiOperation(value="图片上传",notes="图片上传")
    @RequestMapping(value="/uploadImg", method = RequestMethod.PUT)
    public Object uploadImg(@RequestParam("editormd-image-file") MultipartFile multipartFile, @RequestParam("psnImageId") Long psnImageId ){
        return personnelImageService.uploadImg(multipartFile, psnImageId);
    }
}
