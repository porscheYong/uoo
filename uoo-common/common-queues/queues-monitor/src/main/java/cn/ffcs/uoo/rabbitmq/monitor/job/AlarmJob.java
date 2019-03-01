package cn.ffcs.uoo.rabbitmq.monitor.job;

import cn.ffcs.uoo.rabbitmq.monitor.constant.RabbitmqIndexConstant;
import cn.ffcs.uoo.rabbitmq.monitor.dao.AlarmDao;
import cn.ffcs.uoo.rabbitmq.monitor.service.RabbitmqDateService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@EnableScheduling
@Component
public class AlarmJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitmqDateService rabbitmqDateService;

    @Resource
    private AlarmDao alarmDao;

    @Scheduled(cron = "0 0/1 * * * ? ")
    @Transactional(readOnly = false,noRollbackFor = Exception.class)
    public void alarm(){
        //获取数据
        Map<String,Object> map = rabbitmqDateService.getDate();
        logger.info("msg:{}", JSON.toJSONString(map));

        List<Map<String,Object>> nodes = (List<Map<String,Object>>)map.get("nodes");

        List<Map<String,Object>> queues = (List<Map<String,Object>>)map.get("queues");

        if(nodes != null){
            nodes.forEach(m->{
                String name = (String) m.get("name");
                boolean running = (boolean)m.get("running");
                Long memUsed =  0L;
                if(m.get("mem_used") != null){
                    memUsed =  Long.parseLong(String.valueOf(m.get("mem_used")))/1048576L;
                }
                if(!running){
                    //写告警短信[节点关闭]
                    String msg = RabbitmqIndexConstant.TITLE + name+"节点状态为关闭。";
                    logger.info("告警短信为:{}",msg);
                    alarmDao.insert(msg);
                 }else if(memUsed >= RabbitmqIndexConstant.MAX_MEM){
                    //写告警短信[内存占用过高]
                    String msg = RabbitmqIndexConstant.TITLE + name+"节点内存占用过高。";
                    logger.info("告警短信为:{}",msg);
                    alarmDao.insert(msg);
                }
            });
        }else{
            //写告警短信[未检查到节点消息，管理员排查]
            String msg = RabbitmqIndexConstant.TITLE +"未检查到节点消息，管理员排查。";
            logger.info("告警短信为:{}",msg);
            alarmDao.insert(msg);
        }


        if(queues != null){
            queues.forEach(q->{
                Long ready = 0L;
                if(q.get("messages_ready") != null)
                    ready = Long.parseLong(String.valueOf(q.get("messages_ready")));
                Long unacknowledged = 0l;
                if(q.get("messages_unacknowledged") != null)
                    unacknowledged = Long.parseLong(String.valueOf(q.get("messages_unacknowledged")));
                String name = (String) q.get("name");
                String state = (String)q.get("state");
                if(state == null || !"running".equals(state)){
                    //写告警短信[队列被关闭]
                    String msg = RabbitmqIndexConstant.TITLE +name+"队列状态为关闭。";
                    logger.info("告警短信为:{}",msg);
                    alarmDao.insert(msg);
                }
                else if(ready >=RabbitmqIndexConstant.MAX_MSG || unacknowledged>=1L){
                    //写告警短信[消息过多，请有关系统及时消费消息]
                    String msg = RabbitmqIndexConstant.TITLE +name+"队列消息过多或者出现消息消费不及时。";
                    logger.info("告警短信为:{}",msg);
                    alarmDao.insert(msg);
                }
            });
        }else {
            //写告警短信[未检查到队列消息，管理员排查]
            String msg = RabbitmqIndexConstant.TITLE +"未检查到队列消息，管理员排查。";
            logger.info("告警短信为:{}",msg);
            alarmDao.insert(msg);
        }

    }
}