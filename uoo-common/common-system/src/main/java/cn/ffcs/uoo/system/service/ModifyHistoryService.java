package cn.ffcs.uoo.system.service;

import cn.ffcs.uoo.system.entity.ModifyHistory;
import com.baomidou.mybatisplus.service.IService;

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
    public String addModifyHistory(Object oldObj, Object newObj);

    /**
     * 日志变化保存
     * @param oldObj
     * @param newObj
     * @param userId
     * @return
     */
    public String addModifyHistory(Object oldObj, Object newObj, Long userId, String batchNumber);


    /**
     * 保存日志 增和删
     * @param tabName
     * @param oper
     * @param pkId
     * @param filedName
     * @param filedValue
     * @param batchNum
     * @param userId
     * @return
     */
    public String addModifyHistory(String tabName, String oper, String pkId, String filedName, String filedValue, String batchNum, Long userId);

    /**
     * 获取批次号
     * @return
     */
    public String getBatchNumber();

}
