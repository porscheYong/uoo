package cn.ffcs.uoo.rabbitmq.monitor.dao;

import cn.ffcs.uoo.rabbitmq.monitor.pojo.RabbitmqIndex;
import java.math.BigDecimal;

public interface RabbitmqIndexMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(RabbitmqIndex record);

    int insertSelective(RabbitmqIndex record);

    RabbitmqIndex selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(RabbitmqIndex record);

    int updateByPrimaryKeyWithBLOBs(RabbitmqIndex record);

    int updateByPrimaryKey(RabbitmqIndex record);
}