package cn.ffcs.uoo.system.controller.service.impl;

import cn.ffcs.uoo.system.controller.entity.SysPosition;
import cn.ffcs.uoo.system.controller.dao.SysPositionMapper;
import cn.ffcs.uoo.system.controller.service.SysPositionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 根据不同部门的工作性质、责任轻重、难易程度和所需资格条件等进行分类，在平台上，不对职位进行过细的区分 服务实现类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-12-20
 */
@Service
public class SysPositionServiceImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService {

}
