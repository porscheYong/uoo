package cn.ffcs.uoo.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.ffcs.uoo.system.dao.SysTableColumnMapper;
import cn.ffcs.uoo.system.entity.SysTableColumn;
import cn.ffcs.uoo.system.service.ISysTableColumnService;

/**
 * <p>
 * 记录系统功能实现中涉及库表的字段信息。 服务实现类
 * </p>
 *
 * @author zengxsh
 * @since 2018-12-21
 */
@Service
public class SysTableColumnServiceImpl extends ServiceImpl<SysTableColumnMapper, SysTableColumn> implements ISysTableColumnService {

}
