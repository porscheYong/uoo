package cn.ffcs.uoo.core.personnel.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TbContactVo extends BaseVo {

    /**
     * 联系方式标识
     */
    private Long contactId;
    /**
     * 人员标识
     */
    private Long personnelId;
    /**
     * 联系类型 邮箱、手机等
     */
    private String contactType;
    /**
     * 联系内容
     */
    private String content;
    /**
     * UUID
     */
    private String uuid;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private BigDecimal createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private BigDecimal updateUser;
    /**
     * 状态变更的时间
     */
    private Date statusDate;
}
