package cn.ffcs.uoo.web.maindata.organization.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/17
 */
public class PageVo<E> implements Serializable {

    /**
     * 当前页数
     */
    private int pageNow;
    /**
     * 每页显示记录的条数
     */
    private int pageSize;

    /**
     * 总的记录条数
     */
    private int totalCount;

    /**
     * 总的页数
     */
    private int totalPageCount;

    /**
     *  结果集
     */
    private List<E> list;


    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow <= 1 ? 0 : (pageNow-1)*pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if(totalCount%pageSize==0)
            this.totalPageCount = totalCount/pageSize;
        else
            this.totalPageCount = totalCount/pageSize+1;
        this.totalCount = totalCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

}
