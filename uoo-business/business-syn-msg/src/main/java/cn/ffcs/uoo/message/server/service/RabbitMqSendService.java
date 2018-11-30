package cn.ffcs.uoo.message.server.service;

public interface RabbitMqSendService {
    void sendMsg(String queueName,String msg);
}
