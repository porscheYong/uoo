package cn.ffcs.uoo.web.maindata.personnel.service.fallback;

import cn.ffcs.uoo.web.maindata.personnel.dto.TbPsnjob;
import cn.ffcs.uoo.web.maindata.personnel.service.PsnjobService;
import org.springframework.stereotype.Component;
/**
 * @ClassName PersonnelController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */
@Component
public class PsnjobServiceHystrix implements PsnjobService {
    @Override
    public Object saveTbPsnjob(TbPsnjob tbPsnjob) {
        return null;
    }

    @Override
    public Object updateTbPsnjob(TbPsnjob tbPsnjob) {
        return null;
    }

    @Override
    public Object delTbPsnjob(Long psnjobId) {
        return null;
    }

    @Override
    public Object getTbPsnjob(Long psnjobId) {
        return null;
    }

    @Override
    public Object getTbPsnjobPage(Long personnelId, Integer pageNo, Integer pageSize) {
        return null;
    }
}
