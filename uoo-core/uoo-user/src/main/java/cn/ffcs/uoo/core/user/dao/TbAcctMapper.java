package cn.ffcs.uoo.core.user.dao;


import cn.ffcs.uoo.core.user.entity.TbAcct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 主账号 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Component
public interface TbAcctMapper extends BaseMapper<TbAcct> {

    /**
     * 插入一条记录
     * @param tbAcct
     * @return
     */
    long save(TbAcct tbAcct);

    /**
     * 失效一条记录
     * @param tbAcct
     */
    void delete(TbAcct tbAcct);

    List<TbAcct> selectAcctList(TbAcct tbAcct);
}
