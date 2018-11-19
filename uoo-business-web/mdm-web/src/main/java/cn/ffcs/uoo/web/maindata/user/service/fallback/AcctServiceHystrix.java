package cn.ffcs.uoo.web.maindata.user.service.fallback;

import cn.ffcs.uoo.web.maindata.user.service.AcctService;
import cn.ffcs.uoo.web.maindata.user.vo.EditFormAcctVo;
import org.springframework.stereotype.Component;

@Component
public class AcctServiceHystrix implements AcctService {
    @Override
    public Object saveAcct(EditFormAcctVo editFormAcctVo) {
        return null;
    }

    @Override
    public Object removeAcct(Long acctId) {
        return null;
    }

    @Override
    public Object updateAcct(EditFormAcctVo editFormAcctVo) {
        return null;
    }
}
