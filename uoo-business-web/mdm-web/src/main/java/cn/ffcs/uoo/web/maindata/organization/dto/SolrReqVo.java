package cn.ffcs.uoo.web.maindata.organization.dto;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/21
 */
public class SolrReqVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 核心名称
     */
    private String coreName ;

    /**
     * 查询条件
     */
    private String query;

    /**
     * 组织关系类型
     */
    private String orgRelTypeId;

    /**
     * 组织根节点
     */
    private String orgRootId;

    /**
     * 当前页
     */
    private int pageNum = 0;
    /**
     * 每页的条数
     */
    private int pageSize = 0;

    /**
     * 高亮
     */
    private boolean isHighlight;



    public String getOrgRootId() {
        return orgRootId;
    }

    public void setOrgRootId(String orgRootId) {
        this.orgRootId = orgRootId;
    }

    public String getOrgRelTypeId() {
        return orgRelTypeId;
    }

    public void setOrgRelTypeId(String orgRelTypeId) {
        this.orgRelTypeId = orgRelTypeId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHighlight() {
        return isHighlight;
    }

    public void setHighlight(boolean highlight) {
        isHighlight = highlight;
    }

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}

