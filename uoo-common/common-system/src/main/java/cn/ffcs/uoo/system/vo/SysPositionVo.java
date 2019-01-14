package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 根据不同部门的工作性质、责任轻重、难易程度和所需资格条件等进行分类，在平台上，不对职位进行过细的区分
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
public class SysPositionVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    /**
     * 职位标识
     */
    private Long positionId;
    /**
     * 职位名称
     */
    private String positionName;
    /**
     * 职位编码
     */
    private String positionCode;
    /**
     * 上级职位
     */
    private String parentPositionCode;

    /**
     * 上级职位名称
     */
    private String pPositionName;

    /**
     * 引用电信管理区域
     */
    private String regionNbr;
    /**
     * 职位优先级排序
     */
    private Integer sortNum;
    /**
     * 备注
     */
    private String notes;
    /**
     * 状态
     */
    private String statusCd;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private Long updateUser;
    /**
     * 状态变更的时间
     */
    private Date statusDate;


    private String regionName;


    private String roleNames;

    private String orgPositionNum;

    private String orgUserNum;

    private List<String> roleCodeList;

    private List<SysRoleDTO> sysRoleDTOList;

    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    private Long userId;
    private String accout;

    public String getParentPositionCode() {
        return parentPositionCode;
    }

    public void setParentPositionCode(String parentPositionCode) {
        this.parentPositionCode = parentPositionCode;
    }

    public String getpPositionName() {
        return pPositionName;
    }

    public void setpPositionName(String pPositionName) {
        this.pPositionName = pPositionName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccout() {
        return accout;
    }

    public void setAccout(String accout) {
        this.accout = accout;
    }

    public List<SysRoleDTO> getSysRoleDTOList() {
        return sysRoleDTOList;
    }

    public void setSysRoleDTOList(List<SysRoleDTO> sysRoleDTOList) {
        this.sysRoleDTOList = sysRoleDTOList;
    }

    private String isSearchlower;


    public String getIsSearchlower() {
        return isSearchlower;
    }

    public void setIsSearchlower(String isSearchlower) {
        this.isSearchlower = isSearchlower;
    }

    public List<String> getRoleCodeList() {
        return roleCodeList;
    }

    public void setRoleCodeList(List<String> roleCodeList) {
        this.roleCodeList = roleCodeList;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getOrgPositionNum() {
        return orgPositionNum;
    }

    public void setOrgPositionNum(String orgPositionNum) {
        this.orgPositionNum = orgPositionNum;
    }

    public String getOrgUserNum() {
        return orgUserNum;
    }

    public void setOrgUserNum(String orgUserNum) {
        this.orgUserNum = orgUserNum;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }


    public String getRegionNbr() {
        return regionNbr;
    }

    public void setRegionNbr(String regionNbr) {
        this.regionNbr = regionNbr;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
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

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }


}
