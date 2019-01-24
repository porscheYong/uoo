package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbAcctExt;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 主账号扩展 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbAcctExtService extends IService<TbAcctExt> {

    void removeAcctExt(TbAcctExt tbAcctExt);

    //** todo --新版本--------------------------------

    Long getId();

    /**
     *  从账号对应扩展属性
     * @param tbAcctExt
     * @return
     */
    public TbAcctExt saveTbAcctExt(TbAcctExt tbAcctExt);

    /**
     * 根据slaveAcctId 删除 扩展属性
     * @param slaveAcctId
     * @param userId
     * @return
     */
    public Object delTbAcctExt(Long slaveAcctId, Long userId);

    /**
     * 扩展属性 验证
     * @param tbAcctExt
     * @return
     */
    public Object checkAcctExt(TbAcctExt tbAcctExt);


}
