package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.dao.TbUserMapper;
import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbUser;
import cn.ffcs.uoo.core.user.service.CommonSystemService;
import cn.ffcs.uoo.core.user.service.TbUserService;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wudj
 * @since 2018-10-31
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

    @Autowired
    private CommonSystemService commonSystemService;

    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public List<TbRoles> getRoleByUserId(Long userId){
        return  baseMapper.getRoleByUserId(String.valueOf(userId));
    }

    @Override
    public List<ListSlaveAcctVo> getSlaveAcctInfo(Long userId, Long acctId){
        return  baseMapper.getSlaveAcctInfo(userId, acctId);
    }

    @Override
    public Page<ListUserOrgVo> selectUserOrgPage(PsonOrgVo psonOrgVo){
        Page<ListUserOrgVo> page = new Page<ListUserOrgVo>(psonOrgVo.getPageNo()==0?1:psonOrgVo.getPageNo()
                ,psonOrgVo.getPageSize()==0?10:psonOrgVo.getPageSize());
        List<ListUserOrgVo> list = baseMapper.getUserOrg(page, psonOrgVo);
        page.setRecords(list);
        return  page;
    }


    @Override
    public Page<ListUserVo> getUserList( Long personnelId, Integer pageNo, Integer pageSize, String account){

        String inSActSql = commonSystemService.getSysDataRuleSql("t3",BaseUnitConstants.TB_ORG, account);
        String inSql = commonSystemService.getSysDataRuleSql("t2",BaseUnitConstants.TB_ACCOUNT_ORG_REL, account);
        Page<ListUserVo> page = new Page<ListUserVo>(StrUtil.intiPageNo(pageNo), StrUtil.intiPageSize(pageSize));
        page.setRecords(baseMapper.getUserList(page, personnelId, inSql, inSActSql));
        return  page;
    }

    @Override
    public PersonnelInfoVo getPersonnelInfo( Long personnelId){
        return baseMapper.getPersonnelInfo(personnelId);
    }

    @Override
    public Page<ListAcctOrgVo> getAcctOrg(Long acctId, Integer pageNo, Integer pageSize, String account){
        Map<String, String> map = new HashMap<>(16);
        map.put(BaseUnitConstants.TB_ORG, "t1");
        map.put(BaseUnitConstants.TB_ACCOUNT_ORG_REL, "t2");
        String inSql = commonSystemService.getSqlJointList(map, account);
        Page<ListAcctOrgVo> page = new Page<ListAcctOrgVo>(StrUtil.intiPageNo(pageNo)
            , StrUtil.intiPageSize(pageSize));
        page.setRecords(baseMapper.getAcctOrg(page, acctId, inSql));
        return  page;
    }

    @Override
    public List<ListAcctOrgVo> getSlaveAcctOrg(ListAcctOrgVo acctOrgVo){
        return baseMapper.getSlaveAcctOrg(acctOrgVo);
    }

    @Override
    public List<ListAcctOrgVo> getAcctOrgByPsnId(Long personnelId, Long resourceObjId, String account){
        Map<String, String> map = new HashMap<>(16);
        map.put(BaseUnitConstants.TB_ORG, "t1");
        map.put(BaseUnitConstants.TB_ACCOUNT_ORG_REL, "t2");
        String inSql = commonSystemService.getSqlJointList(map, account);
        return baseMapper.getAcctOrgByPsnId(personnelId, resourceObjId, inSql);
    }

}
