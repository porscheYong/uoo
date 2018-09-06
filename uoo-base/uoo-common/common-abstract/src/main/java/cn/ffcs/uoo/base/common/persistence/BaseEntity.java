package cn.ffcs.uoo.base.common.persistence;

//import cn.ffcs.uoo.base.common.config.UooProperties;
import cn.ffcs.uoo.base.common.config.Global;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

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
 * @ClassName BaseEntity
 * @Description  基类
 * @author WCNGS@QQ.COM
 * @date 2018/9/5 16:50
 * @Version 1.0.0
*/
public abstract class BaseEntity <T> implements Serializable {

    private static final long serialVersionUID = 1L;

//    /**
//     * 状态
//     */
//    protected String statusCd;
//
//    /**
//     * 创建时间
//     */
//    protected Date createDate;
//
//    /**
//     * 创建人
//     */
//    protected String createUser;
//
//    /**
//     * 修改时间
//     */
//    protected Date updateDate;
//
//    /**
//     * 修改人
//     */
//    protected String updateUser;
//
//    /**
//     * 状态时间
//     */
//    protected Date statusDate;


    /**
     * 获取数据库类型名
     */
    @JsonIgnore
    public String getDbTypeName(){
        return Global.getEnvProperties().getJdbcType();
    }

    /**
     * 获取环境名称
     */
    @JsonIgnore
    public String getEnvName(){
        return Global.getEnvProperties().getEnvType();
    }

//    public String getStatusCd() {
//        return statusCd;
//    }
//
//    public void setStatusCd(String statusCd) {
//        this.statusCd = statusCd;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }
//
//    public String getCreateUser() {
//        return createUser;
//    }
//
//    public void setCreateUser(String createUser) {
//        this.createUser = createUser;
//    }
//
//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//    }
//
//    public String getUpdateUser() {
//        return updateUser;
//    }
//
//    public void setUpdateUser(String updateUser) {
//        this.updateUser = updateUser;
//    }
//
//    public Date getStatusDate() {
//        return statusDate;
//    }
//
//    public void setStatusDate(Date statusDate) {
//        this.statusDate = statusDate;
//    }
}
