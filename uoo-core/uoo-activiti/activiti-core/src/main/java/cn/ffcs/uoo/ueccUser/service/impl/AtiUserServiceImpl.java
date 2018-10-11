package cn.ffcs.uoo.ueccUser.service.impl;

import cn.ffcs.uoo.ueccUser.dao.AtiUserMapper;
import cn.ffcs.uoo.ueccUser.entity.AtiUser;
import cn.ffcs.uoo.ueccUser.service.AtiUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户实体服务实现类
 * Created by liuxiaodong on 2018/10/9.
 */
@Service
public class AtiUserServiceImpl extends ServiceImpl<AtiUserMapper, AtiUser> implements AtiUserService {
    @Override
    public List<AtiUser> getDeptLeaderUsersByNo(String no) {
        return baseMapper.getDeptLeaderUsersByNo(no);
    }

    @Override
    public List<AtiUser> getHrUsersByNo(String no) {
        return baseMapper.getHrUsersByNo(no);
    }
}
