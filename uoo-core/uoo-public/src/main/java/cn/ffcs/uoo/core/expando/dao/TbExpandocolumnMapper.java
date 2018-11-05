package cn.ffcs.uoo.core.expando.dao;

import cn.ffcs.uoo.core.expando.entity.TbExpandocolumn;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 扩展列 Mapper 接口
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbExpandocolumnMapper extends BaseMapper<TbExpandocolumn> {
    /**
     * 新增扩展列
     * @param tbExpandocolumn
     * @return
     */
    int save(TbExpandocolumn tbExpandocolumn);

    /**
     * 删除扩展列
     * @param tbExpandocolumn
     */
    void remove(TbExpandocolumn tbExpandocolumn);

    /**
     *  查询扩展列
     * @param tableId 表标识
     * @param resourceId 资源标识
     * @return
     */
    List<TbExpandocolumn> queryColumnList(@Param("tableId") Long tableId, @Param("resourceId") String resourceId);
}
