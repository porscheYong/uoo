package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import cn.ffcs.uoo.core.organization.vo.PageVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OrgService extends IService<Org> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    public void delete(Org org);


    public void add(Org org);


    public void update(Org org);

    public String JudgeOrgParams(Org org);

    /**
     * 获取组织code
     * @return
     */
    public String getGenerateOrgCode();
    /**
     * 查询组织列表
     * @param org
     * @return
     */
    public List<Org> getOrgList(Org org);


    /**
     * 组织关系分页
     * @param org
     * @return
     */
    public Page<OrgVo> selectOrgRelPage(OrgVo org);

    /**
     * 组织分页
     * @param org
     * @return
     */
    public Page<OrgVo> selectOrgPage(OrgVo org);


    /**
     * 获取系统路径
     * @param orgRelType
     * @param id
     * @return
     */
    public String getSysFullName(String orgRootId,String id);
}
