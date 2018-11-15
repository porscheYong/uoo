package cn.ffcs.uoo.web.maindata.personnel.service.fallback;


import cn.ffcs.uoo.web.maindata.personnel.entity.TbPersonnel;
import cn.ffcs.uoo.web.maindata.personnel.service.PersonnelService;
import cn.ffcs.uoo.web.maindata.personnel.vo.EditFormPersonnelVo;
import cn.ffcs.uoo.web.maindata.personnel.vo.PersonnelVo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Component;

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
public class PersonnelServiceHystrix implements PersonnelService {


    @Override
    public Object getFormPersonnel(Long personnelId, Long orgRootId, Long orgId) {
        return null;
    }

    @Override
    public Object savePersonnel(EditFormPersonnelVo editFormPersonnelVo) {
        return null;
    }

    @Override
    public Object deletePersonnel(Long personnelId) {
        return null;
    }

    @Override
    public Object upPersonnel(PersonnelVo personnelVo) {
        return null;
    }
}
