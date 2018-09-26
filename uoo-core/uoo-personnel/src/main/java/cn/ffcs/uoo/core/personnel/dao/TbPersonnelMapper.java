package cn.ffcs.uoo.core.personnel.dao;

import cn.ffcs.uoo.base.common.annotion.MyBatisDao;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.vo.PersonnelRelationInfoVo;
import cn.ffcs.uoo.core.personnel.vo.TbPersonnelVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-09-06
 */
@MyBatisDao
public interface TbPersonnelMapper extends BaseMapper<TbPersonnel> {
    public List<PersonnelRelationInfoVo> getPersonnelRelationInfo(TbPersonnelVo tbPersonnelVo);
}