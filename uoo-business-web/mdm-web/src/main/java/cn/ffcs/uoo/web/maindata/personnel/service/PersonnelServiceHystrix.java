package cn.ffcs.uoo.web.maindata.personnel.service;


import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.personnel.dto.TbPersonnel;

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
 * @ClassName PersonnelServiceHystrix
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/9/8 22:00
 * @Version 1.0.0
*/
@Component
public class PersonnelServiceHystrix implements PersonnelService{

    @Override
    public Page<TbPersonnel> testPersonnel() {
        Page<TbPersonnel> page = new Page<TbPersonnel>();
        page.setSize(12);
        page.setCurrent(0);
        return page;
    }

    @Override
    public Page<TbPersonnel> getPersonnelCondition(TbPersonnel tbPersonnel) {
        return null;
    }

    @Override
    public Page<TbPersonnel> getPersonnel(Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public String test() {
        return " service is error";
    }
}
