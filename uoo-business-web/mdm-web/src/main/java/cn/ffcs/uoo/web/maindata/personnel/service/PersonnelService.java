package cn.ffcs.uoo.web.maindata.personnel.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.personnel.dto.TbPersonnel;
import common.config.PersonnelServiceConfiguration;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName PersonnelService
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/8 21:49
 * @Version 1.0.0
*/
@FeignClient(value = "personnel-service",configuration = {PersonnelServiceConfiguration.class},fallback = PersonnelServiceHystrix.class)
//@FeignClient("personnel-service")
public interface PersonnelService {

    @RequestMapping(value = "/personnel/testPage",method = RequestMethod.GET)
    Page<TbPersonnel> testPersonnel();

    @RequestMapping(value = "/personnel/getPage/",method = RequestMethod.POST)
    Page<TbPersonnel> getPersonnelCondition(@RequestBody TbPersonnel tbPersonnel);

    @RequestMapping(value = "/personnel/getPage/pageNo={pageNo}&pageSize={pageSize}",method = RequestMethod.GET)
    Page<TbPersonnel> getPersonnel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize);

    @RequestMapping(value = "/personnel/test",method = RequestMethod.GET)
    String test();
}
