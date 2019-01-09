package cn.ffcs.uoo.web.maindata.common.system.vo;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserDeptRefVo {
    /**
     * 用户标识
     */
    private Long userId;
    /**
     * 用户登录账号
     */
    private String accout;
    /**
     * 用户密码
     */
    private String passwd;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 人员编码
     */
    private String userCode;
    /**
     * 出生年月
     */
    private Date birthday;
    /**
     * 用户持有的证件类别
     */
    private String certType;
    /**
     * 用户持有的证件号码
     */
    private String certId;
    /**
     * 联系号码
     */
    private String mobile;
    /**
     * 邮件地址
     */
    private String email;
    /**
     * 头像图片路径
     */
    private String imageUrl;

    /**
     * 账号等级
     */
    private String acctLevel;
    /**
     * 账号是否被锁定
     */
    private Integer locked;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 归属组织信息
     */
    private Page<SysUserDeptPositionVo> sysUserDeptPositionVos;
}
