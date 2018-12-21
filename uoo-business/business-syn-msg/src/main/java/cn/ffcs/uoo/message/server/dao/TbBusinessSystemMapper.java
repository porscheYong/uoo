package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbBusinessSystem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbBusinessSystemMapper extends BaseMapper<TbBusinessSystem> {

    //根据从账号获取系统
    TbBusinessSystem getSystemBySlaveAcct(@Param("slaveAcctId") Long slaveAcctId);

    //根据人员获取系统
    List<TbBusinessSystem> getSystemByPersonal(@Param("personalId") Long personalId);

    //根据人员获取系统
    List<TbBusinessSystem> getSystemByPersonalLimitDelete(@Param("personalId") Long personalId);

    //根据组织获取系统
    List<TbBusinessSystem> getSystemByOrg(@Param("orgId") Long orgId);
}