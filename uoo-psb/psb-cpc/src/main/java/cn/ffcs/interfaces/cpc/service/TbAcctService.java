package cn.ffcs.interfaces.cpc.service;

import cn.ffcs.interfaces.cpc.pojo.TbAcct;
import com.baomidou.mybatisplus.service.IService;

/**
 * Created by liuxiaodong on 2019/1/11.
 */
public interface TbAcctService extends IService<TbAcct> {
    TbAcct selectByPersonnelId(Long personnelId);
}
