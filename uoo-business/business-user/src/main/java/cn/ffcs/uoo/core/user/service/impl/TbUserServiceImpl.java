package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.dao.TbUserMapper;
import cn.ffcs.uoo.core.user.entity.TbRoles;
import cn.ffcs.uoo.core.user.entity.TbUser;
import cn.ffcs.uoo.core.user.service.TbUserService;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.ListUserOrgVo;
import cn.ffcs.uoo.core.user.vo.PsonOrgVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import feign.Param;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
