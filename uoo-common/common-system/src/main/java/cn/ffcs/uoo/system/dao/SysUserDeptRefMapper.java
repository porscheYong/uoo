package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysUserDeptRef;
import cn.ffcs.uoo.system.vo.SysUserDeptVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<SysUserDeptVo> getUserDeptByUserCode(Pagination page, @Param("userCode") String userCode);

}
