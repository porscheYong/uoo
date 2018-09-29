package cn.ffcs.uoo.leaveDemo.service.impl;

import cn.ffcs.uoo.leaveDemo.entity.AtiDelegateInfo;
import cn.ffcs.uoo.leaveDemo.dao.AtiDelegateInfoMapper;
import cn.ffcs.uoo.leaveDemo.service.AtiDelegateInfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  任务代理服务实现类
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
@Service
public class AtiDelegateInfoServiceImpl extends ServiceImpl<AtiDelegateInfoMapper, AtiDelegateInfo> implements AtiDelegateInfoService {

    @Override
    public Long insertOneDelegateInfo(AtiDelegateInfo atiDelegateInfo) {
        return baseMapper.insertOneDelegateInfo(atiDelegateInfo);
    }

    @Override
    public void modifyOneDelegateInfo(AtiDelegateInfo atiDelegateInfo) {
        baseMapper.modifyOneDelegateInfo(atiDelegateInfo);
    }

    @Override
    public void removeOneDelegateInfo(String atiDelegateInfoId) {
        baseMapper.removeOneDelegateInfo(atiDelegateInfoId);
    }

    /**
     * 获取原任务办理人有效期内的任务代理列表
     * @param assignee 原任务办理人
     * @return
     */
    @Override
    public List<AtiDelegateInfo> delegateInfoList(String assignee) {
        Wrapper<AtiDelegateInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("ASSIGNEE", assignee);
        wrapper.eq("STATUS_CD", "1000");
        List<AtiDelegateInfo> list = baseMapper.selectList(wrapper);
        if (null == list || list.size() == 0) {
            return new ArrayList<>();
        }
        List<AtiDelegateInfo> delegateInfoList = new ArrayList<>();
        for (AtiDelegateInfo delegateInfo : list) {

            Date startTime = delegateInfo.getStartTime();
            Date endTime = delegateInfo.getEndTime();

            //判断代理时间的有效性
            if (timeNotBetweenNow(startTime, endTime)) {
                continue;
            }

            delegateInfoList.add(delegateInfo);
        }

        return delegateInfoList;
    }

    private boolean timeNotBetweenNow(Date startTime,Date endTime) {
        Date now = new Date();

        if(startTime != null) {
            return now.before(startTime);
        }

        if(endTime != null) {
            return now.after(endTime);
        }

        return false;
    }
}
