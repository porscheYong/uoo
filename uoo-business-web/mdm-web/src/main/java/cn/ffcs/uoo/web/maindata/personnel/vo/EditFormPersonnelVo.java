package cn.ffcs.uoo.web.maindata.personnel.vo;


import cn.ffcs.uoo.web.maindata.personnel.dto.TbContact;
import cn.ffcs.uoo.web.maindata.personnel.dto.TbEdu;
import cn.ffcs.uoo.web.maindata.personnel.dto.TbFamily;
import cn.ffcs.uoo.web.maindata.personnel.dto.TbPsnjob;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EditFormPersonnelVo {

    /**  1、基本信息    */

    /**
     * 主键 人员标识
     * tb_personnel
     */
    private Long personnelId;

    /**
     * 人员姓名
     * tb_personnel
     */
    private String psnName;

    /**
     * 性别
     * tb_personnel
     */
    private String gender;

    /**
     * 人员头像
     * tb_personnel
     */
    private String image;

    /**
     * 员工工号
     * tb_personnel
     */
    private String psnNbr;

    /**
     * 人员编码
     * tb_personnel
     */
    private String psnCode;

    /**
     * 人力编码
     * tb_personnel
     */
    private String ncCode;

    /**
     *  籍贯
     * tb_personnel
     */
    private String nationality;

    /**
     *  民族
     * tb_personnel
     */
    private String nation;

    /**
     *  首次参加工作时间
     * tb_personnel
     */
    private Date toWorkTime;

    /**
     *  政治面貌
     * tb_personnel
     */
    private String pliticalStatus;

    /**
     *  婚姻状况
     * tb_personnel
     */
    private String marriage;

    /**
     * 证件类型
     * TB_CERT
     */
    private String certType;

    /**
     * 证件号码
     * TB_CERT
     */
    private String certNo;

    /**
     * 证件地址
     * TB_CERT
     */
    private String address;

    /**
     * 操作人
     */
    private Long userId;

    /**  2.1、联系信息(手机)    */

    private List<TbContact> tbMobileVoList;

    /**  2.2、联系信息(邮箱)    */
    private List<TbContact> tbEamilVoList;

    /**  3、归属组织信息 */
    private List<PsonOrgVo> psonOrgVoList;

    /**  4、工作履历信息 */

    private List<TbPsnjob> tbPsnjobList;

    /**  5、教育信息 */

    private List<TbEdu> tbEduList;

    /**  6、家庭成员信息 */

    private List<TbFamily> tbFamilyList;
}
