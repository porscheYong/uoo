package cn.ffcs.uoo.core.user.dao;

import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 主账号扩展 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Component
public interface TbAcctExtMapper extends BaseMapper<TbAcctExt> {

    /**
     * 失效一条记录
     * @param tbAcctExt
     */
    void delete(TbAcctExt tbAcctExt);

    Long getId();
}
