package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.dao.TbCertMapper;
import cn.ffcs.interfaces.cpc.pojo.TbCert;
import cn.ffcs.interfaces.cpc.service.TbCertService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created by liuxiaodong on 2019/1/10.
 */
@Service
public class TbCertServiceImpl extends ServiceImpl<TbCertMapper, TbCert> implements TbCertService {
}
