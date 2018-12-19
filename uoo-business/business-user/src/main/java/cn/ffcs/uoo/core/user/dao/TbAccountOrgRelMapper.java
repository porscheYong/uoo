package cn.ffcs.uoo.core.user.dao;

import cn.ffcs.uoo.core.user.entity.TbAccountOrgRel;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wudj
 * @since 2018-11-08
 */
public interface TbAccountOrgRelMapper extends BaseMapper<TbAccountOrgRel> {

    public Long  getId();

    public List<TbSlaveAcct> findSlaveAcct(@Param("orgId") Long orgId, @Param("acctId") Long acctId, @Param("orgTreeId") Long orgTreeId);

}
