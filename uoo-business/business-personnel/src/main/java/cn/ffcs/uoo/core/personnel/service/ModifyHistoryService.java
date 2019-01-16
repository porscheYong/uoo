package cn.ffcs.uoo.core.personnel.service;


import cn.ffcs.uoo.core.personnel.entity.ModifyHistory;
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
     * @param userId
     * @param batchNum
     * @return
     */
    public String addModifyHistory(Object oldObj, Object newObj, Long userId, String batchNum);


    /**
     * 保存日志 增和删
     * @param oper
     * @param filedName
     * @param filedValue
     * @param userId
     * @param batchNum
     * @return
     */
    public String addModifyHistory(Object obj, String oper, Long userId, String filedName,String filedValue, String batchNum);

    /**
     * 获取批次号
     * @return
     */
    public String getBatchNumber();

    /**
     * 新增 保存日志
     * @param newObj
     * @param userId
     * @param batchNum
     * @return
     */
    public String insertModifyHistory(Object newObj, Long userId, String batchNum);

    /**
     *修改 保存日志
     * @param oldObj
     * @param newObj
     * @param userId
     * @param batchNum
     * @return
     */
    public String updateModifyHistory(Object oldObj, Object newObj, Long userId, String batchNum);

    /**
     * 删除 保存日志
     * @param oldObj
     * @param userId
     * @param batchNum
     * @return
     */
    public String deleteModifyHistory(Object oldObj, Long userId, String batchNum);

}
