package cn.ffcs.uoo.core.user.service.impl;

import cn.ffcs.uoo.core.user.constant.BaseUnitConstants;
import cn.ffcs.uoo.core.user.entity.TbAcct;
import cn.ffcs.uoo.core.user.service.RabbitMqService;
import cn.ffcs.uoo.core.user.service.TbAcctService;
import cn.ffcs.uoo.core.user.util.StrUtil;
import cn.ffcs.uoo.core.user.vo.MqUserContextVo;
import cn.ffcs.uoo.core.user.vo.MqUserMessageVo;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * RabbitMqServiceImpl
 * @author wudj
 * @date 2018/11/16
 */
@Service("rabbitMqService")
public class RabbitMqServiceImpl implements RabbitMqService {

    @Autowired
    private AmqpTemplate template;
    @Autowired
    private TbAcctService tbAcctService;

    @Override
    public Object sendMqMsg(String type, String handle, String column, Long value){
        MqUserMessageVo mqMessageVo = new MqUserMessageVo();
        MqUserContextVo mqContextVo = new MqUserContextVo();
        if("person".equals(type)){
            TbAcct tbAcct = (TbAcct) tbAcctService.getTbAcctByPsnId(value);
            if(StrUtil.isNullOrEmpty(tbAcct)){
                return null;
            }
        }
        mqMessageVo.setType(type);
        mqMessageVo.setHandle(handle);
        mqContextVo.setColumn(column);
        mqContextVo.setValue(value);
        mqMessageVo.setContext(mqContextVo);

        if(!StrUtil.isNullOrEmpty(mqMessageVo)){
            template.convertAndSend(BaseUnitConstants.MSG_SHARING_QUEUE, JSON.toJSONString(mqMessageVo));
        }
        return null;
    }
}
