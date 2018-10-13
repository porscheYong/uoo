package cn.ffcs.uoo.oa.service.impl;

import cn.ffcs.uoo.oa.dao.AtiBaseFormMapper;
import cn.ffcs.uoo.oa.entity.AtiBaseForm;
import cn.ffcs.uoo.oa.service.AtiBaseFormService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 通用表单服务实现类
 * Created by liuxiaodong on 2018/10/8.
 */
@Service
public class AtiBaseFormServiceImpl extends ServiceImpl<AtiBaseFormMapper, AtiBaseForm> implements AtiBaseFormService {
    @Override
    public void addAtiBaseForm(AtiBaseForm atiBaseForm) {
        baseMapper.addAtiBaseForm(atiBaseForm);
    }

    @Override
    public AtiBaseForm getBaseFormByProcInstId(String procInstId) {
        return baseMapper.getBaseFormByProcInstId(procInstId);
    }

    @Override
    public void updateProcInstIdByBaseFormId(String procInstId, Long atiBaseFormId) {
        baseMapper.updateProcInstIdByBaseFormId(procInstId, atiBaseFormId);
    }
}
