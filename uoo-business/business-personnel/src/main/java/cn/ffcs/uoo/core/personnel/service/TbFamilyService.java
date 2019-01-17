package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbFamily;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
public interface TbFamilyService extends IService<TbFamily> {

    /**
     * 新增 保存
     * @param tbFamily
     */
    public void save(TbFamily tbFamily);

    public Long getId();

    public void delete(TbFamily tbFamily);

    /**
     * 新增家庭成员信息
     * @param tbFamily
     * @return
     */
    public Object saveTbFamily(TbFamily tbFamily);

    /**
     * 更新家庭成员信息
     * @param tbFamily
     * @return
     */
    public Object updateTbFamily(TbFamily tbFamily);

    /**
     * 删除家庭成员信息
     * @param familyId
     * @param userId
     * @return
     */
    public Object delTbFamily(Long familyId, Long userId);

    /**
     * 根据ID获取家庭成员信息
     * @param familyId
     * @return
     */
    public Object getTbFamilyById(Long familyId);

    /**
     * 根据personnelId 删除 家庭成员信息
     * @param personnelId
     * @param userId
     * @return
     */
    public Object delTbFamilyByPsnId(Long personnelId, Long userId);

    /**
     * 家庭成员分页查询
     * @param personnelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Object getTbFamilyPage(Long personnelId, Integer pageNo, Integer pageSize);

    /**
     * personnelId 获取 家庭成员
     * @param personnelId
     * @return
     */
    public Page<TbFamily> getTbFamilyPage(Long personnelId);
}
