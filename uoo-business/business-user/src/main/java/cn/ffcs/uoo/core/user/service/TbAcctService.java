package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbRoles;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 主账号 服务类
 * </p>
 *
 * @author wudj
 * @since 2018-10-24
 */
public interface TbAcctService extends IService<TbAcct> {

    public Long getId();

    /**
     * 保存主账号
     * @param tbAcct
     */
    long saveAcct(TbAcct tbAcct);

    /**
     * 失效一条数据
     * @param tbAcct
     */
    void removeAcct(TbAcct tbAcct);

    List<TbAcct> selectAcctList(TbAcct tbAcct);


    // todo ---新版本-------------------------------------------------

    /**
     * 角色
     * @param acctType
     * @param acctId
     * @return
     */
    public List<TbRoles> getTbRoles(Long acctType, Long acctId);

    /**
     * 根据Id删除账号
     * @param acctId
     * @return
     */
    public Object removeTbAcct(Long acctId);

    /**
     * 根据 personelId 获取主账号信息
     * @param personelId
     * @return
     */
    public Object getTbAcctByPsnId(Long personelId);
}
