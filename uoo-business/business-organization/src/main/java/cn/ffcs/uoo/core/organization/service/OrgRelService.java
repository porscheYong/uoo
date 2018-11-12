package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.Org;
import cn.ffcs.uoo.core.organization.entity.OrgRel;
import cn.ffcs.uoo.core.organization.entity.OrgRelType;
import cn.ffcs.uoo.core.organization.entity.OrgTree;
import cn.ffcs.uoo.core.organization.util.ResponseResult;
import cn.ffcs.uoo.core.organization.vo.OrgRefTypeVo;
import cn.ffcs.uoo.core.organization.vo.OrgVo;
import cn.ffcs.uoo.core.organization.vo.PsonOrgVo;
import cn.ffcs.uoo.core.organization.vo.TreeNodeVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
public interface OrgRelService extends IService<OrgRel> {

        /**
         * 获取seq
         * @return
         */
        public Long getId();

        /**
         * 失效状态
         * @param orgRel
         */
        public void delete(OrgRel orgRel);



        public void add(OrgRel orgRel);



        public void update(OrgRel orgRel);
        /**
         * 查询组织关系是否存在
         * @param org
         * @return
         */
        public List<OrgRel> selectOrgRel(Org org);

        /**
         * 检索组织信息
         * @param search
         * @param orgRootId
         * @return
         */
        public List<Org> selectFuzzyOrgRel(String search, String orgRootId);

        /**
         * 检索组织数量
         * @param search
         * @param orgRootId
         * @return
         */
        public int selectFuzzyOrgRelCount(String search, String orgRootId);

        /**
         * 组织检索翻页
         * @param orgVo
         * @return
         */
        public Page<OrgVo> selectFuzzyOrgRelPage(OrgVo orgVo);

        /**
         * 查询检索的组织树信息
         * @param orgleafId
         * @param orgRootId
         * @param isFull
         * @return
         */
        public List<TreeNodeVo> selectFuzzyOrgRelTree(String orgleafId,String orgRootId,boolean isFull);


        /**
         * 组织树查询
         * @param orgRootId
         * @param refCode
         * @param pid
         * @param isRoot
         * @return
         */
        public List<TreeNodeVo> queryOrgTree(String orgRootId,String refCode,String pid,boolean isRoot);

        /**
         * 获取组织关系类型
         * @param org
         * @return
         */
        public List<OrgRefTypeVo> getOrgRelType(Org org);


        /**
         * 获取组织类型分页
         */
        public Page<OrgRefTypeVo> selectOrgRelTypePage(OrgRefTypeVo orgRefTypeVo);

        /**
         * 获取树的组织关系
         */
        public List<OrgRel> getOrgRel(String orgTreeId,String orgId);

        /**
         * 获取指定组织树和层级
         * @param orgRootId
         * @param lv
         * @param curOrgId
         * @param isFull
         * @return
         */
        public List<TreeNodeVo> selectTarOrgRelTreeAndLv(String orgRootId, String lv, String curOrgId, boolean isFull);

}
