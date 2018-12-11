package cn.ffcs.uoo.core.user.client;

import cn.ffcs.uoo.core.user.vo.PersonnelInfoVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "business-personnel")
public interface PsnClient {

}
