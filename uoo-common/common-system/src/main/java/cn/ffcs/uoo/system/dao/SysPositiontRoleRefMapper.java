package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysPositiontRoleRef;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 对一定职位可以默认具备一些角色 Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-20
 */
public interface SysPositiontRoleRefMapper extends BaseMapper<SysPositiontRoleRef> {
    public Long getId();
    public List<SysPositiontRoleRef> getCurRoleList(@Param("positionCode") String positionCode);
}
