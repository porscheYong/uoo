package cn.ffcs.common.gol.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import xyz.wongs.common.persistence.jpa.entity.BaseOracle4Date;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SYS_USER")
public class SysUser extends BaseOracle4Date<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(name="seq_user",sequenceName="SEQ_SYS_USER_ID",allocationSize=1)
    @Column(name = "USER_ID",length = 12)
    private Long id;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long aLong) {
        this.id=aLong;
    }

    @Basic
    @Column(name = "ACCOUT")
    private String accout;

    @Basic
    @Column(name = "PASSWD")
    private String passwd;

    @Basic
    @Column(name = "SALT")
    private String salt;

    @Basic
    @Column(name = "USER_NAME")
    private String userName;

    @Basic
    @Column(name = "GENDER")
    private String gender;

    @Basic
    @Column(name = "USER_CODE")
    private String userCode;

    @Column(name="BIRTHDAY", columnDefinition="DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date birthday;

    @Basic
    @Column(name = "CERT_TYPE")
    private String certType;

    @Basic
    @Column(name = "CERT_ID")
    private String certId;

    @Basic
    @Column(name = "MOBILE")
    private String mobile;

    @Basic
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Basic
    @Column(name = "LAST_IP")
    private String lastIp;

    @Basic
    @Column(name = "ACCT_LEVEL")
    private String acctLevel;

    @Basic
    @Column(name = "LOCKED")
    private Boolean locked;

    @Column(name="LAST_LOGIN", columnDefinition="DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastLogin;

}
