package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.base.common.tool.util.StringUtils;
import cn.ffcs.uoo.system.consts.BaseUnitConstants;
import cn.ffcs.uoo.system.dao.SysUserMapper;
import cn.ffcs.uoo.system.entity.SysUser;
import cn.ffcs.uoo.system.entity.SysUserDeptRef;
import cn.ffcs.uoo.system.service.SysUserDeptRefService;
import cn.ffcs.uoo.system.service.SysUserPositionRefService;
import cn.ffcs.uoo.system.service.SysUserService;
import cn.ffcs.uoo.system.util.IdCardVerification;
import cn.ffcs.uoo.system.util.MD5Util;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.SysUserDeptPositionVo;
import cn.ffcs.uoo.system.vo.SysUserDeptVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Resource
    SysUserMapper sysUserMapper;

    @Autowired
    private SysUserDeptRefService sysUserDeptRefService;
    @Autowired
    private SysUserPositionRefService sysUserPositionRefService;

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
        // 选项有：账号，密码，姓名，性别，部门，角色，岗位，证件类型，证件号，手机，邮箱，变更原因
        if (StringUtils.isEmpty(sysUser.getAccout())) {
            return "账号为空！";
        }
        if (StringUtils.isEmpty(sysUser.getPasswd())) {
            return "密码为空!";
        }
        if (StringUtils.isEmpty(sysUser.getUserName())) {
            return "姓名为空！";
        }
        if (StringUtils.isEmpty(sysUser.getMobile())) {
            return "手机号为空！";
        }
        /*
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
        if (StringUtils.isEmpty(sysUser.getEmail())) {
            return "邮箱为空！";
        }
        if (StringUtils.isEmpty(sysUser.getReason())) {
            return "变更原因为空！";
        }*/

        return null;
    }

    @Override
    public void sysUserRegister(@RequestBody SysUser sysUser) {
        String salt = MD5Util.getSalt();
        String md5Content = DigestUtils.md5Hex(sysUser.getPasswd());
        sysUser.setSalt(salt);
        sysUser.setPasswd(MD5Util.md5Encoding(md5Content, salt));
        sysUser.setUserId(getId());
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

    @Override
    public String checkAllRegister(SysUser sysUser){
        String msg = checkRegister(sysUser);
        if(!StrUtil.isNullOrEmpty(msg)){
            return msg;
        }
        if (StringUtils.isEmpty(sysUser.getGender())) {
            return "性别为空！";
        }
        if (StringUtils.isEmpty(sysUser.getCertType())) {
            return "证件类型为空！";
        }
        if (StringUtils.isEmpty(sysUser.getCertId())) {
            return "证件号为空！";
        }
//        if (StringUtils.isEmpty(sysUser.getEmail())) {
//            return "邮箱为空！";
//        }
        if(!IdCardVerification.idCardValidate(sysUser.getCertId())){
            return "证件号格式有误";
        }
        if(!StrUtil.checkTelephoneNumber(sysUser.getMobile())){
            return "手机号格式有误";
        }
        if(!StrUtil.checkEmail(sysUser.getEmail())){
            return "邮箱格式有误";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TB_SYS_USER_ACCOUT, sysUser.getAccout());
        SysUser user = this.selectOne(new EntityWrapper<SysUser>().allEq(map));
        if(!StrUtil.isNullOrEmpty(user)){
            if(!StrUtil.isNullOrEmpty(sysUser.getUserId()) && !user.getUserId().equals(sysUser.getUserId())){
                return "账号已经被占用";
            }
        }


        // 手机号，证件号、邮箱等 重复验证

        return null;
    }

    @Override
    public Object addOrUpdateUser(SysUser sysUser){
        String type = BaseUnitConstants.TYPE_UPDATE;
        SysUser user = this.getSysUserById(sysUser.getUserId());
        if(StrUtil.isNullOrEmpty(user)){
            type = BaseUnitConstants.TYPE_INSERT;
        }
        if(type.equals(BaseUnitConstants.TYPE_INSERT) || !sysUser.getPasswd().equals(user.getPasswd())){
            String salt = MD5Util.getSalt();
            String md5Content = DigestUtils.md5Hex(sysUser.getPasswd());
            sysUser.setSalt(salt);
            sysUser.setPasswd(MD5Util.md5Encoding(md5Content, salt));
            sysUser.setUserId(getId());
        }
        if(type.equals(BaseUnitConstants.TYPE_INSERT)){
            baseMapper.insert(sysUser);
        }
        if(type.equals(BaseUnitConstants.TYPE_UPDATE)){
            baseMapper.updateById(sysUser);
        }

        return null;
    }

    @Override
    public SysUser getSysUserById(Long userId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        map.put(BaseUnitConstants.TB_SYS_USER_ID, userId);
        SysUser user = this.selectOne(new EntityWrapper<SysUser>().allEq(map));
        if(StrUtil.isNullOrEmpty(user)){
            return null;
        }
        return user;
    }

    @Override
    public Page<SysUserDeptPositionVo> getUserDeptPosition(String userCode, Integer pageNo, Integer pageSize){
        List<SysUserDeptPositionVo> sysUserDeptPositionVoList = new ArrayList<>();
        Page<SysUserDeptVo> userDeptRefPage = sysUserDeptRefService.getUserDeptByUserCode(userCode, pageNo, pageSize);
        List<SysUserDeptVo> sysUserDeptRefs = userDeptRefPage.getRecords();
        if(sysUserDeptRefs != null && sysUserDeptRefs.size() > 0){
            for(SysUserDeptVo userDeptRef : sysUserDeptRefs){
                SysUserDeptPositionVo userDeptPositionVo = new SysUserDeptPositionVo();
                BeanUtils.copyProperties(userDeptRef, userDeptPositionVo);
                userDeptPositionVo.setUserPositionRefList(sysUserPositionRefService.getUserPositionRef(userCode, userDeptRef.getOrgCode()));
                sysUserDeptPositionVoList.add(userDeptPositionVo);
            }
        }
       Page<SysUserDeptPositionVo> page = new Page<SysUserDeptPositionVo>(StrUtil.intiPageNo(pageNo), StrUtil.intiPageSize(pageSize));
//        List<SysUserDeptPositionVo> list = baseMapper.getUserDeptPosition(page, userCode);
        page.setTotal(userDeptRefPage.getTotal());
        page.setRecords(sysUserDeptPositionVoList);
        return page;
    }




}
