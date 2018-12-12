package cn.ffcs.uoo.web.maindata.busipublic.expando.service.fallback;

import cn.ffcs.uoo.web.maindata.busipublic.expando.dto.TbExpandorow;
import cn.ffcs.uoo.web.maindata.busipublic.expando.service.TbExpandorowClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 扩展行客户端断路器
 * @author zhanglu
 * @date 2018-11-12
 */
@Component
public class TbExpandorowClientHystrix implements TbExpandorowClient {
    @Override
    public List<TbExpandorow> queryRowList(Long tableId, String resourceId, String recordId) {
        return null;
    }
}
