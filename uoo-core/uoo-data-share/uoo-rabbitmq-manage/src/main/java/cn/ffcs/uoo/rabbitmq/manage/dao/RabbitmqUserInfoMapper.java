package cn.ffcs.uoo.rabbitmq.manage.dao;

import cn.ffcs.uoo.rabbitmq.manage.pojo.RabbitmqUserInfo;

public interface RabbitmqUserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RabbitmqUserInfo record);

    int insertSelective(RabbitmqUserInfo record);

    RabbitmqUserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RabbitmqUserInfo record);

    int updateByPrimaryKey(RabbitmqUserInfo record);
}