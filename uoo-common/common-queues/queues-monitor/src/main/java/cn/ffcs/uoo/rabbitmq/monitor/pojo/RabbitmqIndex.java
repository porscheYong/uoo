package cn.ffcs.uoo.rabbitmq.monitor.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class RabbitmqIndex {
    private BigDecimal id;

    private String state;

    private Date collectionData;

    private Date useData;

    private String rabbitmqDate;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
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
}