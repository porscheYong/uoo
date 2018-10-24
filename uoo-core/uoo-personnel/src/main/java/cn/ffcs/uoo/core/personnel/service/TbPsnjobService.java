package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbPsnjob;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
public interface TbPsnjobService extends IService<TbPsnjob> {

    public Long getId();

    public void delete(TbPsnjob tbPsnjob);

}
