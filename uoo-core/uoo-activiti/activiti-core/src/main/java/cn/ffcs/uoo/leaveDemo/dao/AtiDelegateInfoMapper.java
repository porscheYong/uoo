package cn.ffcs.uoo.leaveDemo.dao;

import cn.ffcs.uoo.leaveDemo.entity.AtiDelegateInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  AtiDelegateInfoMapper 接口
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
public interface AtiDelegateInfoMapper extends BaseMapper<AtiDelegateInfo> {

    Long insertOneDelegateInfo(AtiDelegateInfo atiDelegateInfo);

    void modifyOneDelegateInfo(AtiDelegateInfo atiDelegateInfo);

    void removeOneDelegateInfo(String atiDelegateInfoId);
}
