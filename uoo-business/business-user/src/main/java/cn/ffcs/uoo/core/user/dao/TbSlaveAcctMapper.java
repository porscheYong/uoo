package cn.ffcs.uoo.core.user.dao;

import cn.ffcs.uoo.core.user.entity.ListUser;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctOrgVo;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 从账号 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbSlaveAcctMapper extends BaseMapper<TbSlaveAcct> {

    public Long getId();

    public List<ListUser> getUserList(Long slaveAcctId);

    public List<ListUser> getApplyUserList(Long slaveAcctId);

    public List<TbAcct> getAcct(Long slaveAcctId);


    // todo -----新版本--------------------------------------------------------

    public List<ListSlaveAcctOrgVo> getSlaveAcctOrg(Pagination page,@Param("slaveAcctOrgVo") ListSlaveAcctOrgVo slaveAcctOrgVo, @Param("inSql") String inSql);

}
