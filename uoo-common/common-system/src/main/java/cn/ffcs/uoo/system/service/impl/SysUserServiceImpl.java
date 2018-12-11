package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.system.dao.SysUserMapper;
import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.service.SysUserService;
import cn.ffcs.uoo.system.util.AESUtil;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public Long getId() {
        return sysUserMapper.getId();
    }

    /**
     * 登录
     * @param sysUser 用户信息
     */
    @Override
    public String sysUserLogin(SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getAccout())) {
            return "登录名为空！";
        }

        if (StringUtils.isEmpty(sysUser.getPasswd())) {
            return "密码为空!";
        }

        Wrapper<SysUser> wrapper = new EntityWrapper<>();

        if (Pattern.matches(REGEX_MOBILE, sysUser.getAccout())) {
            wrapper.eq("mobile", sysUser.getAccout()).eq("status_cd","1000");
        } else if (Pattern.matches(REGEX_EMAIL, sysUser.getAccout())) {
            wrapper.eq("email", sysUser.getAccout()).eq("status_cd","1000");
        } else {
            wrapper.eq("accout", sysUser.getAccout()).eq("status_cd","1000");
        }
        List<SysUser> userList = baseMapper.selectList(wrapper);
        if (null == userList || userList.size() == 0) {
            return "用户名不存在！";
        }
        if (!MD5Util.verify(sysUser.getPasswd(), userList.get(0).getSalt(), userList.get(0).getPasswd())) {
            return "密码不正确!";
        }

        return null;
    }

    /**
     * 检查注册
     * @param sysUser 用户信息
     */
    @Override
    public String checkRegister(SysUser sysUser) {
        // 必需项有：账号，密码，姓名，性别，部门，角色，岗位，证件类型，证件号，手机，邮箱，变更原因
        if (StringUtils.isEmpty(sysUser.getAccout())) {
            return "账号为空！";
        }
        if (StringUtils.isEmpty(sysUser.getPasswd())) {
            return "密码为空!";
        }
        if (StringUtils.isEmpty(sysUser.getUname())) {
            return "姓名为空！";
        }
        if (StringUtils.isEmpty(sysUser.getGender())) {
            return "性别为空！";
        }
        if (null == sysUser.getDeptId()) {
            return "部门为空！";
        }
        if (null == sysUser.getRoleId()) {
            return "角色为空！";
        }
        if (null == sysUser.getPostionId()) {
            return "岗位为空！";
        }
        if (StringUtils.isEmpty(sysUser.getCertType())) {
            return "证件类型为空！";
        }
        if (StringUtils.isEmpty(sysUser.getCertId())) {
            return "证件号为空！";
        }
        if (StringUtils.isEmpty(sysUser.getMobile())) {
            return "手机号为空！";
        }
        if (StringUtils.isEmpty(sysUser.getEmail())) {
            return "邮箱为空！";
        }
        if (StringUtils.isEmpty(sysUser.getReason())) {
            return "变更原因为空！";
        }

        return null;
    }

    @Override
    public void sysUserRegister(SysUser sysUser) {
        String salt = MD5Util.getSalt();
        String md5Content = DigestUtils.md5Hex(sysUser.getPasswd());
        sysUser.setSalt(salt);
        sysUser.setPasswd(MD5Util.md5Encoding(md5Content, salt));
        setObjStatus(sysUser);
        baseMapper.insert(sysUser);
    }

    /**
     * 设置对象状态
     * @param sysUser 用户信息
     */
    private void setObjStatus(SysUser sysUser) {
        sysUser.setStatusCd("1000");
        sysUser.setCreateDate(DateUtils.parseDate(DateUtils.getDate()));
        sysUser.setCreateUser(1L);
        sysUser.setUpdateDate(DateUtils.parseDate(DateUtils.getDate()));
        sysUser.setUpdateUser(1L);
        sysUser.setStatusDate(DateUtils.parseDate(DateUtils.getDate()));
    }
}
