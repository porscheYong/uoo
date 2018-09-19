package cn.ffcs.uoo.rabbitmq.manage.util;
import java.util.UUID;

public class BuildQueueNameUtil {
	
	public static String build() {
		StringBuffer queueName = new StringBuffer("queue_");
		queueName.append(UUID.randomUUID().toString().replaceAll("-","").trim());
		return queueName.toString();
	}
	
}