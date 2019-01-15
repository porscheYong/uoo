package cn.ffcs.uoo.core.resource.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@TableName("TB_FILE")
public class File extends Model<File> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件标识，主键
     */
    @TableId("FILE_ID")
    private Integer fileId;

    /**
     * 文件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 文件类型
     */
    @TableField("FILE_TYPE")
    private String fileType;

    /**
     * 文件大小
     */
    @TableField("FILE_SIZE")
    private Double fileSize;

    /**
     * 文件描述
     */
    @TableField("COMP_DESC")
    private String compDesc;

    /**
     * 文件路径
     */
    @TableField("FILE_ADDR")
    private String fileAddr;

    /**
     * 状态
     */
    @TableField("STATUS_CD")
    private String statusCd;

    /**
     * 修改时间
     */
    @TableField("STATUS_DATE")
    private Date statusDate;

    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;

    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    private Long createUser;

    /**
     * 修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;

    /**
     * 修改人
     */
    @TableField("UPDATE_USER")
    private Long updateUser;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }
    public String getCompDesc() {
        return compDesc;
    }

    public void setCompDesc(String compDesc) {
        this.compDesc = compDesc;
    }
    public String getFileAddr() {
        return fileAddr;
    }

    public void setFileAddr(String fileAddr) {
        this.fileAddr = fileAddr;
    }
    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    protected Serializable pkVal() {
        return this.fileId;
    }

    @Override
    public String toString() {
        return "File{" +
        "fileId=" + fileId +
        ", fileName=" + fileName +
        ", fileType=" + fileType +
        ", fileSize=" + fileSize +
        ", compDesc=" + compDesc +
        ", fileAddr=" + fileAddr +
        ", statusCd=" + statusCd +
        ", statusDate=" + statusDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
