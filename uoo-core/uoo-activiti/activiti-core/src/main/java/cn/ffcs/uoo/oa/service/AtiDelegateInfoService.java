package cn.ffcs.uoo.oa.service;

import cn.ffcs.uoo.oa.entity.AtiDelegateInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  任务委托服务类
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
public interface AtiDelegateInfoService extends IService<AtiDelegateInfo> {

    Long insertOneDelegateInfo(AtiDelegateInfo atiDelegateInfo);

    void modifyOneDelegateInfo(AtiDelegateInfo atiDelegateInfo);

    void removeOneDelegateInfo(String atiDelegateInfoId);

    List<AtiDelegateInfo> delegateInfoList(String assignee);
}
