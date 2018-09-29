package cn.ffcs.uoo.leaveDemo.dao;

import cn.ffcs.uoo.leaveDemo.entity.AtiDelegateInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-27
 */
public interface AtiDelegateInfoMapper extends BaseMapper<AtiDelegateInfo> {

    void modifyOneDelegateInfo(AtiDelegateInfo atiDelegateInfo);

    void removeOneDelegateInfo(String atiDelegateInfoId);
}
