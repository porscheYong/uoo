package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgType;
import cn.ffcs.uoo.core.organization.entity.OrgOrgtypeRel;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OrgTypeService extends IService<OrgType> {
    /**
     * 查询组织类别列表
     * @param orgId
     * @return
     */
    public List<OrgType> getOrgTypeByOrgId(Long orgId);

    /**
     * 查询组织类别列表
     * @param orgTreeId
     * @return
     */
    public List<OrgType> getOrgTypeByOrgTreeId(Long orgTreeId);


    /**
     * 查询组织类别树
     * @param orgTypeId
     * @param orgTypeCode
     * @return
     */
    public List<TreeNodeVo> selectOrgTypeTree(String orgTypeId,String orgTypeCode);


    /**
     * 查询组织类别树
     * @param orgTypeId
     * @param orgTypeCode
     * @return
     */
    public List<TreeNodeVo> selectFullOrgTypeTreeByOrgId(String orgTypeId,String orgTypeCode,String orgId);

    /**
     * 是否存在子节点
     * @param treeNodeVo
     * @return
     */
    public boolean isLeaf(TreeNodeVo treeNodeVo);

    /**
     * 获取组织类别信息
     * @param orgId
     * @return
     */
    public String getOrgTypeInfoByOrgId(String orgId);


    /**
     * 判断是否是营销类别
     */
    public boolean isYxsxType(String orgTypeId);

}
