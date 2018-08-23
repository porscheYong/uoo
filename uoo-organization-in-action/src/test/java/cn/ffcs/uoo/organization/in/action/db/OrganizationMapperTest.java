package cn.ffcs.uoo.organization.in.action.db;

import cn.ffcs.uoo.organization.in.action.domain.Organization;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationMapperTest {

    @Resource
    OrganizationMapper organizationMapper;

    @Test
    public void testList() {
        ArrayList<Organization> organizations = organizationMapper.list();
        Assert.assertNotNull(organizations);
    }

    @Test
    public void testGet() {
        Organization organization = organizationMapper.get(1);
        Assert.assertNotNull(organization);
    }

    @Test
    @Transactional
    public void testSave() {
        Organization organization = new Organization();
        organization.setName("测试组织");
        Assert.assertEquals(1, organizationMapper.save(organization));
    }

    @Test
    @Transactional
    public void testRemove() {
        Organization organization = new Organization();
        organization.setId(1);
        Assert.assertEquals(1, organizationMapper.remove(organization));
    }

    @Test
    @Transactional
    public void testUpdatte() {
        Organization organization = new Organization();
        organization.setId(1);
        organization.setName("测试-修改");
        Assert.assertEquals(1, organizationMapper.update(organization));
    }

}
