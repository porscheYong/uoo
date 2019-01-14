package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.AcctCrossRel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
public interface AcctCrossRelMapper extends BaseMapper<AcctCrossRel> {

    @Update("update tb_acct_cross_rel set status_cd = '1100' " +
            "where acct_id = #{acctId} and rela_type = #{relaType} and status_cd ='1000'")
    boolean deleteByAcctIdAndRelaType(@Param("acctId") Long acctId, @Param("relaType") String relaType);

   @Select("select personnel_id from tb_acct where acct_id = (select acct_id from tb_acct_cross_rel x where x.rela_type = #{type} and cross_tran = #{code} and status_cd = '1000')")
    Long checkExistCrossRelTypeAndSalesCode(@Param("type")String type, @Param("code")String code);

}
