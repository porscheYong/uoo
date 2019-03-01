package cn.ffcs.uoo.rabbitmq.monitor.service.impl;

import cn.ffcs.uoo.rabbitmq.monitor.config.RabbitMQConfig;
import cn.ffcs.uoo.rabbitmq.monitor.service.RabbitmqDateService;
import cn.ffcs.uoo.rabbitmq.monitor.util.RabbitMqDateUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RabbitmqDateServiceImpl implements RabbitmqDateService {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Map<String, Object> getDate() {
        Map<String, Object> resultMap = new HashMap<>();

        //获取队列的有关指标
        String date = "";
        String ip = rabbitMQConfig.getIp();
        String port = rabbitMQConfig.getPort();
        String username = rabbitMQConfig.getUsername();
        String password = rabbitMQConfig.getPassword();
        String url = "http://"+ip+":"+port+"/api/queues";
        try{
            date = RabbitMqDateUtil.getDate(url,username,password);
        }catch (IOException e){
            logger.error("url:{},exception:{}",url,e.toString());
        }

        logger.info(date);

        List<Map<String,Object>> list = JSON.parseObject(date,List.class);

        List<Map<String,Object>> queueList = new ArrayList<>();
        list.forEach(e->{
            Map<String,Object> temp = new HashMap<>();
            //消费者数量
            temp.put("consumers",e.get("consumers"));
            //内存消耗
            temp.put("memory",e.get("memory"));
            //消息总数
            temp.put("messages",e.get("messages"));
            //预发送数
            temp.put("messages_ready",e.get("messages_ready"));
            //unack消息数
            temp.put("messages_unacknowledged",e.get("messages_unacknowledged"));
            //持久化消息数
            temp.put("messages_persistent",e.get("messages_persistent"));
            //内存消息数
            temp.put("messages_ram",e.get("messages_ram"));
            //状态
            temp.put("state",e.get("state"));
            //队列名
            temp.put("name",e.get("name"));
            //节点名
            temp.put("node",e.get("node"));
            queueList.add(temp);
        });

        //获取节点的有关指标
        url = "http://"+ip+":"+port+"/api/nodes";
        try{
            date = RabbitMqDateUtil.getDate(url,username,password);
        }catch (IOException e){
            logger.error("url:{},exception:{}",url,e.toString());
        }

        logger.info(date);
        list = JSON.parseObject(date,List.class);
        List<Map<String,Object>> nodeList = new ArrayList<>();
        list.forEach(e->{
            Map<String,Object> temp = new HashMap<>();
            //内存消耗
            temp.put("mem_used",e.get("mem_used"));
            //节点名称
            temp.put("name",e.get("name"));
            //是否在运行
            temp.put("running",e.get("running"));
            nodeList.add(temp);
        });

        resultMap.put("nodes",nodeList);
        resultMap.put("queues",queueList);
        return resultMap;
    }

}