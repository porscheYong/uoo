package cn.ffcs.uoo.message.server.job;

import cn.ffcs.uoo.message.server.dao.*;
import cn.ffcs.uoo.message.server.pojo.*;
import cn.ffcs.uoo.message.server.service.RabbitMqSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@EnableScheduling
public class SendJob {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private TbImportMapper tbImportMapper;
    @Resource
    private TbHandelMapper tbHandelMapper;

    @Resource
    private TbOrgMapper tbOrgMapper;
    @Resource
    private TbPersonnelMapper tbPersonnelMapper;
    @Resource
    private TbSlaveAcctMapper tbSlaveAcctMapper;
    @Autowired
    private AmqpTemplate template;

    @Scheduled(cron = "0 0/5 * * * ?")
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void sendMsg() {
        logger.info("执行开始-----");

        synchronized (tbHandelMapper) {
            //没有批次号在执行
            if (tbHandelMapper.checkRun() == 0) {
                TbHandel tbHandel = tbHandelMapper.getTbHandleByTime();

                if(tbHandel == null){
                    logger.warn("没有需要执行的批次号，执行结束------");
                    return ;
                }

                tbHandel.setStatusCd("1");
                tbHandelMapper.updateById(tbHandel);
                //获取这个批次的待执行的的数据
                List<TbImport> list = tbImportMapper.getTbImportListByBatchCode(tbHandel.getBatchCode());

                if(list != null && list.size() >0){
                    list.forEach((temp)->{
                        String type = temp.getImportType();
                        Long value = Long.parseLong(temp.getImportValue());
                        switch (type){
                            case "orgId":{
                                TbOrg org = tbOrgMapper.selectById(value);
                                if(org == null){
                                    temp.setStatusCd("-2");
                                    temp.setFailReason("组织标识不在数据库");
                                    tbImportMapper.updateById(temp);
                                }else{
                                    String msg = "";
                                    if("1000".equals(org.getStatusCd())){
                                        //update
                                        msg = "{\"type\":\"org\",\"handle\":\"update\",\"context\":{\"column\":\"orgId\",\"value\":"+value+"}}";
                                        send(msg);
                                    }else if("1100".equals(org.getStatusCd())){
                                        //delete
                                        msg = "{\"type\":\"org\",\"handle\":\"delete\",\"context\":{\"column\":\"orgId\",\"value\":"+value+"}}";
                                        send(msg);
                                    }
                                    temp.setStatusCd("1");
                                    temp.setSimpleData(msg);
                                    tbImportMapper.updateById(temp);
                                }
                            };break;
                            case "slaveAcctId":{
                                TbSlaveAcct tbSlaveAcct = tbSlaveAcctMapper.selectById(value);
                                if(tbSlaveAcct == null){
                                    temp.setStatusCd("-2");
                                    temp.setFailReason("从账号标识不在数据库");
                                    tbImportMapper.updateById(temp);
                                }else{String msg ="";
                                    if("1000".equals(tbSlaveAcct.getStatusCd())){
                                        //update
                                         msg = "{\"type\":\"person\",\"handle\":\"update\",\"context\":{\"column\":\"slaveAcctId\",\"value\":"+value+"}}";
                                        send(msg);
                                    }else if("1100".equals(tbSlaveAcct.getStatusCd())){
                                        //delete
                                         msg = "{\"type\":\"person\",\"handle\":\"delete\",\"context\":{\"column\":\"slaveAcctId\",\"value\":"+value+"}}";
                                        send(msg);
                                    }
                                    temp.setStatusCd("1");
                                    temp.setSimpleData(msg);
                                    tbImportMapper.updateById(temp);
                                }
                            };break;
                            case "personnelId":{
                                List<TbPersonnel> tbPersonnelList = tbPersonnelMapper.selectPersonnelAndAcctById(value);
                                if(tbPersonnelList == null && tbPersonnelList.size() <=0){
                                    temp.setStatusCd("-2");
                                    temp.setFailReason("人员/主账号不在数据库");
                                    tbImportMapper.updateById(temp);
                                }else{
                                    String cd = "1100";
                                    for(TbPersonnel t:tbPersonnelList){
                                        if("2000".equals(t.getStatusCd())){
                                            cd = "1000";
                                            break;
                                        }
                                    }
                                    String msg ="";
                                    if("1000".equals(cd)){
                                        //update
                                         msg = "{\"type\":\"person\",\"handle\":\"update\",\"context\":{\"column\":\"personnelId\",\"value\":"+value+"}}";
                                        send(msg);
                                    }else if("1100".equals(cd)){
                                        //delete
                                         msg = "{\"type\":\"person\",\"handle\":\"delete\",\"context\":{\"column\":\"personnelId\",\"value\":"+value+"}}";
                                        send(msg);
                                    }
                                    temp.setStatusCd("1");
                                    temp.setSimpleData(msg);
                                    tbImportMapper.updateById(temp);
                                }
                            };break;
                        }
                    });
                }
                tbHandel.setStatusCd("-1");
                tbHandelMapper.updateById(tbHandel);
            }
        }
        logger.info("执行结束------");
    }
    private void send(String msg){
        template.convertAndSend("message_sharing_center_queue",msg);
    }
}