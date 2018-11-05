package cn.ffcs.uoo.core.user.dao;


import cn.ffcs.uoo.core.user.entity.TbAcct;
import com.baomidou.mybatisplus.mapper.BaseMapper;

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
}
