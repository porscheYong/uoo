package cn.ffcs.common.gol.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wongs.common.persistence.jpa.entity.BaseOracleEntity;

import javax.persistence.*;
import java.util.List;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName ControllerAccessLog
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/23 10:45
 * @Version 1.0.0
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="LOG_ACCESS")
public class ControllerAccessLog extends BaseOracleEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acc")
    @SequenceGenerator(name="seq_acc",sequenceName="seq_log_access_id",allocationSize=1)
    @Column(name = "LOG_CA_ID",length = 12)
    private Long id;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long aLong) {
        this.id=aLong;
    }

    @Column(name = "urls",length = 400)
    private String url;

    @Column(name = "method",length = 100)
    private String method;

    @Column(name = "ip",length = 100)
    private String ipAddress;

    @Column(name = "clazz",length = 100)
    private String clazz;

    @Lob
    private String args;

    @Lob
    private String response;

    @Column(name = "cost_Millis",length = 12)
    private long costMillis;

    @Column(name = "user_id",length = 12)
    private Long userId;

    @Column(name = "operate",length = 100)
    private String operate;

}
