package cn.ffcs.uoo.rabbitmq.monitor.dao;

import cn.ffcs.uoo.rabbitmq.monitor.pojo.RabbitmqIndex;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

public interface RabbitmqIndexMapper extends BaseMapper<RabbitmqIndex> {
    @Select("select 1 from dual")
    int test1();
    int test2();
}