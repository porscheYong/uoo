package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.TbContact;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface TbContactMapper  extends BaseMapper<TbContact> {
    @Update("update tb_contact set status_cd = '1100' where personnel_id = #{personnelId} and status_cd = '1000'")
    void deleteByPersonnelId(@Param("personnelId") Long personnelId);
}