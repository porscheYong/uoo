package cn.ffcs.uoo.rabbitmq.manage.service;

import cn.ffcs.uoo.rabbitmq.manage.pojo.NodeInfo;
import cn.ffcs.uoo.rabbitmq.manage.vo.NodeVo;
import com.baomidou.mybatisplus.service.IService;

/**
 * Created by liuxiaodong on 2018/11/14.
 */
public interface NodeInfoService extends IService<NodeInfo> {

    NodeVo getRandNodeInfo();

    int test();
}
