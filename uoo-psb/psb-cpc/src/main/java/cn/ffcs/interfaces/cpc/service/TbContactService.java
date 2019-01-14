package cn.ffcs.interfaces.cpc.service;

import cn.ffcs.interfaces.cpc.pojo.TbContact;
import com.baomidou.mybatisplus.service.IService;

/**
 * Created by liuxiaodong on 2019/1/11.
 */
public interface TbContactService extends IService<TbContact> {
    boolean deleteByPersonnelId(Long personnelId);
}
