package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgPersonRel;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-21
 */
public interface OrgPersonRelService extends IService<OrgPersonRel> {

    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param orgPersonRel
     */
    public void delete(OrgPersonRel orgPersonRel);

    public void add(OrgPersonRel orgPersonRel);

    public void update(OrgPersonRel orgPersonRel);
    /**
     * 对象转换
     * @param psonOrgVo
     * @return
     */
    public OrgPersonRel convertObj(PsonOrgVo psonOrgVo);
    /**
     * 组织人员参数判断
     */
    public String judgeOrgPsnParams(PsonOrgVo psonOrgVo);
    /**
     * 获取人员组织列表
     * @param psonOrgVo
     * @return
     */
    public List<PsonOrgVo> getPerOrgRelList(PsonOrgVo psonOrgVo);
    /**
     * 获取人员组织翻页
     * @param psonOrgVo
     * @return
     */
    public Page<PsonOrgVo> selectPerOrgRelPage(PsonOrgVo psonOrgVo);
    /**
     * 获取人员翻页
     * @param psonOrgVo
     * @return
     */
    public Page<PsonOrgVo> selectOrgRelsByPerIdPage(PsonOrgVo psonOrgVo);
    /**
     * 获取全量人员组织翻页
     * @param psonOrgVo
     * @return
     */
    public Page<PsonOrgVo> selectAllPerOrgRelPage(PsonOrgVo psonOrgVo);

    /**
     * 获取人员or人员组织翻页
     * @return
     */
    public Page<PsonOrgVo> selectPerOrOrgRelPage(PsonOrgVo psonOrgVo);

    /**
     * 组织人员检索
     * @param psonOrgVo
     * @return
     */
    public Page<PsonOrgVo> selectFuzzyOrgPsnPage(PsonOrgVo psonOrgVo);

    /**
     * 用户组织查询
     * @param psonOrgVo
     * @return
     */
    public Page<PsonOrgVo> selectUserOrgRelPage(PsonOrgVo psonOrgVo);


    /**
     * 查询全部用户查询
     * @param psonOrgVo
     * @return
     */
    public Page<PsonOrgVo> selectAllUserOrgRelPage(PsonOrgVo psonOrgVo);


    /**
     * 查询组织树组织信息
     */
    public List<OrgPersonRel> getOrgPsnByOrgAndPsnId(String orgTreeId,String personnelId,String orgId);

    /**
     * 查询组织树组织信息
     */
    public List<OrgPersonRel> getOrgPsnRel(String orgTreeId,String orgId);
    /**
     * 查询组织树账号信息
     */
    public List<OrgPersonRel>  getOrgAcctRel(String orgTreeId,String orgId);

    /**
     * 简单查询满足人员信息条件
     * @param search
     * @return
     */
    public boolean JudgePer(String search);

}
