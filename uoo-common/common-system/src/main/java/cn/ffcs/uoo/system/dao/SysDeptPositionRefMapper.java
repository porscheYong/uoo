package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysDeptPositionRef;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 对部门可选岗位的限定 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
public interface SysDeptPositionRefMapper extends BaseMapper<SysDeptPositionRef> {

    public Long getId();

    public List<SysDeptPositionRef> getDeptPositionRelList(String orgCode);
}
