package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbPersonnel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbPersonnelMapper  extends BaseMapper<TbPersonnel> {

    List<TbPersonnel> selectPersonnelAndAcctById(@Param("personnelId") Long value);
}