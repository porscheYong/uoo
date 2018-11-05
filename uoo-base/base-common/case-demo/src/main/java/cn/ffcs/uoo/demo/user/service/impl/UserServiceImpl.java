package cn.ffcs.uoo.demo.user.service.impl;

import cn.ffcs.uoo.demo.user.dao.UserMapper;
import cn.ffcs.uoo.demo.user.entity.User;
import cn.ffcs.uoo.demo.user.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-08-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    public void testUserDemo(){
        System.out.println("------");
    }
}
