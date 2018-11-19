package cn.ffcs.uoo.core.personnel.vo;

import lombok.Data;

@Data
public class TbPersonnelImageVo {

    /**
     * 人员头像标识
     */
    private Long psnImageId;
    /**
     * 人员标识
     */
    private Long personnelId;
    /**
     * 人员头像
     */
    private String image;
}
