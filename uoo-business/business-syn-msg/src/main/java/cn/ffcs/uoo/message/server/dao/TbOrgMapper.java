package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbOrg;
import cn.ffcs.uoo.message.server.vo.TbOrgVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface TbOrgMapper extends BaseMapper<TbOrg> {
    TbOrgVo getOrgVo(@Param("orgId") Long orgId, @Param("orgTreeId") Long orgTreeId, @Param("businessSystemId") Long businessSystemId);
}