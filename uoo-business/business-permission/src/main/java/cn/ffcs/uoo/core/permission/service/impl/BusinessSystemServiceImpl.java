package cn.ffcs.uoo.core.permission.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.core.permission.dao.BusinessSystemMapper;
import cn.ffcs.uoo.core.permission.entity.BusinessSystem;
import cn.ffcs.uoo.core.permission.service.BusinessSystemService;

/**
 * <p>
 * 接入系统表 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-10-30
 */
@Service
public class BusinessSystemServiceImpl extends ServiceImpl<BusinessSystemMapper, BusinessSystem> implements BusinessSystemService {

}
