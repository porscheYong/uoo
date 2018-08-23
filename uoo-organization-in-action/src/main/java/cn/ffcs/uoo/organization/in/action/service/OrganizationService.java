package cn.ffcs.uoo.organization.in.action.service;

import cn.ffcs.uoo.organization.in.action.domain.Organization;

import java.util.ArrayList;

/**
 * OrganizationService接口定义
 *
 * @author Lin
 * @date 2018/08/23
 */
public interface OrganizationService {

    ArrayList<Organization> list();

    Organization get(Integer id);

    int save(Organization organization);

    int remove(Organization organization);

    int update(Organization organization);

}
