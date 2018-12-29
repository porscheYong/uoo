package cn.ffcs.uoo.core.permission.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import cn.ffcs.uoo.core.permission.entity.BusinessSystem;

/**
 * <p>
 * 接入系统表 服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
public interface BusinessSystemService extends IService<BusinessSystem> {
    List<BusinessSystem> selectListByTreeId(Long treeId);
}
