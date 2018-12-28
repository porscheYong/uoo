package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.vo.*;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    public Long getId();

    public void delete(TbPersonnel tbPersonnel);

    public Page<PersonnelOrgVo> getPersonnelOrg(TbPersonnelVo tbPersonnelVo);

    /**
     * 员工工号 人员编码
     * @return
     */
    public Long getSeqPsnCode();

    /**
     * 用户关联人员基础信息
     * @param psnByUserVo
     * @return
     */
    public PsnByUserVo getPsnByUser(PsnByUserVo psnByUserVo);

    /**
     * 根据personnelId 删除 人员基础信息
     * @param personnelId
     * @return
     */
    public Object delTbPersonnelByPsnId(Long personnelId, Long userId);

    /**
     * 选择人员
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<PsnBasicInfoVo> getPsnBasicInfo(String keyWord, Integer pageNo, Integer pageSize);

    /**
     *主账号是否存在
     * @param personnelId
     * @return
     */
    public boolean isExistsAcct( Long personnelId);

    /**
     * 人员新增或者更新
     * @param tbPersonnel
     * @return
     */
    public Object insertOrUpdateTbPsn(TbPersonnel tbPersonnel);

    /**
     * 游离人员
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<FreePsnInfoVo> getFreePsnInfo(String keyWord, Integer pageNo, Integer pageSize);

    /**
     * 根据身份证  查询人力编码
     * @param certNo
     * @return
     */
    public UomGrpUserOrgInfoVo getIdCardNcCode(String certNo);

    /**
     * 首页统计
     * @param labelType
     * @return
     */
    public Object getHomeStatistics(String labelType);
}
