package cn.ffcs.uoo.web.maindata.personnel.controller;

import cn.ffcs.uoo.web.maindata.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.web.maindata.personnel.service.PersonnelService;
import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * @ClassName PersonnelController
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/8 20:28
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/personnel")
public class PersonnelController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonnelService personnelService;

    @RequestMapping(value = "/testPage",method = RequestMethod.GET)
    public Page<TbPersonnel> testPersonnel() {
        log.error(" testPersonnel was be Requseted");
        return personnelService.testPersonnel();
    }


    @RequestMapping(value = "/getPage/",method = RequestMethod.POST)
    public Page<TbPersonnel> getPersonnelCondition(@RequestBody TbPersonnel tbPersonnel){
        return personnelService.getPersonnelCondition(tbPersonnel);
    }

    @RequestMapping(value = "/getPage/pageNo={pageNo}&pageSize={pageSize}",method = RequestMethod.GET)
    public Page<TbPersonnel> getPersonnel(@PathVariable(value = "pageNo") Integer pageNo, @PathVariable(value = "pageSize",required = false) Integer pageSize){
        return personnelService.getPersonnel(pageNo,pageSize);
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test() {
        return personnelService.test();
    }

}
