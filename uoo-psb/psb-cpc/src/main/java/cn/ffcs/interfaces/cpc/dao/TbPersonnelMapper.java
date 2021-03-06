package cn.ffcs.interfaces.cpc.dao;


import cn.ffcs.interfaces.cpc.pojo.TbPersonnel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface TbPersonnelMapper extends BaseMapper<TbPersonnel> {

    /**
     * 插入人员信息
     * @param tbPersonnel
     */
    void insertValueOfPersonnel(TbPersonnel tbPersonnel);

    @Select("select personnel_id from tb_personnel where psn_code = #{psnCode} and status_cd = '1000'")
    Long checkExistPsnCode(@Param("psnCode") String psnCode);

    @Select("SELECT unicode FROM uom.v_uom_grp_user_org_info a WHERE a.RELACD='专职' AND a.DEL_STATUS=1000 and ctidentitynumber = #{certNumber}")
    String checkExistNcCodeByIdCode(@Param("certNumber")String cert_number);

    @Select("SELECT SEQ_PSN_CODE.NEXTVAL from dual")
    Long getPsnCode();
}
