package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysUserDeptRef;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 对部门可选岗位的限定 Mapper 接口
 * </p>
 *
 * @author wudj
 * @since 2019-01-05
 */
public interface SysUserDeptRefMapper extends BaseMapper<SysUserDeptRef> {

    Long getId();

    void delUserDeptDef(@Param("userCode") String userCode, @Param("updateUser") Long updateUser);

}
