package cn.ffcs.uoo.core.user.service;

public interface RabbitMqService {

    /**
     * 向mq发送消息
     * @param type
     * @param handle
     * @param column
     * @param value
     * @return
     */
    public Object sendMqMsg(String type, String handle, String column, Long value);
}
