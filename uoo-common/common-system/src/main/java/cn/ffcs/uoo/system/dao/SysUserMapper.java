package cn.ffcs.uoo.system.dao;


import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.entity.SysUserPositionRef;
import cn.ffcs.uoo.system.vo.SysUserDeptPositionVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
    Long getId();

    List<SysUserDeptPositionVo> getUserDeptPosition(Pagination page, @Param("userCode") String userCode);

}