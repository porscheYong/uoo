package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.system.entity.SysUserPositionRef;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

import java.util.List;

/**
 * 归属组织职位
 * SysUserDeptPositionVo class
 * @author wudj
 * @date 2019/01/07
 */

@Data
public class SysUserDeptPositionVo {

    private Long deptPositionRefId;
    /**
     * 人员编码
     */
    private String userCode;
    /**
     * 组织编码
     */
    private String orgCode;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 修改人
     */
    private Long updateUser;

    private List<SysUserPositionRef> userPositionRefList;

}
