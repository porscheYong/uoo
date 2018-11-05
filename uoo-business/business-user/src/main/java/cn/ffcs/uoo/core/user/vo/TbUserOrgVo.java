package cn.ffcs.uoo.core.user.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName TbUserOrgVo
 * @Description
 * @author wudj
 * @date 2018/10/16
 * @Version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TbUserOrgVo extends BaseVo {

    /**
     * 组织ID
     */
    private String orgId;

    /**
     * 查询条件
     */
    private String query;
}
