package cn.ffcs.uoo.message.server.dao;

import cn.ffcs.uoo.message.server.pojo.SystemQueueRela;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SystemQueueRelaMapper extends BaseMapper<SystemQueueRela> {

    @Select("select queue_name from system_queue_rela where status = #{status} and system_name = #{systemName} and double_name = #{doubleName}")
    List<String> getQueueName(@Param("systemName") String systemName, @Param("doubleName") String doubleName, @Param("status")String status);
}