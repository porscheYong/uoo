package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.system.dao.SysDeptMapper;
import cn.ffcs.uoo.system.dao.SysUserMapper;
import cn.ffcs.uoo.system.entity.SysDept;
import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.service.SysDeptService;
import cn.ffcs.uoo.system.service.SysUserService;
import cn.ffcs.uoo.system.util.MD5Util;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;


/*
 * 系统域用户Service实现类
 * Created by liuxiaodong on 2018/11/12.
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    SysDeptMapper sysDeptMapper;

    @Override
    public Long getId() {
        return sysDeptMapper.getId();
    }
}
