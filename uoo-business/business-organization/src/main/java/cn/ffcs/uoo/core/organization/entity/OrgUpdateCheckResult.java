package cn.ffcs.uoo.core.organization.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * 新增修改删除一个组织  要先检测能不能操作
 * bss引用了人力、营销  
 * 
 * @author zxs
 *
 */
public class OrgUpdateCheckResult {
    private boolean vaild;//通过检测 该组织可以数据增删改
    private boolean sync;//需要同步
    public boolean isSync() {
        return sync;
    }
    public void setSync(boolean sync) {
        this.sync = sync;
    }
    public boolean isVaild() {
        return vaild;
    }
    public void setVaild(boolean vaild) {
        this.vaild = vaild;
    }
    public static enum OrgOperateType{
        ADD,UPDATE,DELETE
    }
}
