package cn.ffcs.uoo.core.permission.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.FuncMenuMapper;
import cn.ffcs.uoo.core.permission.entity.FuncMenu;
import cn.ffcs.uoo.core.permission.service.FuncMenuService;
import cn.ffcs.uoo.core.permission.vo.MenuVO;
import cn.ffcs.uoo.core.permission.vo.TreeNodeVo;

/**
 * <p>
 * 功能模块和功能模块之间存在上下级关系，一个功能模块可以有多个下级功能模块，一个功能模块只能属于一个上级功能模块。例如：一级菜单功能“订单管理”下挂二级菜单功能“订单受理”、“订单查询”、“订单历史”。 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@Service
public class FuncMenuServiceImpl extends ServiceImpl<FuncMenuMapper, FuncMenu> implements FuncMenuService {
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    /**
     * 失效状态
     * @param funcMenu
     */
    @Override
    public void delete(FuncMenu funcMenu){
        funcMenu.setStatusCd("1100");
        funcMenu.setStatusDate(new Date());
        funcMenu.setUpdateDate(new Date());
        funcMenu.setUpdateUser(0L);
        updateById(funcMenu);
    }



    /**
     * 新增
     */
    @Override
    public void add(FuncMenu funcMenu){
        funcMenu.setCreateDate(new Date());
        funcMenu.setCreateUser(0L);
        funcMenu.setStatusCd("1000");
        funcMenu.setStatusDate(new Date());
        insert(funcMenu);
    }

    /**
     * 更新
     */
    @Override
    public void update(FuncMenu funcMenu){
        funcMenu.setUpdateDate(new Date());
        funcMenu.setUpdateUser(0L);
        funcMenu.setStatusDate(new Date());
        updateById(funcMenu);
    }

    /**
     * 查询菜单树
     * @param id
     * @return
     */
    @Override
    public List<TreeNodeVo> selectFuncMenuAsyncTree(String id){
        return baseMapper.selectFuncMenuTree(id);
    }

    @Override
    public List<TreeNodeVo> selectFuncMenuSyncTree(){
        return baseMapper.selectFuncMenuSyncTree();
    }

    /**
     * 子菜单翻页
     * @param menuVo
     * @return
     */
    @Override
    public Page<MenuVO> selectMenuPage(MenuVO menuVo){
        Page<MenuVO> page = new Page<>(menuVo.getPageNo()==0?1:menuVo.getPageNo(),
                menuVo.getPageSize()==0?10:menuVo.getPageNo());
        List<MenuVO> menuVolist = baseMapper.selectMenuPage(page,menuVo);
        page.setRecords(menuVolist);
        return page;
    }


}
