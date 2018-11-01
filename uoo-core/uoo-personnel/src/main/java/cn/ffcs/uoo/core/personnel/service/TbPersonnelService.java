package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.vo.PersonnelOrgVo;
import cn.ffcs.uoo.core.personnel.vo.PersonnelRelationInfoVo;
import cn.ffcs.uoo.core.personnel.vo.PsonOrgVo;
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

    public Long getId();

    public void delete(TbPersonnel tbPersonnel);

    public Page<PersonnelOrgVo> getPersonnelOrg(TbPersonnelVo tbPersonnelVo);

    /**
     * 员工工号  序
     * @return
     */
    public Long getPsnNbrId();

    /**
     * 人员编码
     * @return
     */
    public Long getPsnCodeId();

    /**
     * 人员归属组织
     * @return
     */
    public Page<PsonOrgVo> selectPsonOrgPage(PsonOrgVo psonOrgVo);
}
