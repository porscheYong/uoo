package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@Component
public interface OrgMapper extends BaseMapper<Org> {

    public Long getId();

    public void delete(Org org);

    /**
     * 查询组织信息
     * @param id
     * @return
     */
    //public Org queryOrgById(Long id);


    /**
     * 组织总条数
     * @param org
     * @return
     */
    public int queryOrgCount(@Param("org")Org org);

    /**
     * 组织查询
     * @param org
     * @return
     */
    public List<Org> getOrgList(Org org);


    /**
     * 组织编码
     * @return
     */
    public Long getOrgCodeSeq();


    /**
     * 组织关系翻页查询
     * @param page
     * @param orgVo
     * @return
     */
    public List<OrgVo> selectOrgRelPage(Pagination page,@Param("orgVo")OrgVo orgVo);
    /**
     * 组织翻页查询
     * @param page
     * @param orgVo
     * @return
     */
    public List<OrgVo> selectOrgPage(Pagination page,@Param("orgVo")OrgVo orgVo);

    /**
     * 获取系统路径
     * @param orgRootId
     * @param orgId
     * @return
     */
    public String getSysFullName(@Param("orgRootId")String orgRootId,@Param("orgId")String orgId);

}
