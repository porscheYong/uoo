package cn.ffcs.uoo.core.resource.dao;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.core.resource.entity.ModifyHistory;
import cn.ffcs.uoo.core.resource.vo.ModifyHistoryDTO;

/**
 * <p>
 * 是否为每张业务表设计历史流水记录表？？ Mapper 接口
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-24
 */
public interface ModifyHistoryMapper extends BaseMapper<ModifyHistory> {
    public List<String> getOrgNamesByAccout(String accout);
    public Long getId();

    public Long getCommonTableId(String tableName);

    public String getSeqBatchNumber();
    public List<ModifyHistoryDTO> selectPageDTO(Page<ModifyHistoryDTO> page,HashMap<String,Object> map);
}
