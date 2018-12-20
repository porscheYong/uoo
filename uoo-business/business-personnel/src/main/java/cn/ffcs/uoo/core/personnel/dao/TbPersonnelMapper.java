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

    public Long getSeqPsnCode();

    public PsnByUserVo getPsnByUser(PsnByUserVo psnByUserVo);

    public List<PsnBasicInfoVo> getPsnBasicInfo(Pagination page, @Param("psnBasicInfoVo") PsnBasicInfoVo psnBasicInfoVo);

    public Integer getAcctNumByPsnId(@Param("personnelId") Long personnelId);

    public List<FreePsnInfoVo> getFreePsnInfo(Pagination page, @Param("keyWord") String keyWord);

    public UomGrpUserOrgInfoVo getIdCardNcCode(@Param("certNo") String certNo);

    public List<HomeStatisticsVo> getHomeStatistics(@Param("labelType") String labelType);
}
