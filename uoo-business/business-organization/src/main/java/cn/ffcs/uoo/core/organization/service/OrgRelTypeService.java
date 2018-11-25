package cn.ffcs.uoo.core.organization.service;

import cn.ffcs.uoo.core.organization.entity.OrgRelType;
import cn.ffcs.uoo.core.organization.vo.OrgRefTypeVo;
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
public interface OrgRelTypeService extends IService<OrgRelType> {

    public Long getId();

    public List<OrgRelType> getOrgRelType(String orgTreeId);

    public List<TreeNodeVo> selectOrgRelTypeTree(String refCode);

}
