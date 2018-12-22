package cn.ffcs.uoo.web.maindata.mdm.logs;

public enum OperateType {
    SELECT(1),ADD(2),UPDATE(3),DELETE(4),OTHER(5);
    public long type;
    private OperateType(int type){
        this.type=type;
    }
}
