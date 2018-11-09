package cn.ffcs.uoo.core.permission.vo;

import java.util.Date;
import java.util.List;
/**
 * 给职位授权呢
 * @author zxs
 *
 */
public class BatchAddPositionPrivGrantVO {
    private long posId;//职位id
    private List<Long> privIds;//选中的功能权限 数据权限
    private long operateUser;//操作人
    /**
     * 生效时间
     */
    private Date effDate;
    /**
     * 失效时间
     */
    private Date expDate;
    public Date getEffDate() {
        return effDate;
    }
    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }
    public Date getExpDate() {
        return expDate;
    }
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
    public long getOperateUser() {
        return operateUser;
    }
    public void setOperateUser(long operateUser) {
        this.operateUser = operateUser;
    }
    public long getPosId() {
        return posId;
    }
    public void setPosId(long posId) {
        this.posId = posId;
    }
    public List<Long> getPrivIds() {
        return privIds;
    }
    public void setPrivIds(List<Long> privIds) {
        this.privIds = privIds;
    }
    
}
