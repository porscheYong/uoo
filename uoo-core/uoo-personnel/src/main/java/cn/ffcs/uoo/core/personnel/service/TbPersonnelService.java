package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.vo.PersonnelRelationInfoVo;
import cn.ffcs.uoo.core.personnel.vo.TbPersonnelVo;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-09-06
 */
public interface TbPersonnelService extends IService<TbPersonnel> {
    public Page<PersonnelRelationInfoVo> getPersonnelRelationInfo(TbPersonnelVo tbPersonnelVo);
}
