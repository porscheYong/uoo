package cn.ffcs.uoo.web.maindata.permission.service;

import cn.ffcs.uoo.core.permission.entity.FuncMenu;
import cn.ffcs.uoo.core.permission.vo.MenuVO;
import cn.ffcs.uoo.core.permission.vo.TreeNodeVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 功能模块和功能模块之间存在上下级关系，一个功能模块可以有多个下级功能模块，一个功能模块只能属于一个上级功能模块。例如：一级菜单功能“订单管理”下挂二级菜单功能“订单受理”、“订单查询”、“订单历史”。 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
public interface FuncMenuService extends IService<FuncMenu> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param funcMenu
     */
    public void delete(FuncMenu funcMenu);

    /**
     * 新增
     */
    public void add(FuncMenu funcMenu);

    /**
     * 更新
     */
    public void update(FuncMenu funcMenu);

    /**
     * 查询菜单树
     */
    public List<TreeNodeVo> selectFuncMenuAsyncTree(String id);

    /**
     * 同步树
     * @return
     */
    public List<TreeNodeVo> selectFuncMenuSyncTree();

    /**
     *
     * @param menuVo
     * @return
     */
    public Page<MenuVO> selectMenuPage(MenuVO menuVo);

}
