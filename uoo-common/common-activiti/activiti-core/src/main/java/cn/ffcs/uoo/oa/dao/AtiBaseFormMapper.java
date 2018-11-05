package cn.ffcs.uoo.oa.dao;

import cn.ffcs.uoo.oa.entity.AtiBaseForm;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 通用表单Mapper接口
 * Created by liuxiaodong on 2018/10/8.
 */
public interface AtiBaseFormMapper extends BaseMapper<AtiBaseForm> {

    /**
     * 添加一条记录
     * @param atiBaseForm 通用表单对象
     */
    void addAtiBaseForm(AtiBaseForm atiBaseForm);

    /**
     * 根据流程实例获取流程通用表单
     * @param procInstId 流程实例ID
     * @return 通用表单对象
     */
    AtiBaseForm getBaseFormByProcInstId(String procInstId);

    /**
     * 更新ati_base_form 流程实例id
     * @param procInstId 流程实例id
     * @param atiBaseFormId 主键id
     */
    void updateProcInstIdByBaseFormId(@Param("procInstId") String procInstId,
                                      @Param("atiBaseFormId") Long atiBaseFormId);

}
