package cn.ffcs.uoo.message.server.vo;

public class OrgCrossRelationVo {

    private String crossTran;
    private String crossTranName;
    private String relaType;
    private String relaTypeName;

    public String getCrossTran() {
        return crossTran;
    }

    public void setCrossTran(String crossTran) {
        this.crossTran = crossTran;
    }

    public String getCrossTranName() {
        return crossTranName;
    }

    public void setCrossTranName(String crossTranName) {
        this.crossTranName = crossTranName;
    }

    public String getRelaType() {
        return relaType;
    }

    public void setRelaType(String relaType) {
        this.relaType = relaType;
    }

    public String getRelaTypeName() {
        return relaTypeName;
    }

    public void setRelaTypeName(String relaTypeName) {
        this.relaTypeName = relaTypeName;
    }
}
