package cn.ffcs.uoo.leaveDemo.service.impl;

import cn.ffcs.uoo.leaveDemo.entity.AtiDelegateInfo;
import cn.ffcs.uoo.leaveDemo.dao.AtiDelegateInfoMapper;
import cn.ffcs.uoo.leaveDemo.service.AtiDelegateInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
@Service
public class AtiDelegateInfoServiceImpl extends ServiceImpl<AtiDelegateInfoMapper, AtiDelegateInfo> implements AtiDelegateInfoService {

    @Override
    public void modifyOneDelegateInfo(AtiDelegateInfo atiDelegateInfo) {
        baseMapper.modifyOneDelegateInfo(atiDelegateInfo);
    }

    @Override
    public void removeOneDelegateInfo(String atiDelegateInfoId) {
        baseMapper.removeOneDelegateInfo(atiDelegateInfoId);
    }
}
