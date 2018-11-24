package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbSlaveAcct;
import cn.ffcs.uoo.message.server.vo.TbAcctVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbSlaveAcctMapper extends BaseMapper<TbSlaveAcct> {
    TbAcctVo insertOrUpdateSalveAcct(@Param("slaveAcctId") Long slaveAcctId);

    List<TbSlaveAcct> insertOrUpdateSalveAcctByPersonnelIdAndSystemId(@Param("personnelId") Long personnelId, @Param("systemId") Long systemId);

    TbAcctVo insertOrUpdateAcct(@Param("personnelId") Long personnelId, @Param("orgTreeId") Long orgTreeId);
}