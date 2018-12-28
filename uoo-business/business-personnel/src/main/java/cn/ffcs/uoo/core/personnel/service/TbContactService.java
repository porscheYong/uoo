package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbContact;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 联系方式 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-13
 */
public interface TbContactService extends IService<TbContact> {
    public Long getId();

    public void delete(TbContact tbContact);

    /**
     * 根据personnelId获取 联系信息
     * @param personnelId
     * @return
     */
    public List<TbContact> getTbContactByPsnId(Long personnelId, String contactType);

    /**
     * 根据personnelId 删除 联系信息
     * @param personnelId
     * @param userId
     * @return
     */
    public Object delTbContactByPsnId(Long personnelId, Long userId);

    /**
     * 联系方式 新增或修改
     * @param tbContactList
     * @param personnelId
     * @param contactType
     * @return
     */
    public Object addOrUpdateTbContact(List<TbContact> tbContactList, Long personnelId, String contactType, Long userId);

}
