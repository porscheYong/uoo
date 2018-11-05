package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgContactRel;
import cn.ffcs.uoo.core.organization.entity.OrgLevel;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
public interface OrgContactRelService extends IService<OrgContactRel> {

    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param orgContactRel
     */
    public void delete(OrgContactRel orgContactRel);

    /**
     * 新增
     */
    public void add(OrgContactRel orgContactRel);

    /**
     * 更新
     */
    public void update(OrgContactRel orgContactRel);

    /**
     * 获取组织联系人翻页
     * @param psonOrgVo
     */
    public Page<PsonOrgVo> selectOrgContactPage(PsonOrgVo psonOrgVo);

    /**
     * 查询组织联系人
     * @param orgId
     * @return
     */
    public List<PsonOrgVo> getOrgContact(String orgId);

}
