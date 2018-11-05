package cn.ffcs.uoo.core.personnel.dao;

import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.vo.*;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-09-06
 */
public interface TbPersonnelMapper extends BaseMapper<TbPersonnel> {
    public List<PersonnelRelationInfoVo> getPersonnelRelationInfo(TbPersonnelVo tbPersonnelVo);

    public Long getId();

    public void delete(TbPersonnel tbPersonnel);

    public List<PersonnelOrgVo> getPersonnelOrg(TbPersonnelVo tbPersonnelVo);

    public Long getPsnNbrId();

    public Long getPsnCodeId();

    public List<PsonOrgVo> getPsnOrg(Pagination page, PsonOrgVo psonOrgVo);

    public PsnByUserVo getPsnByUser(PsnByUserVo psnByUserVo);
}
