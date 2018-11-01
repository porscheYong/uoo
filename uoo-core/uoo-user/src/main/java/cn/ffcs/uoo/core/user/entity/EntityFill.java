package cn.ffcs.uoo.core.user.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class EntityFill {

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
    private Long createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    private Date statusDate;
}
