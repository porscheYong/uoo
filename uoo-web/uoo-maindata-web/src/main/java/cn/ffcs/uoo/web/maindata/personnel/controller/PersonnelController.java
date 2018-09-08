package cn.ffcs.uoo.web.maindata.personnel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    private static String PERSONNEL_SERVICE="http://PERSONNEL-SERVICE/personnel/";

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping(value = "/testPage",method = RequestMethod.GET)
    public String testPersonnel() {
        log.error(" testPersonnel was be Requseted");
        return restTemplate.getForEntity(PERSONNEL_SERVICE+"testPage",String.class).getBody();
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test() {

        return "Hello Uoo";
    }

}
