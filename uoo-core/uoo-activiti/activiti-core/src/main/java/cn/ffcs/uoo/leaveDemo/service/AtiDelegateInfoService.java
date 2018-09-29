package cn.ffcs.uoo.leaveDemo.service;

import cn.ffcs.uoo.leaveDemo.entity.AtiDelegateInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  任务委托服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-27
 */
public interface AtiDelegateInfoService extends IService<AtiDelegateInfo> {

    void modifyOneDelegateInfo(AtiDelegateInfo atiDelegateInfo);

    void removeOneDelegateInfo(String atiDelegateInfoId);
}
