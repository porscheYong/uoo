package cn.ffcs.uoo.core.permission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import cn.ffcs.uoo.core.permission.entity.FuncMenu;
import cn.ffcs.uoo.core.permission.vo.MenuVO;
import cn.ffcs.uoo.core.permission.vo.TreeNodeVo;

/**
 * <p>
 * 功能模块和功能模块之间存在上下级关系，一个功能模块可以有多个下级功能模块，一个功能模块只能属于一个上级功能模块。例如：一级菜单功能“订单管理”下挂二级菜单功能“订单受理”、“订单查询”、“订单历史”。 Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
public interface FuncMenuMapper extends BaseMapper<FuncMenu> {

    public Long getId();

    public List<TreeNodeVo> selectFuncMenuTree(@Param("id") String id);

    public List<TreeNodeVo> selectFuncMenuSyncTree();

    public List<MenuVO> selectMenuPage(Pagination page, @Param("menuVO")MenuVO menuVO);

}
