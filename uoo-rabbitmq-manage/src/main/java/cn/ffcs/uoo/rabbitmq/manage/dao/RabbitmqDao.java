package cn.ffcs.uoo.rabbitmq.manage.dao;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.IOException;

@Repository
public class RabbitmqDao {


    @Resource(name="rabbitConnectionFactory")
    private ConnectionFactory connectionFactory;

    /**
     * 创建队列
     * name：名字
     * @return 返回队列创建是否成功创建
     * @throws IOException 
     * @throws AmqpException 
     */
   public void createRabbitmqQueue(String name) throws AmqpException, IOException{
	   connectionFactory.createConnection().createChannel(false).queueDeclare(name,true, false, false, null);
    }

    /**
     * 删除队列
     * name：名字
     * @return 返回队列删除是否成功创建
     * @throws IOException 
     * @throws AmqpException 
     */
    public void deleteRabbitmqQueue(String name) throws AmqpException, IOException{
    	connectionFactory.createConnection().createChannel(false).queueDelete(name);
    }

}
