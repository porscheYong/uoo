package cn.ffcs.uoo.web.maindata.user.service.fallback;

import cn.ffcs.uoo.web.maindata.user.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHystrix implements UserService {
    @Override
    public Object getUserList(Long personnelId) {
        return null;
    }

    @Override
    public Object getFormAcct(Long acctId) {
        return null;
    }

    @Override
    public Object getFormSlaveAcct(Long acctId) {
        return null;
    }

    @Override
    public Object addUser(String userType, Long personnelId) {
        return null;
    }
}
