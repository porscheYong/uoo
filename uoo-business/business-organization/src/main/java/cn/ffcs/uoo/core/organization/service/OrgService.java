package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.PoliticalLocation;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
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


    /**
     * 获取组织名称全路
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public String getFullOrgNameList(String orgTreeId,String orgId,String split);

    /**
     * 获取组织标识名称全路径
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public String getFullOrgIdList(String orgTreeId,String orgId,String split);

    /**
     * 移动组织判断
     * @param orgId
     * @param parentOrgId
     * @param orgTreeId
     * @return
     */
    public String JudgeMoveOrg(Long orgId,Long parentOrgId,String orgName,Long orgTreeId);

    /**
     * 组织名称更新
     * @param orgId
     * @param orgName
     * @param orgTreeId
     * @return
     */
    public String JudgeUpdateOrgName(String orgId,String orgName,Long orgTreeId);
    /**
     * 组织移动
     * @param orgId
     * @param parentOrgId
     * @param orgTreeId
     * @return
     */
    public String moveOrg(Long orgId,Long parentOrgId,Long orgTreeId,Long userId,String batchNumber);

    /**
     * 组织树路径
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public List<TreeNodeVo> getFullOrgVo(String orgTreeId, String orgId);

    /**
     * 下级组织信息
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public List<OrgVo> getLowOrgs(String orgTreeId, String orgId);


    /**
     * 下级组织信息
     * @param refCode
     * @param orgId
     * @return
     */
    public List<OrgVo> getLowOrgsByRefCode(String refCode, String orgId);

    /**
     * 获取渠道编码和标识
     * @param orgId
     * @return
     */
    public HashMap<String,String> getChannelInfo(String orgId);

    /**
     * 渠道组织翻页
     * @param orgVo
     * @return
     */
    public Page<OrgVo> getChannelOrgPage(OrgVo orgVo);

    /**
     * 渠道组织数
     * @return
     */
    public int getChannelOrgCount();

    /**
     * 渠道组织已经被挂载树
     * @param orgTreeId
     * @param orgId
     * @return
     */
    public int getChannelOrgLoader(Long orgTreeId,Long orgId);

    /**
     * 判断渠道数据有效性
     * @param channelOrgVo
     * @return
     */
    public String JudgeChannelOrgParams(ChannelOrgVo channelOrgVo);

    /**
     * 渠道数据保存
     * @param channelOrgVo
     * @return
     */
    public void addChannelOrg(ChannelOrgVo channelOrgVo);

    /**
     * 组织新增同步
     * @param orgTreeIds
     * @param orgId
     * @param orgParentId
     * @param userId
     * @param batchNumber
     */
    public void orgAddSync(List<Long> orgTreeIds,Long orgId,Long orgParentId,Long userId,String batchNumber);

    /**
     * 游离新增同步
     * @param orgTreeIds
     * @param org
     * @param userId
     * @param batchNumber
     */
    public void freeOrgAddSync(List<Long> orgTreeIds,OrgVo org,Long userId,String batchNumber);
    /**
     * 组织更新同步
     * @param orgTreeIds
     * @param org
     * @param userId
     * @param batchNumber
     */
    public void orgUpdateSync(List<Long> orgTreeIds,OrgVo org,Long userId,String batchNumber);

    /**
     * 删除同步
     * @param orgTreeIds
     * @param org
     * @param userId
     * @param batchNumber
     */
    public void orgDelSync(List<Long> orgTreeIds,OrgVo org,Long userId,String batchNumber);

    /**
     * excel导入移动
     * @param orgTreeIds
     * @param orgId
     * @param orgParentId
     * @param userId
     * @param batchNumber
     */
    public void orgExcelMoveSync(List<Long> orgTreeIds,Long orgId,Long orgParentId,Long userId,String batchNumber);

}

