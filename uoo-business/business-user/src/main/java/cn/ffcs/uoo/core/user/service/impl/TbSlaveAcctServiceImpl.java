package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.constant.EumUserResponeCode;
import cn.ffcs.uoo.core.user.dao.TbSlaveAcctMapper;
import cn.ffcs.uoo.core.user.entity.ListUser;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import cn.ffcs.uoo.core.user.service.*;
import cn.ffcs.uoo.core.user.util.*;
import cn.ffcs.uoo.core.user.vo.EditFormSlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctOrgVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 从账号 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service
public class TbSlaveAcctServiceImpl extends ServiceImpl<TbSlaveAcctMapper, TbSlaveAcct> implements TbSlaveAcctService {
    @Autowired
    private TbAcctExtService tbAcctExtService;
    @Autowired
    private TbUserRoleService tbUserRoleService;
    @Autowired
    private TbAcctService tbAcctService;
    @Autowired
    private RabbitMqService rabbitMqService;
    @Autowired
    private CommonSystemService commonSystemService;
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public List<ListUser> getUserList(Long slaveAcctId){
        return baseMapper.getUserList(slaveAcctId);
    }

    @Override
    public List<ListUser> getApplyUserList(Long slaveAcctId){ return  baseMapper.getApplyUserList(slaveAcctId); }

    @Override
    public List<TbAcct> getAcct(Long slaveAcctId){ return  baseMapper.getAcct(slaveAcctId); }

    @Override
    public Page<ListSlaveAcctOrgVo> getSlaveAcctOrg(ListSlaveAcctOrgVo slaveAcctOrgVo, String account){
        String inSql = commonSystemService.getSysDataRuleSql("t2", BaseUnitConstants.TB_ACCOUNT_ORG_REL, account);
        int pageSize = slaveAcctOrgVo.getPageSize() == 0 ? 5 : slaveAcctOrgVo.getPageSize();
        Page<ListSlaveAcctOrgVo> page = new Page<ListSlaveAcctOrgVo>(slaveAcctOrgVo.getPageNo(),pageSize);
        List<ListSlaveAcctOrgVo> list = baseMapper.getSlaveAcctOrg(page, slaveAcctOrgVo, inSql);
        page.setRecords(list);
        return page;
    }
    @Override
    public Object addTbSlaveAcct(TbSlaveAcct tbSlaveAcct){
        if(this.insert(tbSlaveAcct)){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public boolean checkSlaveAcct(String slaveAcct, Long acctOrgRelId , Long resourceObjId, Long slaveAcctId, Long acctId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        if(!StrUtil.isNullOrEmpty(slaveAcct)){
            map.put(BaseUnitConstants.TABLE_SLAVE_ACCT, slaveAcct);
        }
        if(!StrUtil.isNullOrEmpty(acctId)){
            map.put(BaseUnitConstants.TABLE_ACCT_ID, acctId);
        }
        map.put(BaseUnitConstants.TB_ACCT_ORG_REL_ID, acctOrgRelId);
        map.put(BaseUnitConstants.TB_RESOURCE_OBJ_ID, resourceObjId);
        TbSlaveAcct tbSlaveAcct = this.selectOne(new EntityWrapper<TbSlaveAcct>().allEq(map));
        if(StrUtil.isNullOrEmpty(tbSlaveAcct)){
            return false;
        }
        if(!StrUtil.isNullOrEmpty(slaveAcctId)){
            if(slaveAcctId.equals(tbSlaveAcct.getSlaveAcctId())){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object delTbSlaveAcct(Long slaveAcctId, Long userId){
        TbSlaveAcct tbSlaveAcct = new TbSlaveAcct();
        tbSlaveAcct.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbSlaveAcct.setStatusDate(new Date());
        tbSlaveAcct.setUpdateUser(userId);
        EntityWrapper<TbSlaveAcct> wrapper = new EntityWrapper<TbSlaveAcct>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TABLE_SLAVE_ACCT_ID, slaveAcctId);
        if(retBool(baseMapper.update(tbSlaveAcct, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object updateTbSlaveAcct(TbSlaveAcct tbSlaveAcct){
        if(retBool(baseMapper.updateById(tbSlaveAcct))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumUserResponeCode.USER_RESPONSE_ERROR);
    }

    @Override
    public Object delAllTbSlaveAcct(Long slaveAcctId, Long userId){
        //从账号
        this.delTbSlaveAcct(slaveAcctId, userId);

        //2、删除角色
        tbUserRoleService.removeUserRole(slaveAcctId, 2L, userId);

        //扩展信息
        tbAcctExtService.delTbAcctExt(slaveAcctId, userId);

        rabbitMqService.sendMqMsg("person", "delete", "slaveAcctId", slaveAcctId);

        return null;
    }

    @Override
    public Object insertOrUpdateTbSlaveAcct(EditFormSlaveAcctVo editFormSlaveAcctVo, Long slaveAcctId){
        String type = "update";
        String pwd = "";
        TbSlaveAcct tbSlaveAcct = baseMapper.selectById(slaveAcctId);
        if(StrUtil.isNullOrEmpty(tbSlaveAcct)){
            tbSlaveAcct = new TbSlaveAcct();
            type = "insert";
        }else{
            pwd = tbSlaveAcct.getPassword();
        }
        CopyUtils.copyProperties(editFormSlaveAcctVo, tbSlaveAcct);

        if("insert".equals(type) || !editFormSlaveAcctVo.getPassword().equals(pwd)){
            if(!PwdPolicyUtil.isMatchBasicPattern(editFormSlaveAcctVo.getPassword())){
                return ResultUtils.error(EumUserResponeCode.PWD_ERROR);
            }
            // 获取盐
            String salt = MD5Tool.getSalt();
            // 非对称密码
            String password = MD5Tool.md5Encoding(editFormSlaveAcctVo.getPassword(), salt);
            // 对称密码
            String symmetryPassword = AESTool.AESEncode(editFormSlaveAcctVo.getPassword());
            tbSlaveAcct.setSalt(salt);
            tbSlaveAcct.setPassword(password);
            tbSlaveAcct.setSymmetryPassword(symmetryPassword);
        }

        if("insert".equals(type)){
            TbAcct tbAcct = (TbAcct) tbAcctService.getTbAcctByPsnId(editFormSlaveAcctVo.getPersonnelId());
            tbSlaveAcct.setAcctId(tbAcct.getAcctId());
            tbSlaveAcct.setSlaveAcctId(slaveAcctId);
            tbSlaveAcct.setCreateUser(editFormSlaveAcctVo.getUserId());
            tbSlaveAcct.setUpdateUser(editFormSlaveAcctVo.getUserId());
            baseMapper.insert(tbSlaveAcct);
        }
        if("update".equals(type)){
            tbSlaveAcct.setUpdateUser(editFormSlaveAcctVo.getUserId());
            baseMapper.updateById(tbSlaveAcct);
        }
        return null;
    }

}
