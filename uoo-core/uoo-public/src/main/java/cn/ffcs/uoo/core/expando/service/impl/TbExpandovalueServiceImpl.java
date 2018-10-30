package cn.ffcs.uoo.core.expando.service.impl;

import cn.ffcs.uoo.base.common.tool.util.DateUtils;
import cn.ffcs.uoo.core.expando.entity.TbExpandovalue;
import cn.ffcs.uoo.core.expando.dao.TbExpandovalueMapper;
import cn.ffcs.uoo.core.expando.service.TbExpandovalueService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 扩展值 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
@Service
public class TbExpandovalueServiceImpl extends ServiceImpl<TbExpandovalueMapper, TbExpandovalue> implements TbExpandovalueService {

    @Override
    public int save(TbExpandovalue tbExpandovalue) {
        return baseMapper.save(tbExpandovalue);
    }

    @Override
    public void remove(Long valueId, Long updateUser) {
        TbExpandovalue tbExpandovalue = new TbExpandovalue();
        tbExpandovalue.setValueId(valueId);
        // 失效状态
        tbExpandovalue.setStatusCd("1100");
        tbExpandovalue.setUpdateDate(DateUtils.parseDate(DateUtils.getDateTime()));
        tbExpandovalue.setUpdateUser(updateUser);
        tbExpandovalue.setStatusDate(DateUtils.parseDate(DateUtils.getDateTime()));
        baseMapper.remove(tbExpandovalue);
    }

    @Override
    public List<TbExpandovalue> selectValueList(TbExpandovalue tbExpandovalue) {
        return baseMapper.selectValueList(tbExpandovalue);
    }
}
