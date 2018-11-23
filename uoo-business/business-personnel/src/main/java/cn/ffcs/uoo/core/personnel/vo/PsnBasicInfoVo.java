package cn.ffcs.uoo.core.personnel.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;

import java.util.Date;

@Data
public class PsnBasicInfoVo extends BaseVo {

    private Long personnelId;

    private String psnName;

    private String psnNbr;

    private String gender;

    private String content;

    private Date createDate;

    private String keyWord;
}
