package cn.ffcs.uoo.core.personnel.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnelImage;
import cn.ffcs.uoo.core.personnel.exception.PersonnelException;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelImageService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import cn.ffcs.uoo.core.personnel.util.StrUtil;
import cn.ffcs.uoo.core.personnel.vo.PsnImageVo;
import cn.ffcs.uoo.core.personnel.vo.TbPersonnelImageVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import cn.ffcs.uoo.base.controller.BaseController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wudj
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/tbPersonnelImage")
public class TbPersonnelImageController extends BaseController {

    @Autowired
    private TbPersonnelImageService tbPersonnelImageService;

    @ApiOperation(value = "图片上传", notes = "图片上传")
    @ApiImplicitParam(name = "image", value = "图片转码", dataType = "String",paramType="path")
    @UooLog(value = "图片上传",key = "addImage")
    @RequestMapping(value = "/addImage",method = RequestMethod.POST)
    public Object addImage(String image){
        TbPersonnelImage tbPersonnelImage = new TbPersonnelImage();
        String aa = "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";
        tbPersonnelImage.setImage(aa.getBytes());
        tbPersonnelImageService.save(tbPersonnelImage);
        return null;
    }

    @ApiOperation(value="图片上传",notes="图片上传")
    @UooLog(value = "图片上传",key = "uploadImg")
    @RequestMapping(value="/uploadImg", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object uploadImg(@RequestPart("multipartFile") MultipartFile multipartFile, @RequestParam("psnImageId") Long psnImageId ) {
    //public Object uploadImg(PsnImageVo psnImageVo){
        //MultipartFile multipartFile = psnImageVo.getMultipartFile();

        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            throw new PersonnelException(EumPersonnelResponseCode.IMG_NOT_EMPTY);
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            throw new PersonnelException(EumPersonnelResponseCode.IMG_FORMAT_ERROR);
        }

        try {
            FileInputStream is = (FileInputStream) multipartFile.getInputStream();
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();
            TbPersonnelImage tbPersonnelImage = new TbPersonnelImage();
            if("0".equals(String.valueOf(psnImageId))){
                tbPersonnelImage.setPsnImageId(tbPersonnelImageService.getId());
            }else{
                tbPersonnelImage.setPsnImageId(psnImageId);
            }
            tbPersonnelImage.setImage(data);
            tbPersonnelImageService.insertOrUpdate(tbPersonnelImage);

            TbPersonnelImageVo tbPersonnelImageVo = new TbPersonnelImageVo();
            tbPersonnelImageVo.setPsnImageId(tbPersonnelImage.getPsnImageId());
            tbPersonnelImageVo.setImage(new String(Base64.encodeBase64(data)));

            return ResultUtils.success(tbPersonnelImage);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @ApiOperation(value = "图片查看", notes = "图片查看")
    @ApiImplicitParam(name = "personnelId", value = "人员标识", dataType = "Long",paramType="path")
    @UooLog(value = "图片查看",key = "getTbPsnImageByPsnId")
    @RequestMapping(value = "/getTbPsnImageByPsnId",method = RequestMethod.GET)
    public Object getTbPsnImageByPsnId(Long personnelId){
        return ResultUtils.success(tbPersonnelImageService.getTbPsnImageByPsnId(personnelId));
    }


}

