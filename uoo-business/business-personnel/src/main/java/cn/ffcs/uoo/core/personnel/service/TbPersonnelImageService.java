package cn.ffcs.uoo.core.personnel.service;

import cn.ffcs.uoo.core.personnel.entity.TbPersonnelImage;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wudj
 * @since 2018-11-07
 */
public interface TbPersonnelImageService extends IService<TbPersonnelImage> {

    /**
     * 主键
     * @return
     */
    public Long getId();

    /**
     * 人员图片上传插入
     * @param tbPersonnelImage
     */
    public void save(TbPersonnelImage tbPersonnelImage);

    /**
     * 根据personnelId 删除 图片信息
     * @param personnelId
     * @param userId
     * @return
     */
    public Object delTbPersonnelImageByPsnId(Long personnelId, Long userId);

    /**
     * 根据 personnelId 获取图片细信息
     * @param personnelId
     * @return
     */
    public Object getTbPsnImageByPsnId(Long personnelId);

    /**
     * 更新personnelId
     * @param personnelId
     * @param psnImageId
     * @return
     */
    public Object updatePsnId(Long personnelId, Long psnImageId, Long userId);

}
