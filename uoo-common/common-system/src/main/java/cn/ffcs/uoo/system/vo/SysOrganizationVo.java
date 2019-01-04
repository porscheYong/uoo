package cn.ffcs.uoo.system.vo;

import cn.ffcs.uoo.base.common.vo.BaseVo;
import cn.ffcs.uoo.system.entity.SysDeptPositionRef;
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
 * 
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
public class SysOrganizationVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    /**
     * 组织标识
     */

    private Long orgId;
    /**
     * 组织名称
     */

    private String orgName;
    /**
     * 组织编码
     */
    private String orgCode;
    /**
     * 上级组织标识
     */
    private Long parentOrgCode;
    /**
     * 引用电信管理区域
     */
    private String regionNbr;

    private String regionName;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * 排序
     */
    private Double sort;
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


    private String search;

    private String positions;

    private int userNum;

    private Long userId;

    private String accout;

    private List<SysPositionVo> sysPositionVos;

    public List<SysPositionVo> getSysPositionVos() {
        return sysPositionVos;
    }

    public void setSysPositionVos(List<SysPositionVo> sysPositionVos) {
        this.sysPositionVos = sysPositionVos;
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

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Long getParentOrgCode() {
        return parentOrgCode;
    }

    public void setParentOrgCode(Long parentOrgCode) {
        this.parentOrgCode = parentOrgCode;
    }

    public String getRegionNbr() {
        return regionNbr;
    }

    public void setRegionNbr(String regionNbr) {
        this.regionNbr = regionNbr;
    }

    public Double getSort() {
        return sort;
    }

    public void setSort(Double sort) {
        this.sort = sort;
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
