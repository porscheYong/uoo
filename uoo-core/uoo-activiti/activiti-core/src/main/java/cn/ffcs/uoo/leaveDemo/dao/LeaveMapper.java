package cn.ffcs.uoo.leaveDemo.dao;

import cn.ffcs.uoo.leaveDemo.entity.Leave;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * 请假 Mapper 接口
 */
@Component
public interface LeaveMapper extends BaseMapper<Leave> {

    long addLeave(Leave leave);

}