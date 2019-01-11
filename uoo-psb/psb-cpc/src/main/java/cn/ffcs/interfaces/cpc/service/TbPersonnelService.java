package cn.ffcs.interfaces.cpc.service;

import cn.ffcs.interfaces.cpc.pojo.TbPersonnel;
import com.baomidou.mybatisplus.service.IService;

/**
 * Created by liuxiaodong on 2019/1/10.
 */
public interface TbPersonnelService extends IService<TbPersonnel> {
    /**
     * 插入人员信息
     * @param tbPersonnel
     */
    void insertValueOfPersonnel(TbPersonnel tbPersonnel);

}
