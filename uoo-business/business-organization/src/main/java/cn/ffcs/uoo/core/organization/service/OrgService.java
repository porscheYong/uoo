package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.PoliticalLocation;
import cn.ffcs.uoo.core.organization.vo.AreaCodeVo;
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

//    /**
//     * 保存组织
//     */
//    public void insertByObj(OrgVo org);
    /**
     * 组织新增参数判断
     * @param org
     * @return
     */
    public String JudgeOrgParams(OrgVo org);

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
     * 获取系统全路径
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public String getSysFullName(String orgTreeId,String orgId);

    /**
     * 根据组织标识查询组织信息
     * @param orgId
     * @return
     */
    public OrgVo selectOrgByOrgId(String orgId,String orgTreeId);


    /**
     * 获取组织行政管理区域编码
     * @param orgId
     * @return
     */
    public List<PoliticalLocation> getOrgLoc(String orgId);

    /**
     * 获取区域信息
     * @param orgId
     * @return
     */
    public List<AreaCodeVo> getOrgAreaCode(String orgId);



}
