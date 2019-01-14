package cn.ffcs.interfaces.cpc.service;

import cn.ffcs.interfaces.cpc.pojo.TbCert;
import com.baomidou.mybatisplus.service.IService;

/**
 * Created by liuxiaodong on 2019/1/10.
 */
public interface TbCertService extends IService<TbCert> {
    Long checkExistCertTypeAndCertNumber(String certType ,String certNumber);

    boolean deleteByPersonnelId(Long personnelId);
}
