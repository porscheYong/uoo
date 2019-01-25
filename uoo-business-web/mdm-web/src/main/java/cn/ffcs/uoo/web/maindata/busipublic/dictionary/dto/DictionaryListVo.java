package cn.ffcs.uoo.web.maindata.busipublic.dictionary.dto;

import java.util.List;

public class DictionaryListVo {
    private List<DictionaryItemVo> 	CITY_VILLAGE	;//	城乡属性
    private List<DictionaryItemVo> 	SCALE	;//	规模
    private List<DictionaryItemVo> 	ORG_POST_LEVEL	;//	组织岗位级别
    private List<DictionaryItemVo> 	COLUM_TYPE	;//	字段类型
    private List<DictionaryItemVo> 	COL_NULLABLE	;//	字段是否可以空
    private List<DictionaryItemVo> 	EDITABLE	;//	可否编辑
    private List<DictionaryItemVo> 	STATUS_CD	;//	状态
    private List<DictionaryItemVo> 	YES_NO	;//	是否
    private List<DictionaryItemVo> 	RULE_OPERATOR	;//	规则操作符
    private List<DictionaryItemVo> 	NATIONALITY	;//	国籍
    private List<DictionaryItemVo> 	GENDER	;//	性别
    private List<DictionaryItemVo> 	NATION	;//	民族
    private List<DictionaryItemVo> 	MARRIAGE	;//	婚姻状况
    private List<DictionaryItemVo> 	PLITICAL_STATUS	;//	政治面貌
    private List<DictionaryItemVo> 	SCHOOL_TYPE	;//	学校类别
    private List<DictionaryItemVo> 	MEM_RELATION	;//	与本人关系
    private List<DictionaryItemVo> 	CERT_TYPE	;//	证件类型
    private List<DictionaryItemVo> 	ACCT_TYPE	;//	账号类型
    private List<DictionaryItemVo> 	USER_HOST_TYPE	;//	用户主体类型
    private List<DictionaryItemVo> 	ORG_TREE_TYPE	;//	组织树类型
    private List<DictionaryItemVo> 	RELA_TYPE	;//	关系类型
    private List<DictionaryItemVo> 	PROPERTY	;//	用工性质
    private List<DictionaryItemVo> 	REF_TYPE	;//	组织人员关系类型
    private List<DictionaryItemVo> 	ROLE_TYPE	;//	角色类型
    private List<DictionaryItemVo> 	GRANT_OBJ_TYPE	;//	授权对象类型
    private List<DictionaryItemVo> 	PRIV_TYPE	;//	权限类型
    private List<DictionaryItemVo> 	PRIV_REF_TYPE	;//	关联功能类型
    private List<DictionaryItemVo> 	COMP_TYPE	;//	模块组件类型
    private List<DictionaryItemVo> 	INTF_TYPE	;//	接口类型
    private List<DictionaryItemVo> 	CONNECT_TYPE	;//	连接方式
    private List<DictionaryItemVo> 	REGION_TYPE	;//	区域类型
    private List<DictionaryItemVo> 	LOC_TYPE	;//	行政区域类型
    private List<DictionaryItemVo> 	POST_TYPE	;//	职位类型
    private List<DictionaryItemVo> 	POSITION_TYPE	;//	岗位类型
    private List<DictionaryItemVo> 	nodeType	;//	组织节点类型
    private List<DictionaryItemVo> 	areaType	;//	区域级别
    private List<DictionaryItemVo> 	countType	;//	统计属性
    private List<DictionaryItemVo> 	contractType	;//	承包类型
    private List<DictionaryItemVo>   ACCT_LEVEL; // 账号等级
    private List<DictionaryItemVo>   REL_TYPE; // 账号组织关系类型
    private List<DictionaryItemVo>   vipRuleFlg; // 跨区规则

    public List<DictionaryItemVo> getNodeType() {
        return nodeType;
    }

    public void setNodeType(List<DictionaryItemVo> nodeType) {
        this.nodeType = nodeType;
    }

    public List<DictionaryItemVo> getAreaType() {
        return areaType;
    }

    public void setAreaType(List<DictionaryItemVo> areaType) {
        this.areaType = areaType;
    }

    public List<DictionaryItemVo> getCountType() {
        return countType;
    }

    public void setCountType(List<DictionaryItemVo> countType) {
        this.countType = countType;
    }

    public List<DictionaryItemVo> getContractType() {
        return contractType;
    }

    public void setContractType(List<DictionaryItemVo> contractType) {
        this.contractType = contractType;
    }

    public List<DictionaryItemVo> getCITY_VILLAGE() {
        return CITY_VILLAGE;
    }

    public void setCITY_VILLAGE(List<DictionaryItemVo> CITY_VILLAGE) {
        this.CITY_VILLAGE = CITY_VILLAGE;
    }

    public List<DictionaryItemVo> getSCALE() {
        return SCALE;
    }

    public void setSCALE(List<DictionaryItemVo> SCALE) {
        this.SCALE = SCALE;
    }

    public List<DictionaryItemVo> getORG_POST_LEVEL() {
        return ORG_POST_LEVEL;
    }

    public void setORG_POST_LEVEL(List<DictionaryItemVo> ORG_POST_LEVEL) {
        this.ORG_POST_LEVEL = ORG_POST_LEVEL;
    }

    public List<DictionaryItemVo> getCOLUM_TYPE() {
        return COLUM_TYPE;
    }

    public void setCOLUM_TYPE(List<DictionaryItemVo> COLUM_TYPE) {
        this.COLUM_TYPE = COLUM_TYPE;
    }

    public List<DictionaryItemVo> getCOL_NULLABLE() {
        return COL_NULLABLE;
    }

    public void setCOL_NULLABLE(List<DictionaryItemVo> COL_NULLABLE) {
        this.COL_NULLABLE = COL_NULLABLE;
    }

    public List<DictionaryItemVo> getEDITABLE() {
        return EDITABLE;
    }

    public void setEDITABLE(List<DictionaryItemVo> EDITABLE) {
        this.EDITABLE = EDITABLE;
    }

    public List<DictionaryItemVo> getSTATUS_CD() {
        return STATUS_CD;
    }

    public void setSTATUS_CD(List<DictionaryItemVo> STATUS_CD) {
        this.STATUS_CD = STATUS_CD;
    }

    public List<DictionaryItemVo> getYES_NO() {
        return YES_NO;
    }

    public void setYES_NO(List<DictionaryItemVo> YES_NO) {
        this.YES_NO = YES_NO;
    }

    public List<DictionaryItemVo> getRULE_OPERATOR() {
        return RULE_OPERATOR;
    }

    public void setRULE_OPERATOR(List<DictionaryItemVo> RULE_OPERATOR) {
        this.RULE_OPERATOR = RULE_OPERATOR;
    }

    public List<DictionaryItemVo> getNATIONALITY() {
        return NATIONALITY;
    }

    public void setNATIONALITY(List<DictionaryItemVo> NATIONALITY) {
        this.NATIONALITY = NATIONALITY;
    }

    public List<DictionaryItemVo> getGENDER() {
        return GENDER;
    }

    public void setGENDER(List<DictionaryItemVo> GENDER) {
        this.GENDER = GENDER;
    }

    public List<DictionaryItemVo> getNATION() {
        return NATION;
    }

    public void setNATION(List<DictionaryItemVo> NATION) {
        this.NATION = NATION;
    }

    public List<DictionaryItemVo> getMARRIAGE() {
        return MARRIAGE;
    }

    public void setMARRIAGE(List<DictionaryItemVo> MARRIAGE) {
        this.MARRIAGE = MARRIAGE;
    }

    public List<DictionaryItemVo> getPLITICAL_STATUS() {
        return PLITICAL_STATUS;
    }

    public void setPLITICAL_STATUS(List<DictionaryItemVo> PLITICAL_STATUS) {
        this.PLITICAL_STATUS = PLITICAL_STATUS;
    }

    public List<DictionaryItemVo> getSCHOOL_TYPE() {
        return SCHOOL_TYPE;
    }

    public void setSCHOOL_TYPE(List<DictionaryItemVo> SCHOOL_TYPE) {
        this.SCHOOL_TYPE = SCHOOL_TYPE;
    }

    public List<DictionaryItemVo> getMEM_RELATION() {
        return MEM_RELATION;
    }

    public void setMEM_RELATION(List<DictionaryItemVo> MEM_RELATION) {
        this.MEM_RELATION = MEM_RELATION;
    }

    public List<DictionaryItemVo> getCERT_TYPE() {
        return CERT_TYPE;
    }

    public void setCERT_TYPE(List<DictionaryItemVo> CERT_TYPE) {
        this.CERT_TYPE = CERT_TYPE;
    }

    public List<DictionaryItemVo> getACCT_TYPE() {
        return ACCT_TYPE;
    }

    public void setACCT_TYPE(List<DictionaryItemVo> ACCT_TYPE) {
        this.ACCT_TYPE = ACCT_TYPE;
    }

    public List<DictionaryItemVo> getUSER_HOST_TYPE() {
        return USER_HOST_TYPE;
    }

    public void setUSER_HOST_TYPE(List<DictionaryItemVo> USER_HOST_TYPE) {
        this.USER_HOST_TYPE = USER_HOST_TYPE;
    }

    public List<DictionaryItemVo> getORG_TREE_TYPE() {
        return ORG_TREE_TYPE;
    }

    public void setORG_TREE_TYPE(List<DictionaryItemVo> ORG_TREE_TYPE) {
        this.ORG_TREE_TYPE = ORG_TREE_TYPE;
    }

    public List<DictionaryItemVo> getRELA_TYPE() {
        return RELA_TYPE;
    }

    public void setRELA_TYPE(List<DictionaryItemVo> RELA_TYPE) {
        this.RELA_TYPE = RELA_TYPE;
    }

    public List<DictionaryItemVo> getPROPERTY() {
        return PROPERTY;
    }

    public void setPROPERTY(List<DictionaryItemVo> PROPERTY) {
        this.PROPERTY = PROPERTY;
    }

    public List<DictionaryItemVo> getREF_TYPE() {
        return REF_TYPE;
    }

    public void setREF_TYPE(List<DictionaryItemVo> REF_TYPE) {
        this.REF_TYPE = REF_TYPE;
    }

    public List<DictionaryItemVo> getROLE_TYPE() {
        return ROLE_TYPE;
    }

    public void setROLE_TYPE(List<DictionaryItemVo> ROLE_TYPE) {
        this.ROLE_TYPE = ROLE_TYPE;
    }

    public List<DictionaryItemVo> getGRANT_OBJ_TYPE() {
        return GRANT_OBJ_TYPE;
    }

    public void setGRANT_OBJ_TYPE(List<DictionaryItemVo> GRANT_OBJ_TYPE) {
        this.GRANT_OBJ_TYPE = GRANT_OBJ_TYPE;
    }

    public List<DictionaryItemVo> getPRIV_TYPE() {
        return PRIV_TYPE;
    }

    public void setPRIV_TYPE(List<DictionaryItemVo> PRIV_TYPE) {
        this.PRIV_TYPE = PRIV_TYPE;
    }

    public List<DictionaryItemVo> getPRIV_REF_TYPE() {
        return PRIV_REF_TYPE;
    }

    public void setPRIV_REF_TYPE(List<DictionaryItemVo> PRIV_REF_TYPE) {
        this.PRIV_REF_TYPE = PRIV_REF_TYPE;
    }

    public List<DictionaryItemVo> getCOMP_TYPE() {
        return COMP_TYPE;
    }

    public void setCOMP_TYPE(List<DictionaryItemVo> COMP_TYPE) {
        this.COMP_TYPE = COMP_TYPE;
    }

    public List<DictionaryItemVo> getINTF_TYPE() {
        return INTF_TYPE;
    }

    public void setINTF_TYPE(List<DictionaryItemVo> INTF_TYPE) {
        this.INTF_TYPE = INTF_TYPE;
    }

    public List<DictionaryItemVo> getCONNECT_TYPE() {
        return CONNECT_TYPE;
    }

    public void setCONNECT_TYPE(List<DictionaryItemVo> CONNECT_TYPE) {
        this.CONNECT_TYPE = CONNECT_TYPE;
    }

    public List<DictionaryItemVo> getREGION_TYPE() {
        return REGION_TYPE;
    }

    public void setREGION_TYPE(List<DictionaryItemVo> REGION_TYPE) {
        this.REGION_TYPE = REGION_TYPE;
    }

    public List<DictionaryItemVo> getLOC_TYPE() {
        return LOC_TYPE;
    }

    public void setLOC_TYPE(List<DictionaryItemVo> LOC_TYPE) {
        this.LOC_TYPE = LOC_TYPE;
    }

    public List<DictionaryItemVo> getPOST_TYPE() {
        return POST_TYPE;
    }

    public void setPOST_TYPE(List<DictionaryItemVo> POST_TYPE) {
        this.POST_TYPE = POST_TYPE;
    }

    public List<DictionaryItemVo> getPOSITION_TYPE() {
        return POSITION_TYPE;
    }

    public void setPOSITION_TYPE(List<DictionaryItemVo> POSITION_TYPE) {
        this.POSITION_TYPE = POSITION_TYPE;
    }

    public List<DictionaryItemVo> getACCT_LEVEL() {
        return ACCT_LEVEL;
    }

    public void setACCT_LEVEL(List<DictionaryItemVo> ACCT_LEVEL) {
        this.ACCT_LEVEL = ACCT_LEVEL;
    }

    public List<DictionaryItemVo> getREL_TYPE() {
        return REL_TYPE;
    }

    public void setREL_TYPE(List<DictionaryItemVo> REL_TYPE) {
        this.REL_TYPE = REL_TYPE;
    }

    public List<DictionaryItemVo> getVipRuleFlg() {
        return vipRuleFlg;
    }

    public void setVipRuleFlg(List<DictionaryItemVo> vipRuleFlg) {
        this.vipRuleFlg = vipRuleFlg;
    }
}
