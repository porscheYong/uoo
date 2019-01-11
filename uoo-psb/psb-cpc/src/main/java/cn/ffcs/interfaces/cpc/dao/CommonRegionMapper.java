package cn.ffcs.interfaces.cpc.dao;

import cn.ffcs.interfaces.cpc.pojo.CommonRegion;
import cn.ffcs.interfaces.cpc.pojo.CommonRegion;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 指对于各种专业电信管理区域的共性管理区域信息的抽象表达，包括省公司、本地网、营业区。因为使用目的不同，可以定义不同使用类型的管理区域，如：CRM使用、计费使用、营销使用等。 Mapper 接口
 * </p>
 *
 * @author lxd
 * @since 2019-01-10
 */
public interface CommonRegionMapper extends BaseMapper<CommonRegion> {

}
