package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbSlaveAcct;
import cn.ffcs.uoo.message.server.vo.TbAcctVo;
import cn.ffcs.uoo.message.server.vo.TbSlaveAcctVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbSlaveAcctMapper extends BaseMapper<TbSlaveAcct> {
    TbAcctVo insertOrUpdateSalveAcct(@Param("slaveAcctId") Long slaveAcctId);

    List<TbSlaveAcct> insertOrUpdateSalveAcctByPersonnelIdAndSystemId(@Param("personnelId") Long personnelId, @Param("systemId") Long systemId);

    TbAcctVo insertOrUpdateAcct(@Param("personnelId") Long personnelId, @Param("orgTreeId") Long orgTreeId);

    @Select("select count(*) from tb_personnel a ,tb_acct b where a.personnel_id = b.personnel_id " +
            "and a.personnel_id = #{personnelId} and (a.status_cd = '1100' or b.status_cd = '1100')")
    int checkPersonnelAndAcctByUnUse(@Param("personnelId")Long value);

    @Select("select count(*) from tb_personnel a left join tb_acct b on a.personnel_id = b.personnel_id " +
            "where a.personnel_id = #{personnelId} and a.status_cd = '1000' and b.status_cd = '1000'")
    int checkPersonnelAndAcctByUser(@Param("personnelId")Long value);


    TbSlaveAcctVo selectVoById(@Param("slaveAcctId") Long slaveAcctId);
}