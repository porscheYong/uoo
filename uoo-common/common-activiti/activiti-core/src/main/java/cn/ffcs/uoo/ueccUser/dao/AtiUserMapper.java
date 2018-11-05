package cn.ffcs.uoo.ueccUser.dao;

import cn.ffcs.uoo.ueccUser.entity.AtiUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  用户登录实体Mapper 接口
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
public interface AtiUserMapper extends BaseMapper<AtiUser> {

    /**
     * 根据发起人账号获取第一审批人
     * @param no 发起人账号
     * @return 第一审批人
     */
    List<AtiUser> getDeptLeaderUsersByNo(String no);

    /**
     * 根据发起人账号获取第二审批人
     * @param no 发起人账号
     * @return 第二审批人
     */
    List<AtiUser> getHrUsersByNo(String no);
}
