package cn.ffcs.uoo.system.dao;


import cn.ffcs.uoo.system.entity.SysOrganization;
import cn.ffcs.uoo.system.vo.TreeNodeVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
public interface SysOrganizationMapper extends BaseMapper<SysOrganization> {

    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 获取根节点
     * @return
     */
    public List<TreeNodeVo> getTreeRoot();

    /**
     * 获取子节点
     * @param id
     * @return
     */
    public List<TreeNodeVo> getTreeChild(String id);


}
