package cn.ffcs.uoo.leaveDemo.service;

import cn.ffcs.uoo.leaveDemo.entity.AtiBaseForm;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * 通用表单服务类
 * Created by liuxiaodong on 2018/10/8.
 */
public interface AtiBaseFormService extends IService<AtiBaseForm> {

    void addAtiBaseForm(AtiBaseForm atiBaseForm);

    void updateProcInstIdByBaseFormId(@Param("procInstId") String procInstId,
                                      @Param("atiBaseFormId") Long atiBaseFormId);
}
