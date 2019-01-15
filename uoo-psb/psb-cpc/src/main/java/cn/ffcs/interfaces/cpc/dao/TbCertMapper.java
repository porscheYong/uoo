package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.TbCert;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbCertMapper  extends BaseMapper<TbCert> {

    @Select("select personnel_id from tb_cert where cert_type = #{type} and cert_no = #{number} and status_cd = '1000'")
    Long checkExistCertTypeAndCertNumber(@Param("type") String type ,@Param("number") String number);

    @Update("update tb_cert set status_cd = '1100' where personnel_id = #{personnelId}")
    int deleteByPersonnelId(@Param("personnelId") Long personnelId);
}