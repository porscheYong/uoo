package cn.ffcs.uoo.web.maindata.personnel.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PsnImageVo {

    private MultipartFile multipartFile;

    private Long psnImageId;


}
