package cn.ffcs.uoo.rabbitmq.monitor.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

@TableName("RABBITMQ_INDEX")
public class RabbitmqIndex extends Model<RabbitmqIndex> {
    @TableId("ID")
    private BigDecimal id;

    @TableField("STATE")
    private String state;

    @TableField("COLLECTION_DATA")
    private Date collectionData;

    @TableField("USE_DATA")
    private Date useData;

    @TableField("RABBITMQ_DATE")
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}