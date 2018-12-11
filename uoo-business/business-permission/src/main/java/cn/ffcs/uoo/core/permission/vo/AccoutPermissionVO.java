package cn.ffcs.uoo.core.permission.vo;

import java.util.ArrayList;
import java.util.List;

import cn.ffcs.uoo.core.permission.entity.FuncComp;
import cn.ffcs.uoo.core.permission.entity.FuncMenu;

public class AccoutPermissionVO {
    private Long accoutId;
    private List<FuncComp> funcComps=new ArrayList<>();
    private List<FuncMenu> funcMemus=new ArrayList<>();
    public Long getAccoutId() {
        return accoutId;
    }
    public void setAccoutId(Long accoutId) {
        this.accoutId = accoutId;
    }
    public List<FuncComp> getFuncComps() {
        return funcComps;
    }
    public void setFuncComps(List<FuncComp> funcComps) {
        this.funcComps = funcComps;
    }
    public List<FuncMenu> getFuncMemus() {
        return funcMemus;
    }
    public void setFuncMemus(List<FuncMenu> funcMemus) {
        this.funcMemus = funcMemus;
    }
    
}
