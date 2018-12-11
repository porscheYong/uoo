package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbPsnjob;
import cn.ffcs.uoo.core.personnel.vo.TbPsnjobVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
public interface TbPsnjobService extends IService<TbPsnjob> {

    public Long getId();

    public void delete(TbPsnjob tbPsnjob);

    /**
     * 新增工作履历新
     * @param tbPsnjob
     * @return
     */
    public Object saveTbPsnjob(TbPsnjob tbPsnjob);

    /**
     * 更新工作履历
     * @param tbPsnjob
     * @return
     */
    public Object updateTbPsnjob(TbPsnjob tbPsnjob);

    /**
     * 删除工作履历
     * @param psnjobId
     * @return
     */
    public Object delTbPsnjob(Long psnjobId);

    /**
     * 根据ID获取工作履历信息
     * @param psnjobId
     * @return
     */
    public Object getTbPsnjobById(Long psnjobId);

    /**
     * 人员工作履历列表
     * @param personnelId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Object selectTbPsnjobPage(Long personnelId, Integer pageNo, Integer pageSize);

    /**
     * 根据personnelId 删除 工作履历信息
     * @param personnelId
     * @return
     */
    public Object delTbPsnjobByPsnId(Long personnelId);

    /**
     * 根据personnelId 查询 工作履历信息
     * @param personnelId
     * @return
     */
    public Page<TbPsnjobVo> getPsnjobPageBypsnId(Long personnelId, Integer pageNo, Integer pageSize);

}
