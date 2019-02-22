package cn.ffcs.uoo.core.resource.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.core.resource.entity.ModifyHistory;
import cn.ffcs.uoo.core.resource.vo.ModifyHistoryDTO;

/**
 * <p>
 * 是否为每张业务表设计历史流水记录表？？ 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-24
 */
public interface ModifyHistoryService extends IService<ModifyHistory> {

    public Long getId();
    public List<String> getOrgNamesByAccout(String accout);
    public void delete(ModifyHistory modifyHistory);

    public void add(ModifyHistory modifyHistory);

    public void update(ModifyHistory modifyHistory);

    public Long getCommonTableId(String tableName);
    /**
     * 日志变化保存
     * @param oldObj
     * @param newObj
     * @return
     */
    public String addModifyHistory(Object oldObj,Object newObj);

    /**
     * 获取批次号
     * @return
     */
    public String getBatchNumber();
    public List<ModifyHistoryDTO> selectPageDTO(Page<ModifyHistoryDTO> page,long tableId,long recordId);
}
