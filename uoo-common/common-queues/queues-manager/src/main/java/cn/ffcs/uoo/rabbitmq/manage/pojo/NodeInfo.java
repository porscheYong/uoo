package cn.ffcs.uoo.rabbitmq.manage.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("node_info")
public class NodeInfo extends Model<NodeInfo> {

    @TableId("ID")
    private Long id;

    @TableField("IP")
    private String ip;

    @TableField("PORT")
    private String port;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}