package cn.ffcs.interfaces.cpc.service.impl;

import cn.ffcs.interfaces.cpc.dao.CommonRegionMapper;
import cn.ffcs.interfaces.cpc.pojo.CommonRegion;
import cn.ffcs.interfaces.cpc.service.CommonRegionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 指对于各种专业电信管理区域的共性管理区域信息的抽象表达，包括省公司、本地网、营业区。因为使用目的不同，可以定义不同使用类型的管理区域，如：CRM使用、计费使用、营销使用等。 服务实现类
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
@Service
public class CommonRegionServiceImpl extends ServiceImpl<CommonRegionMapper, CommonRegion> implements CommonRegionService {

}
