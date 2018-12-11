package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.dao.TbCertMapper;
import cn.ffcs.uoo.core.personnel.entity.TbCert;
import cn.ffcs.uoo.core.personnel.service.TbCertService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 证件 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Service
public class TbCertServiceImpl extends ServiceImpl<TbCertMapper, TbCert> implements TbCertService {
    @Autowired
    private TbCertMapper tbCertMapper;

    @Override
    public Long getId() {
        return tbCertMapper.getId();
    }

    @Override
    public void delete(TbCert tbCert) {
        tbCertMapper.delete(tbCert);
    }

    @Override
    public TbCert getTbCertByPersonnelId(Integer personnelId){
        return  tbCertMapper.getTbCertByPersonnelId(personnelId);
    }
}
