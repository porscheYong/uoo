package cn.ffcs.uoo.web.maindata.region.vo;

public class ZTreeNode {
    private String name;
    private long id;
    private long pId;
    private boolean open;
    private boolean parent;
    public boolean isParent() {
        return parent;
    }
    public void setParent(boolean parent) {
        this.parent = parent;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getpId() {
        return pId;
    }
    public void setpId(long pId) {
        this.pId = pId;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    
}
