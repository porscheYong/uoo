package cn.ffcs.uoo.message.server.vo;

import cn.ffcs.uoo.message.server.pojo.TbOrgContactRel;
import cn.ffcs.uoo.message.server.pojo.TbPersonnel;

public class TbOrgContactRelVo extends TbOrgContactRel {
    private TbPersonnel personalInfo;

    public TbPersonnel getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(TbPersonnel personalInfo) {
        this.personalInfo = personalInfo;
    }
}
