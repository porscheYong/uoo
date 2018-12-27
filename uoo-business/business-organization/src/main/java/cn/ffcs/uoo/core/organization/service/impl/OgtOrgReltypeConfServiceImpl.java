package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OgtOrgReltypeConf;
import cn.ffcs.uoo.core.organization.dao.OgtOrgReltypeConfMapper;
import cn.ffcs.uoo.core.organization.service.OgtOrgReltypeConfService;
import cn.ffcs.uoo.core.organization.util.StrUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Service
public class OgtOrgReltypeConfServiceImpl extends ServiceImpl<OgtOrgReltypeConfMapper, OgtOrgReltypeConf> implements OgtOrgReltypeConfService {

    @Override
    public Long getId(){
        return baseMapper.getId();
    }
    @Override
    public void delete(OgtOrgReltypeConf ogtOrgReftypeConf){
        ogtOrgReftypeConf.setStatusCd("1100");
        ogtOrgReftypeConf.setStatusDate(new Date());
        ogtOrgReftypeConf.setUpdateDate(new Date());
        ogtOrgReftypeConf.setUpdateUser(StrUtil.isNullOrEmpty(ogtOrgReftypeConf.getUpdateUser())?0L:ogtOrgReftypeConf.getUpdateUser());
        updateById(ogtOrgReftypeConf);
    }
    @Override
    public void add(OgtOrgReltypeConf ogtOrgReltypeConf){
        ogtOrgReltypeConf.setCreateDate(new Date());
        ogtOrgReltypeConf.setCreateUser(StrUtil.isNullOrEmpty(ogtOrgReltypeConf.getCreateUser())?0L:ogtOrgReltypeConf.getCreateUser());
        ogtOrgReltypeConf.setStatusCd("1000");
        ogtOrgReltypeConf.setStatusDate(new Date());
        insert(ogtOrgReltypeConf);
    }

    @Override
    public void update(OgtOrgReltypeConf ogtOrgReltypeConf){
        ogtOrgReltypeConf.setUpdateDate(new Date());
        ogtOrgReltypeConf.setUpdateUser(StrUtil.isNullOrEmpty(ogtOrgReltypeConf.getUpdateUser())?0L:ogtOrgReltypeConf.getUpdateUser());
        ogtOrgReltypeConf.setStatusDate(new Date());
        updateById(ogtOrgReltypeConf);
    }

}
