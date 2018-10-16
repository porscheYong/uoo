package cn.ffcs.uoo.core.user.dao;

import cn.ffcs.uoo.core.user.entity.TbAcctOrg;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 主账号与组织关系 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
@Component
public interface TbAcctOrgMapper extends BaseMapper<TbAcctOrg> {

    /**
     * 插入一条记录
     * @param tbAcctOrg
     * @return
     */
    long save(TbAcctOrg tbAcctOrg);

    /**
     * 失效一条记录
     * @param tbAcctOrg
     */
    void delete(TbAcctOrg tbAcctOrg);

}
