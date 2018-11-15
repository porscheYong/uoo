package cn.ffcs.uoo.core.user.dao;


import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbRoles;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 主账号 Mapper 接口
 * </p>
 *
 * @author wudj
 * @since 2018-10-26
 */

public interface TbAcctMapper extends BaseMapper<TbAcct> {

    public Long getId();

//    /**
//     * 插入一条记录
//     * @param tbAcct
//     * @return
//     */
//   public long save(TbAcct tbAcct);

    /**
     * 失效一条记录
     * @param tbAcct
     */
    public void delete(TbAcct tbAcct);

    public List<TbAcct> selectAcctList(TbAcct tbAcct);

    // todo--新版本-----------------------------------------------------------

    public List<TbRoles> getTbRoles(@Param("acctType") Long acctType,@Param("acctId") Long acctId);

}
