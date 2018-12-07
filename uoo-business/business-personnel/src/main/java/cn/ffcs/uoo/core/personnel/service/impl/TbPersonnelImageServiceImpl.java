package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.personnel.constant.EumPersonnelResponseCode;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnelImage;
import cn.ffcs.uoo.core.personnel.dao.TbPersonnelImageMapper;
import cn.ffcs.uoo.core.personnel.entity.TbPsnjob;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelImageService;
import cn.ffcs.uoo.core.personnel.util.ResultUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wudj
 * @since 2018-11-07
 */
@Service
public class TbPersonnelImageServiceImpl extends ServiceImpl<TbPersonnelImageMapper, TbPersonnelImage> implements TbPersonnelImageService {

    @Override
    public Long getId() {
        return baseMapper.getId();
    }

    @Override
    public void save(TbPersonnelImage tbPersonnelImage) {
        baseMapper.save(tbPersonnelImage);
    }

    @Override
    public Object delTbPersonnelImageByPsnId(Long personnelId){
        TbPersonnelImage tbPersonnelImage = new TbPersonnelImage();
        tbPersonnelImage.setStatusCd(BaseUnitConstants.ENTT_STATE_INACTIVE);
        tbPersonnelImage.setStatusDate(new Date());
        EntityWrapper<TbPersonnelImage> wrapper = new EntityWrapper<TbPersonnelImage>();
        wrapper.eq(BaseUnitConstants.TABLE_CLOUMN_STATUS_CD, BaseUnitConstants.ENTT_STATE_ACTIVE);
        wrapper.eq(BaseUnitConstants.TBPERSONNEL_PERSONNEL_ID, personnelId);
        if(retBool(baseMapper.update(tbPersonnelImage, wrapper))){
            return ResultUtils.success(null);
        }
        return ResultUtils.error(EumPersonnelResponseCode.PERSONNEL_RESPONSE_ERROR);
    }
}
