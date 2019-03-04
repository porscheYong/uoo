package cn.ffcs.uoo.rabbitmq.monitor.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface AlarmDao {

    @Insert("insert into uom.system_message_log (system_message_log_id, system_code, result,system_message_info,create_date) " +
            "values(uom.seq_system_message_log_id.nextval,'10000',0, #{msg},sysdate)")
    void insert(@Param("msg") String msg);
}
