package cn.ffcs.uoo.web.maindata.user.service.fallback;

import cn.ffcs.uoo.web.maindata.user.service.SlaveAcctService;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormSlaveAcctVo;
import org.springframework.stereotype.Component;

@Component
public class SlaveAcctServiceHystrix implements SlaveAcctService {
    @Override
    public Object saveSlaveAcct(EditFormSlaveAcctVo editFormSlaveAcctVo) {
        return null;
    }

    @Override
    public Object delTbSlaveAcct(Long slaveAcctId) {
        return null;
    }

    @Override
    public Object updateTbSlaveAcct(EditFormSlaveAcctVo editFormSlaveAcctVo) {
        return null;
    }
}
