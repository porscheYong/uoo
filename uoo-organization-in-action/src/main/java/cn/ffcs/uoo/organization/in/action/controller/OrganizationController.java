package cn.ffcs.uoo.organization.in.action.controller;

import cn.ffcs.uoo.common.domain.ResultBean;
import cn.ffcs.uoo.organization.in.action.domain.Organization;
import cn.ffcs.uoo.organization.in.action.service.OrganizationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * OrganizationController
 *
 * @author Lin
 * @date 2018/08/23
 */
@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @HystrixCommand
    @GetMapping("/organization")
    public ResultBean<ArrayList<Organization>> list() {
        return new ResultBean(organizationService.list());
    }

    @GetMapping("/organization/{id}")
    public ResultBean<Organization> get(@PathVariable("id") int id) {
        return new ResultBean<>(organizationService.get(id));
    }

    @PostMapping("/organization")
    public ResultBean<String> save(Organization organization) {
        return new ResultBean<>(String.valueOf(organizationService.save(organization)));
    }

    @DeleteMapping("/organization")
    public ResultBean<String> remove(@RequestBody Organization organization) {
        return new ResultBean<>(String.valueOf(organizationService.remove(organization)));
    }

    @PutMapping("/organization")
    public ResultBean<String> update(@RequestBody Organization organization) {
        return new ResultBean<>(String.valueOf(organizationService.update(organization)));
    }

}
