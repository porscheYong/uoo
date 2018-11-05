package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgContactRel;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
public interface OrgContactRelMapper extends BaseMapper<OrgContactRel> {

    public Long getId();

    public void delete(OrgContactRel orgContactRel);

    /**
     * 获取组织联系人列表
     * @param psonOrgVo
     * @return
     */
    public List<PsonOrgVo> selectOrgContactPage(Pagination page,@Param("psonOrgVo") PsonOrgVo psonOrgVo);

    /**
     * 组织联系人
     * @param orgId
     * @return
     */
    public List<PsonOrgVo> getOrgContact(@Param("orgId")String orgId);

}
