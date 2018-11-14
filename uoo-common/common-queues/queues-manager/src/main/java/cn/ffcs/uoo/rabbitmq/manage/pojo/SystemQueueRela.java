package cn.ffcs.uoo.rabbitmq.manage.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("SYSTEM_QUEUE_RELA")
public class SystemQueueRela extends Model<SystemQueueRela> {

    @TableId("QUEUE_NAME")
    private String queueName;

    @TableField("SYSTEM_NAME")
    private String systemName;

    @TableField("DOUBLE_NAME")
    private String doubleName;

    @TableField("CHARGE_PERSON")
    private String chargePerson;

    @TableField("CHARGE_CONTACT")
    private String chargeContact;

    @TableField("STATUS")
    private String status;

    @TableField("IP")
    private String ip;

    @TableField("PORT")
    private String port;

    @TableField("USERNAME")
    private String username;

    @TableField("PASSWORD")
    private String password;

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDoubleName() {
        return doubleName;
    }

    public void setDoubleName(String doubleName) {
        this.doubleName = doubleName;
    }

    public String getChargePerson() {
        return chargePerson;
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson;
    }

    public String getChargeContact() {
        return chargeContact;
    }

    public void setChargeContact(String chargeContact) {
        this.chargeContact = chargeContact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected Serializable pkVal() {
        return this.queueName;
    }
}