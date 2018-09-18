package cn.ffcs.uoo.core.personnel.dao;


import cn.ffcs.uoo.core.personnel.entity.TbAcct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

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
}
