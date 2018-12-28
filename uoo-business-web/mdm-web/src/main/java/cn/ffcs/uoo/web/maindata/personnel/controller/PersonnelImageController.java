package cn.ffcs.uoo.web.maindata.personnel.controller;

import cn.ffcs.uoo.web.maindata.personnel.service.PersonnelImageService;
import cn.ffcs.uoo.web.maindata.personnel.utils.SysUserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping(value="/uploadImg", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object uploadImg(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestParam(value="psnImageId",defaultValue = "0")Long psnImageId ){
        return personnelImageService.uploadImg(multipartFile, psnImageId, SysUserInfo.getUserId());
    }

    @ApiOperation(value = "图片查看", notes = "图片查看")
    @ApiImplicitParam(name = "personnelId", value = "人员标识", dataType = "Long",paramType="path")
    @RequestMapping(value = "/getPsnImage",method = RequestMethod.GET)
    public Object getTbPsnImageByPsnId(Long personnelId){
        return personnelImageService.getTbPsnImageByPsnId(personnelId);
    }
}
