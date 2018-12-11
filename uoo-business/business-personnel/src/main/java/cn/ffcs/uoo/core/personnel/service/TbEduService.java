package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbEdu;
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
public interface TbEduService extends IService<TbEdu> {

    public Long getId();

    public void delete(TbEdu tbEdu);

    /**
     * 新增教育信息
     * @param tbEdu
     * @return
     */
    public Object saveTbEdu(TbEdu tbEdu);

    /**
     * 更新教育信息
     * @param tbEdu
     * @return
     */
    public Object updateTbEdu(TbEdu tbEdu);

    /**
     * 删除教育信息
     * @param eduId
     * @return
     */
    public Object delTbEdu(Long eduId);

    /**
     * 根据ID获取教育信息
     * @param eduId
     * @return
     */
    public Object getTbEduById(Long eduId);

    /**
     * 根据personnelId 删除 教育信息
     * @param personnelId
     * @return
     */
    public Object delTbEduByPsnId(Long personnelId);

    /**
     * 工作履历分页查询
     * @param personnelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Object getTbEduPage(Long personnelId, Integer pageNo, Integer pageSize);

    /**
     * personnelId 查询
     * @param personnelId
     * @return
     */
    public Page<TbEdu> getTbEduPage(Long personnelId);

}
