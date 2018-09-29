package cn.ffcs.uoo.leaveDemo.service;


import cn.ffcs.uoo.leaveDemo.dao.LeaveMapper;
import cn.ffcs.uoo.leaveDemo.entity.Leave;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 请假 service 实现类
 * Created by liuxiaodong on 2018/9/20.
 */
@Service
public interface LeaveService extends IService<Leave> {

    long addLeave(Leave leave);
}
