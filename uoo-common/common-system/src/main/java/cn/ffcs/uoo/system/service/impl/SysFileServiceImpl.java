package cn.ffcs.uoo.system.service.impl;

import cn.ffcs.uoo.system.entity.SysFile;
import cn.ffcs.uoo.system.dao.SysFileMapper;
import cn.ffcs.uoo.system.service.ISysFileService;
import cn.ffcs.uoo.system.service.ModifyHistoryService;
import cn.ffcs.uoo.system.util.StrUtil;
import cn.ffcs.uoo.system.vo.SysFileVo;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ModifyHistoryService modifyHistoryService;
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
        sysFile.setUpdateUser(StrUtil.isNullOrEmpty(sysFile.getUpdateUser())?0L:sysFile.getUpdateUser());
        updateById(sysFile);
        modifyHistoryService.addModifyHistory(sysFile,null,sysFile.getUpdateUser(),sysFile.getBatchNumber());
    }



    /**
     * 新增
     */
    @Override
    public void add(SysFile sysFile){
        sysFile.setCreateDate(new Date());
        sysFile.setCreateUser(StrUtil.isNullOrEmpty(sysFile.getCreateUser())?0L:sysFile.getCreateUser());
        sysFile.setStatusCd("1000");
        sysFile.setStatusDate(new Date());
        insert(sysFile);
        modifyHistoryService.addModifyHistory(null,sysFile,sysFile.getUpdateUser(),sysFile.getBatchNumber());
    }

    /**
     * 更新
     */
    @Override
    public void update(SysFile sysFile){
        sysFile.setUpdateDate(new Date());
        sysFile.setUpdateUser(StrUtil.isNullOrEmpty(sysFile.getUpdateUser())?0L:sysFile.getUpdateUser());
        sysFile.setStatusDate(new Date());
        updateById(sysFile);
        Wrapper depWrapper = Condition.create()
                .eq("FILE_ID",sysFile.getFileId())
                .eq("STATUS_CD","1000");
        SysFile sysFileOld = selectOne(depWrapper);
        modifyHistoryService.addModifyHistory(sysFileOld,sysFile,sysFile.getUpdateUser(),sysFile.getBatchNumber());

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
