package cn.ffcs.uoo.core.expando.service;

import cn.ffcs.uoo.core.expando.entity.TbExpandovalue;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 扩展值 服务类
 * </p>
 *
 * @author zhanglu
 * @since 2018-10-25
 */
public interface TbExpandovalueService extends IService<TbExpandovalue> {
    /**
     * 新增扩展值
     * @param tbExpandovalue
     * @return
     */
    int save(TbExpandovalue tbExpandovalue);

    /**
     * 删除扩展值
     * @param valueId
     * @param updateUser
     */
    void remove(Long valueId, Long updateUser);

    /**
     * 查询扩展值
     * @param tbExpandovalue
     * @return
     */
    List<TbExpandovalue> selectValueList(TbExpandovalue tbExpandovalue);
}
