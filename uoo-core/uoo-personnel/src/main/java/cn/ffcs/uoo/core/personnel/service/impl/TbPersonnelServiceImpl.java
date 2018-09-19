package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.dao.TbPersonnelMapper;
import cn.ffcs.uoo.core.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.core.personnel.service.TbPersonnelService;
import cn.ffcs.uoo.core.personnel.vo.PersonnelRelationInfoVo;
import cn.ffcs.uoo.core.personnel.vo.TbPersonnelVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-09-06
 */
@Service
public class TbPersonnelServiceImpl extends ServiceImpl<TbPersonnelMapper, TbPersonnel> implements TbPersonnelService {
    @Autowired
    private TbPersonnelMapper tbPersonnelMapper;

    @Override
    public Page<PersonnelRelationInfoVo> getPersonnelRelationInfo(TbPersonnelVo tbPersonnelVo) {
        List<PersonnelRelationInfoVo> list = tbPersonnelMapper.getPersonnelRelationInfo(tbPersonnelVo);
        int pageSize = tbPersonnelVo.getPageSize() == 0?12:tbPersonnelVo.getPageSize();
        Page<PersonnelRelationInfoVo> page = new Page<PersonnelRelationInfoVo>(tbPersonnelVo.getPageNo(),pageSize);
        page.setRecords(list);
        return page;
    }
}
