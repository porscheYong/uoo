package cn.ffcs.uoo.web.maindata.personnel.service;


import cn.ffcs.uoo.web.maindata.personnel.service.fallback.PersonnelServiceHystrix;
import cn.ffcs.uoo.web.maindata.personnel.vo.EditFormPersonnelVo;
import cn.ffcs.uoo.web.maindata.personnel.vo.PersonnelVo;
import common.config.PersonnelServiceConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@FeignClient(value = "business-personnel",configuration = {PersonnelServiceConfiguration.class},fallback = PersonnelServiceHystrix.class)
public interface PersonnelService {

    @RequestMapping(value="/personnel/getFormPersonnel", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getFormPersonnel(@RequestParam("personnelId") Long personnelId,
                                    @RequestParam("orgTreeId") Long orgTreeId,
                                    @RequestParam("orgId") Long orgId,
                                    @RequestParam("accout") String accout);

    @RequestMapping(value = "/personnel/savePersonnel", method = RequestMethod.POST , headers={"Content-Type=application/json"} )
    public Object savePersonnel(@RequestBody EditFormPersonnelVo editFormPersonnelVo);

    @RequestMapping(value="/personnel/deletePersonnel",method = RequestMethod.DELETE, headers={"Content-Type=application/json"} )
    public Object deletePersonnel(@RequestParam("personnelId") Long personnelId, @RequestParam("userId") Long userId);

    @RequestMapping(value = "/personnel/updatePersonnel",method = RequestMethod.PUT, headers={"Content-Type=application/json"} )
    public Object upPersonnel(@RequestBody PersonnelVo personnelVo);

    @RequestMapping(value="/personnel/getPsnBasicInfo", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getPsnBasicInfo(@RequestParam("keyWord") String keyWord, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/personnel/getIdCardInfo",method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getIdCardInfo(@RequestParam("certNo") String certNo);

    @RequestMapping(value="/personnel/getFreePsnInfo",method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getFreePsnInfo(@RequestParam("keyWord") String keyWord, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);

    @RequestMapping(value = "/personnel/getIdCardNcCode",method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getIdCardNcCode(@RequestParam("certNo") String certNo);
}
