package cn.ffcs.uoo.core.organization.dao;

import cn.ffcs.uoo.core.organization.entity.ModifyHistory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 是否为每张业务表设计历史流水记录表？？ Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-24
 */
public interface ModifyHistoryMapper extends BaseMapper<ModifyHistory> {

    public Long getId();

    public Long getCommonTableId(@Param("tableName") String tableName);

    public Long getSeqBatchNumber();
}
