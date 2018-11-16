package cn.ffcs.uoo.web.maindata.personnel.service.fallback;

import cn.ffcs.uoo.web.maindata.personnel.dto.TbFamily;
import cn.ffcs.uoo.web.maindata.personnel.service.FamilyService;
import org.springframework.stereotype.Component;

@Component
public class FamilyServiceHystrix implements FamilyService {
    @Override
    public Object saveTbFamily(TbFamily tbFamily) {
        return null;
    }

    @Override
    public Object updateTbFamily(TbFamily tbFamily) {
        return null;
    }

    @Override
    public Object delTbFamily(Long familyId) {
        return null;
    }

    @Override
    public Object getTbFamily(Long familyId) {
        return null;
    }

    @Override
    public Object getTbFamilyPage(Long personnelId, Integer pageNo, Integer pageSize) {
        return null;
    }
}
