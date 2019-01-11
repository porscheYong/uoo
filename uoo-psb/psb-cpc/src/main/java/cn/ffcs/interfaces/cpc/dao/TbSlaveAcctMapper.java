package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.TbSlaveAcct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TbSlaveAcctMapper extends BaseMapper<TbSlaveAcct> {
    @Select("select count(*) from tb_slave_acct where slave_acct = #{slave_acct} and acct_id = #{acct_id}")
    int selectBySlaveAcctAndAcctId(@Param("slave_acct") String account,@Param("acct_id") Long acctId);

    @Update("update tb_slave_acct set status_cd = '1100' where acct_id =#{acctId} and status_cd = '1000'")
    boolean deleteByAcctId(@Param("acctId") Long acctId);
}