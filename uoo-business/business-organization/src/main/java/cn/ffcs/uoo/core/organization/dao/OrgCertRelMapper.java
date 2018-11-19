package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.OrgCertRel;
import cn.ffcs.uoo.core.organization.vo.OrgCertVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 解决智慧BSS组织机构接入过程中的法人信息问题 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-22
 */
public interface OrgCertRelMapper extends BaseMapper<OrgCertRel> {

    public Long getId();

    public void delete(OrgCertRel orgCertRel);

    public List<OrgCertVo> getOrgCerRelByOrgId(@Param("orgId")Long orgId);
}
