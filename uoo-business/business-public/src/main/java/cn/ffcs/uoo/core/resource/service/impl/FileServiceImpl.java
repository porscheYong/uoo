package cn.ffcs.uoo.core.resource.service.impl;

import cn.ffcs.uoo.core.resource.entity.File;
import cn.ffcs.uoo.core.resource.dao.FileMapper;
import cn.ffcs.uoo.core.resource.service.IFileService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 
 * </p>
 *
 * @author zengxsh
 * @since 2019-01-15
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {
    @Override
    public Integer getId(){
        return baseMapper.getId();
    }
}
