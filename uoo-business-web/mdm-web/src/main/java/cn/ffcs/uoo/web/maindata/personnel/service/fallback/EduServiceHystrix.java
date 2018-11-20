package cn.ffcs.uoo.web.maindata.personnel.service.fallback;

import cn.ffcs.uoo.web.maindata.personnel.dto.TbEdu;
import cn.ffcs.uoo.web.maindata.personnel.service.EduService;
import org.springframework.stereotype.Component;
/**
 * @ClassName PersonnelController
 * @Description
 * @author wudj
 * @date 2018/11/14 14:28
 * @Version 1.0.0
 */

@Component
public class EduServiceHystrix implements EduService {
    @Override
    public Object saveTbEdu(TbEdu tbEdu) {
        return null;
    }

    @Override
    public Object updateTbEdu(TbEdu tbEdu) {
        return null;
    }

    @Override
    public Object delTbEdu(Long eduId) {
        return null;
    }

    @Override
    public Object getTbEdu(Long eduId) {
        return null;
    }

    @Override
    public Object getTbEduPage(Long personnelId, Integer pageNo, Integer pageSize) {
        return null;
    }
}
