package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OgtOrgReltypeConf;
import cn.ffcs.uoo.core.organization.dao.OgtOrgReltypeConfMapper;
import cn.ffcs.uoo.core.organization.service.OgtOrgReltypeConfService;
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
        ogtOrgReftypeConf.setUpdateUser(0L);
        updateById(ogtOrgReftypeConf);
    }
    @Override
    public void add(OgtOrgReltypeConf ogtOrgReltypeConf){
        ogtOrgReltypeConf.setCreateDate(new Date());
        ogtOrgReltypeConf.setCreateUser(0L);
        ogtOrgReltypeConf.setStatusCd("1000");
        ogtOrgReltypeConf.setStatusDate(new Date());
        insert(ogtOrgReltypeConf);
    }

    @Override
    public void update(OgtOrgReltypeConf ogtOrgReltypeConf){
        ogtOrgReltypeConf.setUpdateDate(new Date());
        ogtOrgReltypeConf.setUpdateUser(0L);
        ogtOrgReltypeConf.setStatusDate(new Date());
        updateById(ogtOrgReltypeConf);
    }

}
