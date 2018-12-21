package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbAcct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface TbAcctMapper  extends BaseMapper<TbAcct> {
    TbAcct selectBySlaveAcctId(@Param("slaveAcctId") Long slaveAcctId);

    TbAcct selectByPersonIdLimitDelete(@Param("personId") Long personId);
}