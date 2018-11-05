package cn.ffcs.uoo.core.organization.service.impl;

import cn.ffcs.uoo.core.organization.entity.OgtOrgtypeConf;
import cn.ffcs.uoo.core.organization.dao.OgtOrgtypeConfMapper;
import cn.ffcs.uoo.core.organization.service.OgtOrgtypeConfService;
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
public class OgtOrgtypeConfServiceImpl extends ServiceImpl<OgtOrgtypeConfMapper, OgtOrgtypeConf> implements OgtOrgtypeConfService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    @Override
    public void delete(OgtOrgtypeConf ogtOrgtypeConf){
        ogtOrgtypeConf.setStatusCd("1100");
        ogtOrgtypeConf.setStatusDate(new Date());
        ogtOrgtypeConf.setUpdateDate(new Date());
        ogtOrgtypeConf.setUpdateUser(0L);
        updateById(ogtOrgtypeConf);
    }


    /**
     * 新增
     */
    @Override
    public void add(OgtOrgtypeConf ogtOrgtypeConf){
        ogtOrgtypeConf.setCreateDate(new Date());
        ogtOrgtypeConf.setCreateUser(0L);
        ogtOrgtypeConf.setStatusCd("1000");
        ogtOrgtypeConf.setStatusDate(new Date());
        insert(ogtOrgtypeConf);
    }

    /**
     * 更新
     */
    @Override
    public void update(OgtOrgtypeConf ogtOrgtypeConf){
        ogtOrgtypeConf.setUpdateDate(new Date());
        ogtOrgtypeConf.setUpdateUser(0L);
        ogtOrgtypeConf.setStatusDate(new Date());
        updateById(ogtOrgtypeConf);
    }
}
