package cn.ffcs.uoo.leaveDemo.service.impl;

import cn.ffcs.uoo.leaveDemo.dao.AtiBaseFormMapper;
import cn.ffcs.uoo.leaveDemo.entity.AtiBaseForm;
import cn.ffcs.uoo.leaveDemo.service.AtiBaseFormService;
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
    public void updateProcInstIdByBaseFormId(String procInstId, Long atiBaseFormId) {
        baseMapper.updateProcInstIdByBaseFormId(procInstId, atiBaseFormId);
    }
}
