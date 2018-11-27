package cn.ffcs.uoo.message.server.pojo;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RabbitmqIndex extends Model<RabbitmqIndex> {
    private String id;

    private String state;

    private Date collectionData;

    private Date useData;

    private String queueName;

    private String rabbitmqDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCollectionData() {
        return collectionData;
    }

    public void setCollectionData(Date collectionData) {
        this.collectionData = collectionData;
    }

    public Date getUseData() {
        return useData;
    }

    public void setUseData(Date useData) {
        this.useData = useData;
    }

    public String getRabbitmqDate() {
        return rabbitmqDate;
    }

    public void setRabbitmqDate(String rabbitmqDate) {
        this.rabbitmqDate = rabbitmqDate;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}