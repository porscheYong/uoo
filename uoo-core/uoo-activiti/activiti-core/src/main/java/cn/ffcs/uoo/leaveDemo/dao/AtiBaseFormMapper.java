package cn.ffcs.uoo.leaveDemo.dao;

import cn.ffcs.uoo.leaveDemo.entity.AtiBaseForm;
import cn.ffcs.uoo.leaveDemo.vo.UeccVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 通用表单Mapper接口
 * Created by liuxiaodong on 2018/10/8.
 */
public interface AtiBaseFormMapper extends BaseMapper<AtiBaseForm> {

    /**
     * 添加一条记录
     * @param atiBaseForm
     */
    void addAtiBaseForm(AtiBaseForm atiBaseForm);

    /**
     * 更新ati_base_form 流程实例id
     * @param procInstId 流程实例id
     * @param atiBaseFormId 主键id
     */
    void updateProcInstIdByBaseFormId(@Param("procInstId") String procInstId,
                                      @Param("atiBaseFormId") Long atiBaseFormId);

}
