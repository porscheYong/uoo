package cn.ffcs.uoo.core.organization.Api;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018-09-25
 */
@FeignClient(value = "business-personnel")
public interface CertService {

    @RequestMapping(value="/cert/getCertInfo", method = RequestMethod.GET, headers={"Content-Type=application/json"})
    public Object getCertInfo(@RequestParam("keyWord") String keyWord, @RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize);
}
