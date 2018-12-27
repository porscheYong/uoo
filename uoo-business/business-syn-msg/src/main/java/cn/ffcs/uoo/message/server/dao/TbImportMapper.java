package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.TbImport;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TbImportMapper extends BaseMapper<TbImport>{
    //根据批次号获取所有的数据
    @Select("select * from Tb_Import where batch_code = #{batchCode} and status_cd = '0'")
    List<TbImport> getTbImportListByBatchCode(@Param("batchCode") String batchCode);
}