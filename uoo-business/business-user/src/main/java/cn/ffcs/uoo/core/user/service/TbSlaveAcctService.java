package cn.ffcs.uoo.core.user.service;

import cn.ffcs.uoo.core.user.entity.ListUser;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.entity.TbSlaveAcct;
import cn.ffcs.uoo.core.user.vo.EditFormSlaveAcctVo;
import cn.ffcs.uoo.core.user.vo.ListSlaveAcctOrgVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 从账号 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-14
 */
public interface TbSlaveAcctService extends IService<TbSlaveAcct> {

    public Long getId();

    //从账号(资源)关联用户信息
    public List<ListUser> getUserList(Long slaveAcctId);

    //从账号(应用)关联用户信息
    public List<ListUser> getApplyUserList(Long slaveAcctId);

    //从账号(应用)关联主账号
    public List<TbAcct> getAcct(Long slaveAcctId);

    // todo ----新版本---------------------------------------------------------------------

    /**
     * 从账号关联 主账号组织关系
     * @param slaveAcctOrgVo
     * @param account
     * @return
     */
    public Page<ListSlaveAcctOrgVo> getSlaveAcctOrg(ListSlaveAcctOrgVo slaveAcctOrgVo, String account);

    /**
     * 保存 从账号
     * @param tbSlaveAcct
     * @return
     */
    public Object addTbSlaveAcct(TbSlaveAcct tbSlaveAcct);

    /**
     * 验证 从账号是否已存在
     * @param slaveAcct
     * @param resourceObjId
     * @param slaveAcctId
     * @return
     */
    public boolean checkSlaveAcct(String slaveAcct, Long resourceObjId, Long slaveAcctId);

    /**
     * 删除 从账号
     * @param slaveAcctId
     * @param userId
     * @return
     */
    public Object delTbSlaveAcct(Long slaveAcctId, Long userId);

    /**
     * 更新 从账号
     * @param tbSlaveAcct
     * @return
     */
    public Object updateTbSlaveAcct(TbSlaveAcct tbSlaveAcct);

    /**
     * 删除 从账号关联新
     * @param slaveAcctId
     * @param userId
     * @return
     */
    public Object delAllTbSlaveAcct(Long slaveAcctId, Long userId);

    /**
     * 新增 更新 从账号
     * @param editFormSlaveAcctVo
     * @param slaveAcctId
     * @return
     */
    public Object insertOrUpdateTbSlaveAcct(EditFormSlaveAcctVo editFormSlaveAcctVo, Long slaveAcctId);



}
