package cn.ffcs.uoo.demo.area.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.base.controller.BaseController;
import cn.ffcs.uoo.demo.area.entity.TbLocations;
import cn.ffcs.uoo.demo.area.service.TbLocationsService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-08-31
 */
@RestController
@RequestMapping("/area")
public class TbLocationsController extends BaseController {

    @Autowired
    private TbLocationsService tbLocationsService;

    /**
     * 分页 PAGE
     */
    @UooLog(value = "查询区域测试分页", key = "testArea")
    @RequestMapping(value = "/getPage/{page}",method = RequestMethod.GET)
    public Page<TbLocations> getPage(@PathVariable(value = "page") int page) {
        return tbLocationsService.selectPage(new Page<TbLocations>(page, 12));
    }

}

