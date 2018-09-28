package cn.ffcs.uoo.leaveDemo.service.impl;

import cn.ffcs.uoo.leaveDemo.dao.LeaveMapper;
import cn.ffcs.uoo.leaveDemo.entity.Leave;
import cn.ffcs.uoo.leaveDemo.service.LeaveService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liuxiaodong on 2018/9/21.
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements LeaveService {

    @Resource
    private LeaveMapper leaveMapper;

    @Override
    public long addLeave(Leave leave) {
        return leaveMapper.addLeave(leave);
    }
}
