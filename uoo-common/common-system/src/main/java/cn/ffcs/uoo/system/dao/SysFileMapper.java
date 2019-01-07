package cn.ffcs.uoo.system.dao;

import cn.ffcs.uoo.system.entity.SysFile;
import cn.ffcs.uoo.system.vo.SysFileVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-24
 */
public interface SysFileMapper extends BaseMapper<SysFile> {

    public Long getId();
    List<SysFileVo> getSysFilePage(Pagination page, @Param("search")String search);
}
