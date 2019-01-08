package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysFile;
import cn.ffcs.uoo.system.dao.SysFileMapper;
import cn.ffcs.uoo.system.service.ISysFileService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.SysFileVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {


    /**
     * 获取seq
     * @return
     */
    @Override
    public Long getId(){
        return baseMapper.getId();
    }

    /**
     * 失效状态
     * @param sysFile
     */
    @Override
    public void delete(SysFile sysFile){
        sysFile.setStatusCd("1100");
        sysFile.setStatusDate(new Date());
        sysFile.setUpdateDate(new Date());
        sysFile.setUpdateUser(0L);
        updateById(sysFile);
    }



    /**
     * 新增
     */
    @Override
    public void add(SysFile sysFile){
        sysFile.setCreateDate(new Date());
        sysFile.setCreateUser(0L);
        sysFile.setStatusCd("1000");
        sysFile.setStatusDate(new Date());
        insert(sysFile);
    }

    /**
     * 更新
     */
    @Override
    public void update(SysFile sysFile){
        sysFile.setUpdateDate(new Date());
        sysFile.setUpdateUser(0L);
        sysFile.setStatusDate(new Date());
        updateById(sysFile);
    }
    @Override
    public Page<SysFileVo> getSysFilePage(String search,Integer pageSize, Integer pageNo){
        Page<SysFileVo> page = new Page<SysFileVo>(StrUtil.isNullOrEmpty(pageNo)?1:pageNo,
                StrUtil.isNullOrEmpty(pageSize)?10:pageSize);
        List<SysFileVo> list = new ArrayList<>();
        list = baseMapper.getSysFilePage(page,search);
        page.setRecords(list);
        return page;
    }
}
