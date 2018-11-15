package cn.ffcs.uoo.rabbitmq.manage.service;

import cn.ffcs.uoo.rabbitmq.manage.pojo.SystemQueueRela;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liuxiaodong on 2018/11/14.
 */
public interface SystemQueueRelaService extends IService<SystemQueueRela> {

    int checkSystemQueueRela(@Param("systemName") String systemName, @Param("doubleName") String doubleName, @Param("queueName") String queueName, @Param("status") String status);
}
