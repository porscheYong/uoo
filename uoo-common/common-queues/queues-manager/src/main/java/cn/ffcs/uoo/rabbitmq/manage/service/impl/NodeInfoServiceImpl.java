package cn.ffcs.uoo.rabbitmq.manage.service.impl;

import cn.ffcs.uoo.rabbitmq.manage.dao.NodeInfoMapper;
import cn.ffcs.uoo.rabbitmq.manage.pojo.NodeInfo;
import cn.ffcs.uoo.rabbitmq.manage.service.NodeInfoService;
import cn.ffcs.uoo.rabbitmq.manage.vo.NodeVo;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by liuxiaodong on 2018/11/14.
 */
@Service
public class NodeInfoServiceImpl extends ServiceImpl<NodeInfoMapper, NodeInfo> implements NodeInfoService{

    @Override
    public NodeVo getRandNodeInfo() {
        return baseMapper.getRandNodeInfo();
    }

    @Override
    public int test() {
        return baseMapper.test();
    }
}
