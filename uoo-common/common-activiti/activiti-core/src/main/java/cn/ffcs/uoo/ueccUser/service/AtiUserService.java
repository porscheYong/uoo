package cn.ffcs.uoo.ueccUser.service;

import cn.ffcs.uoo.ueccUser.entity.AtiUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
public interface AtiUserService extends IService<AtiUser> {

    List<AtiUser> getDeptLeaderUsersByNo(String no);

    List<AtiUser> getHrUsersByNo(String no);
}
