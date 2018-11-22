package cn.ffcs.uoo.rabbitmq.manage.service.impl;

import cn.ffcs.uoo.rabbitmq.manage.dao.SystemQueueRelaMapper;
import cn.ffcs.uoo.rabbitmq.manage.pojo.SystemQueueRela;
import cn.ffcs.uoo.rabbitmq.manage.service.SystemQueueRelaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by liuxiaodong on 2018/11/14.
 */
@Service
public class SystemQueueRelaServiceImpl extends ServiceImpl<SystemQueueRelaMapper, SystemQueueRela> implements SystemQueueRelaService {
    @Override
    public int checkSystemQueueRela(String systemName, String doubleName, String queueName, String status) {
        return baseMapper.checkSystemQueueRela(systemName, doubleName, queueName, status);
    }
}
