package cn.ffcs.uoo.core.resource.dao;

import cn.ffcs.uoo.core.resource.entity.ModifyHistory;
import com.baomidou.mybatisplus.mapper.BaseMapper;

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

    public Long getCommonTableId(String tableName);
}
