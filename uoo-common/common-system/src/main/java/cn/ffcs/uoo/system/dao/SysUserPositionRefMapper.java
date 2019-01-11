package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysUserPositionRef;
import cn.ffcs.uoo.system.vo.SysUserPositionRefVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 平台用户职位关系 Mapper 接口
 * </p>
 *
 * @author wudj
 * @since 2019-01-05
 */
public interface SysUserPositionRefMapper extends BaseMapper<SysUserPositionRef> {

    Long getId();

    List<SysUserPositionRefVo> getUserPositionRef(@Param("userCode") String userCode, @Param("orgCode") String orgCode);

    void delUserPositionDef(@Param("userCode") String userCode, @Param("updateUser") Long updateUser);
}
