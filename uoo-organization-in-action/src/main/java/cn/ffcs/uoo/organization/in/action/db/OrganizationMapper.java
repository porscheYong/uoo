package cn.ffcs.uoo.organization.in.action.db;

import cn.ffcs.uoo.organization.in.action.domain.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * OrganizationMapper
 *
 * @author Lin
 * @date 2018/08/23
 */
@Mapper
public interface OrganizationMapper {

    ArrayList<Organization> list();

    Organization get(@Param("id") Integer id);

    int save(Organization organization);

    int remove(Organization organization);

    int update(Organization organization);

}
