package cn.ffcs.uoo.core.personnel.service.impl;

import cn.ffcs.uoo.core.personnel.dao.TbContactMapper;
import cn.ffcs.uoo.core.personnel.entity.TbContact;
import cn.ffcs.uoo.core.personnel.service.TbContactService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 联系方式 服务实现类
 * </p>
 *
 * @author zhanglu
 * @since 2018-09-13
 */
@Service
public class TbContactServiceImpl extends ServiceImpl<TbContactMapper, TbContact> implements TbContactService {
    @Autowired
    private TbContactMapper tbContactMapper;

    @Override
    public Long getId() {
        return tbContactMapper.getId();
    }

    @Override
    public void delete(TbContact tbContact) {
        tbContactMapper.delete(tbContact);
    }
}
