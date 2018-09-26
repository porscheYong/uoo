package cn.ffcs.uoo.message.service.dao;

import cn.ffcs.uoo.message.service.pojo.SystemQueueRela;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SystemQueueRelaMapper {
    int deleteByPrimaryKey(String queueName);

    int insert(SystemQueueRela record);

    int insertSelective(SystemQueueRela record);

    SystemQueueRela selectByPrimaryKey(String queueName);

    List<String> selectBySystemName(@Param("systemName") String systemName);

    int updateByPrimaryKeySelective(SystemQueueRela record);

    int updateByPrimaryKey(SystemQueueRela record);

	int checkSystemQueueRela(@Param("systemName") String systemName, @Param("doubleName") String doubleName, @Param("queueName") String queueName, @Param("status") String status);

	@Select("select queue_name , system_name from System_queue_rela where status = #{status}")
    List<Map<String,String>> getAllSystemAndQueueName(@Param("status")String value);
}