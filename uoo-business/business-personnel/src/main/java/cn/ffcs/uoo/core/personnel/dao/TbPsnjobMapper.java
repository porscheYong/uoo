package cn.ffcs.uoo.core.personnel.dao;

import cn.ffcs.uoo.core.personnel.entity.TbPsnjob;
import cn.ffcs.uoo.core.personnel.vo.TbPsnjobVo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wudj
 * @since 2018-10-11
 */
public interface TbPsnjobMapper extends BaseMapper<TbPsnjob> {

    public Long getId();

    public void delete(TbPsnjob tbPsnjob);

    public List<TbPsnjobVo> getPsnjobPageBypsnId(Pagination page, @Param("personnelId") Long personnelId);

}
