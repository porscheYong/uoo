package cn.ffcs.uoo.system.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.system.entity.SysFile;
import cn.ffcs.uoo.system.vo.PermFile;
import cn.ffcs.uoo.system.vo.SysFileVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface ISysFileService extends IService<SysFile> {
    /**
     * 获取seq
     * @return
     */
    public Long getId();

    /**
     * 失效状态
     * @param sysFile
     */
    public void delete(SysFile sysFile);

    /**
     * 新增
     */
    public void add(SysFile sysFile);

    /**
     * 更新
     */
    public void update(SysFile sysFile);

    public Page<SysFileVo> getSysFilePage(String search,Integer pageSize, Integer pageNo);

    //public SysFileVo getSysFile(String id,Integer pageSize, Integer pageNo);
    public List<PermFile> listByPermissionId(Long permId);
}
