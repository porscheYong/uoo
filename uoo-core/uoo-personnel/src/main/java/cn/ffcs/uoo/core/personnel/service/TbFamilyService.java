package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbFamily;
import cn.ffcs.uoo.core.personnel.vo.TbFamilyVo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
public interface TbFamilyService extends IService<TbFamily> {

    /**
     * 新增 保存
     * @param tbFamily
     */
    public void save(TbFamily tbFamily);

    public Long getId();

    public void delete(TbFamily tbFamily);
}
