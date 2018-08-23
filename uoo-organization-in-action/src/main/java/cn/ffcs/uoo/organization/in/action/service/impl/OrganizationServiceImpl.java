package cn.ffcs.uoo.organization.in.action.service.impl;

import cn.ffcs.uoo.organization.in.action.db.OrganizationMapper;
import cn.ffcs.uoo.organization.in.action.domain.Organization;
import cn.ffcs.uoo.organization.in.action.service.OrganizationService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * OrganizationService实现
 *
 * @author Lin
 * @date 2018/08/23
 */
@Component
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private OrganizationMapper organizationMapper;

    @Override
    public ArrayList<Organization> list() {
        return organizationMapper.list();
    }

    @Override
    public Organization get(Integer id) {
        return organizationMapper.get(id);
    }

    @Override
    public int save(Organization organization) {
        return organizationMapper.save(organization);
    }

    @Override
    public int remove(Organization organization) {
        return organizationMapper.remove(organization);
    }

    @Override
    public int update(Organization organization) {
        return organizationMapper.update(organization);
    }

}
